package com.example.demo.service;

import com.example.demo.config.VNPayConfig;
import com.example.demo.dto.request.PaymentRequest;
import com.google.gson.Gson;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Service;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class VNPayService {
    private final VNPayConfig vnPayConfig;

    public VNPayService(VNPayConfig vnPayConfig) {
        this.vnPayConfig = vnPayConfig;
    }
    // Thực hiện giao dịch thanh toán và tạo URL thanh toán VNPay

    public String createPaymentRequest(PaymentRequest paymentRequest) throws IOException, UnsupportedEncodingException {
        // Lấy các thông tin cần thiết từ đối tượng paymentRequest
        String vnp_TxnRef = VNPayConfig.getRandomNumber(8);  // Mã giao dịch ngẫu nhiên
        String vnp_Version = "2.1.0";
        String vnp_Command = "pay";
        String orderType = "other";  // Loại đơn hàng (ví dụ: "other")
        long amount = (paymentRequest.getAmount() * 100); // Amount in cents (VND)

        String bankCode = paymentRequest.getBankCode();  // Mã ngân hàng thanh toán (nếu có, có thể lấy từ paymentRequest)
        String vnp_IpAddr = paymentRequest.getIpAddress();  // Địa chỉ IP của người dùng (có thể lấy từ paymentRequest)
        String vnp_TmnCode = vnPayConfig.getVnpTmnCode();

        // Tạo Map để chứa các tham số cho yêu cầu
        Map<String, String> vnp_Params = new TreeMap<>();  // Sử dụng TreeMap để tự động sắp xếp theo thứ tự từ A-Z
        vnp_Params.put("vnp_Version", vnp_Version);
        vnp_Params.put("vnp_Command", vnp_Command);
        vnp_Params.put("vnp_TmnCode", vnp_TmnCode);
        vnp_Params.put("vnp_Amount", String.valueOf(amount));
        vnp_Params.put("vnp_CurrCode", "VND");

        if (bankCode != null && !bankCode.isEmpty()) {
            vnp_Params.put("vnp_BankCode", bankCode);  // Thêm mã ngân hàng nếu có
        }

        vnp_Params.put("vnp_TxnRef", vnp_TxnRef);

        // Mã hóa OrderInfo để đảm bảo tính hợp lệ của URL
        String orderInfo = paymentRequest.getOrderDescription();  // Mô tả đơn hàng từ paymentRequest
        String encodedOrderInfo = URLEncoder.encode(orderInfo, "UTF-8");
        vnp_Params.put("vnp_OrderInfo", encodedOrderInfo);

        vnp_Params.put("vnp_OrderType", orderType);
        vnp_Params.put("vnp_Locale", "vn");  // Ngôn ngữ hiển thị (vn - Tiếng Việt, en - Tiếng Anh)
        vnp_Params.put("vnp_IpAddr", vnp_IpAddr);

        // Lấy thời gian hiện tại cho CreateDate và ExpireDate
        Calendar cld = Calendar.getInstance(TimeZone.getTimeZone("Etc/GMT+7"));
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        formatter.setTimeZone(TimeZone.getTimeZone("GMT+7"));
        String vnp_CreateDate = formatter.format(cld.getTime());
        vnp_Params.put("vnp_CreateDate", vnp_CreateDate);

        // Tính thời gian hết hạn 10 phút sau thời điểm hiện tại
        cld.add(Calendar.MINUTE, 10);  // Tính toán thời gian hết hạn 10 phút
        String vnp_ExpireDate = formatter.format(cld.getTime());
        vnp_Params.put("vnp_ExpireDate", vnp_ExpireDate);

        // Xây dựng chuỗi hash
        StringBuilder hashData = new StringBuilder();
        for (Map.Entry<String, String> entry : vnp_Params.entrySet()) {
            hashData.append(entry.getKey()).append("=").append(entry.getValue()).append("|");
        }
        hashData.deleteCharAt(hashData.length() - 1); // Loại bỏ dấu "|" cuối cùng

        // Tính toán secure hash
        String vnp_SecureHash = VNPayConfig.hmacSHA512(vnPayConfig.getSecretKey(), hashData.toString());
        vnp_Params.put("vnp_SecureHash", vnp_SecureHash);

        // Xây dựng URL thanh toán
        StringBuilder queryUrl = new StringBuilder();
        for (Map.Entry<String, String> entry : vnp_Params.entrySet()) {
            queryUrl.append(entry.getKey()).append("=").append(entry.getValue()).append("&");
        }
        queryUrl.deleteCharAt(queryUrl.length() - 1); // Loại bỏ dấu "&" cuối cùng

        // Trả về URL thanh toán hoàn chỉnh
        return vnPayConfig.getVnpPayUrl() + "?" + queryUrl.toString();
    }

    public String createPaymentUrl(HttpServletRequest request, long amountRequest) throws UnsupportedEncodingException {
        String orderType = "other";  // Loại đơn hàng
        long amount = amountRequest * 100;  // Chuyển từ VND sang đồng (VNPay yêu cầu số tiền tính theo đồng)
        String vnp_TxnRef = VNPayConfig.getRandomNumber(8);  // Mã giao dịch ngẫu nhiên
        String vnp_IpAddr = VNPayConfig.getIpAddress(request);  // Lấy địa chỉ IP của người dùng

        String vnp_TmnCode = vnPayConfig.getVnpTmnCode();  // Mã merchant từ cấu hình

        Map<String, String> vnp_Params = new HashMap<>();
        vnp_Params.put("vnp_Version", vnPayConfig.getVnp_Version());  // Phiên bản
        vnp_Params.put("vnp_Command", vnPayConfig.getVnp_Command());  // Lệnh thanh toán
        vnp_Params.put("vnp_TmnCode", vnp_TmnCode);  // Mã merchant
        vnp_Params.put("vnp_Amount", String.valueOf(amount));  // Số tiền thanh toán
        vnp_Params.put("vnp_CurrCode", "VND");  // Mã tiền tệ
        vnp_Params.put("vnp_TxnRef", vnp_TxnRef);  // Mã giao dịch
        vnp_Params.put("vnp_OrderInfo", "Thanh toán đơn hàng:" + vnp_TxnRef);  // Mô tả đơn hàng
        vnp_Params.put("vnp_OrderType", orderType);  // Loại đơn hàng
        vnp_Params.put("vnp_Locale", "vn");  // Ngôn ngữ hiển thị (vn: Tiếng Việt)
        vnp_Params.put("vnp_ReturnUrl", vnPayConfig.getVnpReturnUrl());  // URL trả kết quả
        vnp_Params.put("vnp_IpAddr", vnp_IpAddr);  // Địa chỉ IP của người dùng

        Calendar cld = Calendar.getInstance(TimeZone.getTimeZone("Etc/GMT+7"));
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        String vnp_CreateDate = formatter.format(cld.getTime());  // Thời gian tạo giao dịch
        vnp_Params.put("vnp_CreateDate", vnp_CreateDate);

        cld.add(Calendar.MINUTE, 15);  // Cộng thêm 15 phút vào thời gian hiện tại
        String vnp_ExpireDate = formatter.format(cld.getTime());  // Thời gian hết hạn
        vnp_Params.put("vnp_ExpireDate", vnp_ExpireDate);

        List<String> fieldNames = new ArrayList<>(vnp_Params.keySet());
        Collections.sort(fieldNames);  // Sắp xếp các tham số

        StringBuilder hashData = new StringBuilder();
        StringBuilder query = new StringBuilder();
        Iterator<String> itr = fieldNames.iterator();
        while (itr.hasNext()) {
            String fieldName = itr.next();
            String fieldValue = vnp_Params.get(fieldName);
            if (fieldValue != null && fieldValue.length() > 0) {
                hashData.append(fieldName).append('=').append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
                query.append(URLEncoder.encode(fieldName, StandardCharsets.US_ASCII.toString()))
                        .append('=')
                        .append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
                if (itr.hasNext()) {
                    query.append('&');
                    hashData.append('&');
                }
            }
        }

        String queryUrl = query.toString();
        String vnp_SecureHash = VNPayConfig.hmacSHA512(vnPayConfig.getSecretKey(), hashData.toString());
        queryUrl += "&vnp_SecureHash=" + vnp_SecureHash;

        String paymentUrl = vnPayConfig.getVnpPayUrl() + "?" + queryUrl;
        return paymentUrl;
    }





    // Truy vấn thông tin giao dịch từ VNPay
    public String queryTransaction(HttpServletRequest req) throws IOException {
        String vnp_RequestId = VNPayConfig.getRandomNumber(8);
        String vnp_Version = "2.1.0";
        String vnp_Command = "query";
        String vnp_TmnCode = vnPayConfig.getVnpTmnCode();
        String vnp_TxnRef = req.getParameter("order_id");

        // Create a map to hold the parameters for the API request
        Map<String, String> vnp_Params = new TreeMap<>();
        vnp_Params.put("vnp_RequestId", vnp_RequestId);
        vnp_Params.put("vnp_Version", vnp_Version);
        vnp_Params.put("vnp_Command", vnp_Command);
        vnp_Params.put("vnp_TmnCode", vnp_TmnCode);
        vnp_Params.put("vnp_TxnRef", vnp_TxnRef);

        // Create a string for the hash data
        StringBuilder hashData = new StringBuilder();
        for (Map.Entry<String, String> entry : vnp_Params.entrySet()) {
            hashData.append(entry.getKey()).append("|").append(entry.getValue()).append("|");
        }

        // Remove the trailing "|" from the string
        hashData.deleteCharAt(hashData.length() - 1);

        // Calculate the secure hash
        String vnp_SecureHash = VNPayConfig.hmacSHA512(vnPayConfig.getSecretKey(), hashData.toString());
        vnp_Params.put("vnp_SecureHash", vnp_SecureHash);

        // Construct the query string for the API request
        StringBuilder requestData = new StringBuilder();
        for (Map.Entry<String, String> entry : vnp_Params.entrySet()) {
            requestData.append(entry.getKey()).append("=").append(entry.getValue()).append("&");
        }

        // Remove the trailing "&" from the query string
        requestData.deleteCharAt(requestData.length() - 1);

        // Send the request to VNPay API
        URL url = new URL(vnPayConfig.getVnpApiUrl());
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("POST");
        con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        con.setDoOutput(true);

        // Send the parameters as URL-encoded data
        DataOutputStream wr = new DataOutputStream(con.getOutputStream());
        wr.writeBytes(requestData.toString());
        wr.flush();
        wr.close();

        // Read the response from the VNPay API
        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        StringBuilder response = new StringBuilder();
        String output;
        while ((output = in.readLine()) != null) {
            response.append(output);
        }
        in.close();


        // Return the API response
        return response.toString();
    }

    public boolean verifySecureHash(Map<String, String> responseParams) {
        StringBuilder hashData = new StringBuilder();

        // Lấy tất cả các tham số và sắp xếp theo thứ tự
        for (Map.Entry<String, String> entry : responseParams.entrySet()) {
            // Bỏ qua tham số vnp_SecureHash khi tạo chuỗi hash
            if (!"vnp_SecureHash".equals(entry.getKey())) {
                hashData.append(entry.getKey()).append("=").append(entry.getValue()).append("|");
            }
        }

        // Tính toán Secure Hash
        String secureHash = VNPayConfig.hmacSHA512(vnPayConfig.getSecretKey(), hashData.toString());
        String responseSecureHash = responseParams.get("vnp_SecureHash");

        return secureHash.equals(responseSecureHash);
    }


}
