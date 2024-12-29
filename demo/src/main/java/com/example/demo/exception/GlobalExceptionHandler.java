package com.example.demo.exception;

import com.example.demo.entity.ResponseObject;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.security.SignatureException;
import jakarta.persistence.EntityNotFoundException;
import org.apache.coyote.BadRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler extends RuntimeException {
    // Bắt lỗi nếu không tìm thấy đối tượng (ví dụ: không tìm thấy category)
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<String> handleEntityNotFound(EntityNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    // Bắt lỗi yêu cầu không hợp lệ
    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<String> handleBadRequest(BadRequestException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    // Bắt lỗi bất kỳ khác (generic error handler)
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleGenericException(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred: " + ex.getMessage());
    }

    @ExceptionHandler(BindException.class)
    public ResponseEntity<String> handleValidationExceptions(BindException ex) {
        // Lấy thông tin lỗi validation
        String errorMessage = ex.getBindingResult().getAllErrors().toString();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Validation failed: " + errorMessage);
    }

    @ExceptionHandler(JwtException.class)
    public ResponseEntity<ResponseObject> handleJwtException(JwtException ex) {
        // Xử lý ngoại lệ JWT
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(new ResponseObject("error", "JWT error: " + ex.getMessage(), null));
    }
    @ExceptionHandler(SignatureException.class)
    public ResponseEntity<ResponseObject> handleJwtSignatureException(SignatureException ex) {
        // Trả về mã lỗi 401 (Unauthorized) với thông báo lỗi
        ResponseObject response = new ResponseObject("error", "Invalid JWT signature: " + ex.getMessage(), null);
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
    }

}
