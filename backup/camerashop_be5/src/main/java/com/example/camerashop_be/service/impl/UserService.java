package com.example.camerashop_be.service.impl;

import com.example.camerashop_be.converter.UserConverter;
import com.example.camerashop_be.dto.UserDTO;
import com.example.camerashop_be.dto.response.UserResponse;
import com.example.camerashop_be.entity.ERole;
import com.example.camerashop_be.entity.EStatus;
import com.example.camerashop_be.entity.Role;
import com.example.camerashop_be.entity.User;
import com.example.camerashop_be.repository.RoleRepo;
import com.example.camerashop_be.repository.UserRepo;
import com.example.camerashop_be.repository.specs.UserSpecification;
import com.example.camerashop_be.service.IUserService;
import net.bytebuddy.utility.RandomString;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.persistence.EntityNotFoundException;

import java.beans.FeatureDescriptor;
import java.io.UnsupportedEncodingException;
import java.time.Instant;
import java.util.*;
import java.util.stream.Stream;

@Service
public class UserService implements IUserService {
    private final Long refreshTokenDurationMs;  // Remove the 'final' keyword
    private final UserRepo userRepo;
    private final RoleRepo roleRepo;
    private final UserConverter userConverter;
    private final JavaMailSender mailSender;
    private final PasswordEncoder passwordEncoder;

    public UserService(
            @Value("${hiep.app.jwtRefreshExpirationMs:172800000}") Long refreshTokenDurationMs,
            UserRepo userRepo,
            RoleRepo roleRepo,
            UserConverter userConverter,
            JavaMailSender mailSender,
            PasswordEncoder passwordEncoder) {
        this.refreshTokenDurationMs = refreshTokenDurationMs;
        this.userRepo = userRepo;
        this.roleRepo = roleRepo;
        this.userConverter = userConverter;
        this.mailSender = mailSender;
        this.passwordEncoder = passwordEncoder;
    }

    public static final int MAX_FAILED_ATTEMPTS = 5;

    private static final long LOCK_TIME_DURATION = 60 * 15 * 1000; // 1 minutes

    @Override
    public Page<UserResponse> filter(UserSpecification userSpecification, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("id").descending());
        return userConverter.convertToResponse(userRepo.findAll(userSpecification, pageable));
    }

    @Override
    public Integer totalOrdersInDay(Date date) {
        return userRepo.totalOrdersInDay(date);
    }

    @Override
    public Integer totalOrders() {
        return userRepo.totalOrders();
    }

    @Override
    public UserResponse add(UserDTO userDTO) {

        if (userRepo.findByEmail(userDTO.getEmail()) != null) {
            throw new RuntimeException("Email already exists!");
        }
        List<Role> roles = new ArrayList<>();
        Arrays.stream(userDTO.getRoles()).forEach(role -> roles.add(roleRepo.findByName(role)));
        User user = userConverter.convertToEntity(userDTO);
        user.setRoles(roles);
        user.setPassword(encodedPassword(123456 + ""));
        user.setEnabled(false);
        user.setUserName(UUID.randomUUID().toString());
        String randomCode = RandomString.make(64);
        user.setVerificationCode(randomCode);
        sendVerificationEmail(user, "http://localhost:4200");
        User userAdded = userRepo.save(user);
        return userConverter.convertToResponse(userAdded);
    }

    @Override
    public UserResponse edit(UserDTO userDTO) {
        User user = userRepo.findById(userDTO.getId())
                .orElseThrow(() -> new EntityNotFoundException("Product " + userDTO.getId() + " does not exist!"));
        List<Role> roles = new ArrayList<>();
        Arrays.stream(userDTO.getRoles()).forEach(role -> roles.add(roleRepo.findByName(role)));
        BeanUtils.copyProperties(userDTO, user, "email");
        user.setRoles(roles);
        user.setPassword(encodedPassword(123456 + ""));
        user.setEnabled(true);
        user.setUserName(UUID.randomUUID().toString());
        User userAdded = userRepo.save(user);
        return userConverter.convertToResponse(userAdded);
    }

    @Override
    public List<UserDTO> getUsers() {
        return null;
    }

    @Override
    public User getUserByToken(String token) {
        User user = userRepo.findByToken(token);
        if (user.getExpiryDate().compareTo(Instant.now()) < 0) {
            user.setToken(null);
            user.setExpiryDate(null);
            userRepo.save(user);
            throw new TokenRefreshException(token, "Refresh token was expired. Please make a new signing request");
        }
        return user;
    }

    @Override
    public UserResponse getById(Long id) {
        User u = userRepo.findById(id).orElseThrow(() -> new EntityNotFoundException("User " + id + " does not exist!"));
        return userConverter.convertToResponse(u);
    }

    @Override
    public void register(UserDTO userDTO, String siteURL, boolean isSendMail) {
        User userCheck = userRepo.findByEmail(userDTO.getEmail());
        if (userCheck != null && !userCheck.getEnabled()) {
//			sendVerificationEmail(userConverter.convertToEntity(userDTO), siteURL);
//			return;
            throw new RuntimeException("Email already exists! Checkout your mail to verify!");
        }
        if (userCheck != null && userCheck.getEnabled()) {
            throw new RuntimeException("Email already exists!");
        }
       /* if (userRepo.existsByUsername(userDTO.getEmail())){
            throw new RuntimeException("Username đã được đăng ký!");
        }*/
        userDTO.setPassword(encodedPassword(userDTO.getPassword()));
        User user = userConverter.convertToEntity(userDTO);
        Role role = roleRepo.findByName(ERole.CLIENT.name());
        List<Role> roles = new ArrayList<>();
        roles.add(role);
        user.setRoles(roles);
//		user.getRoles().add(role);
        user.setStatus(1);
        user.setUserName(UUID.randomUUID().toString());
        user.setExpiryDate(Instant.now().plusMillis(refreshTokenDurationMs));
        user.setToken(UUID.randomUUID().toString());
        String randomCode = RandomString.make(64);
        user.setVerificationCode(randomCode);

        if (isSendMail) {
            user.setEnabled(false);
            sendVerificationEmail(user, siteURL);
        } else {
            user.setEnabled(true);
        }
        userRepo.save(user);
    }

    private String encodedPassword(String password) {
        return passwordEncoder.encode(password);
    }

    @Override
    public void forgotPassword(String email, String siteURL) {
        User user = userRepo.findByEmailAndStatus(email, 1);
        if (user == null) {
            throw new RuntimeException("Email does not exists!");
        }
        String randomCode = RandomString.make(64);
        user.setVerificationCode(randomCode);
        userRepo.save(user);
        sendMailForgotPassword(user, siteURL);
    }

    @Override
//	@Transactional
    public String getTokenByUserId(Long id) {
        User user = userRepo.findById(id).get();
        user.setExpiryDate(Instant.now().plusMillis(refreshTokenDurationMs));
        user.setToken(UUID.randomUUID().toString());
        userRepo.save(user);
        return user.getToken();
    }
//    public boolean verifyExpiration(String token,Instant expiryDate) {
//        if (expiryDate.compareTo(Instant.now()) < 0) {
//            refreshTokenRepository.delete(token);
//            throw new TokenRefreshException(token.getToken(), "Refresh token was expired. Please make a new signin request");
//        }
//
//        return token;
//    }

    @Override
    public Boolean verify(String verificationCode) {
        User user = userRepo.findByVerificationCode(verificationCode);

        if (user == null || user.getEnabled()) {
            return false;
        }
        user.setEnabled(true);
        user.setVerificationCode(null);
        userRepo.save(user);
        return true;

    }

    //	@Transactional
    @Override
    public void resetPassword(String verificationCode, String password) {
        User user = userRepo.findByVerificationCode(verificationCode);
        if (user == null) throw new RuntimeException("Account does not exist!");
        user.setPassword(encodedPassword(password));
        user.setVerificationCode(null);
        userRepo.save(user);
    }

    //	@Transactional
    @Override
    public void changePassword(String email, String password) {
        User user = userRepo.findByEmail(email);
        if (user == null) throw new RuntimeException("Account does not exist!");
        user.setPassword(encodedPassword(password));
        userRepo.save(user);
    }

    @Override
    public boolean checkExistByEmail(String email) {
        return userRepo.existsByEmail(email);
    }

    @Override
    public User getUserByEmail(String email) {
        return userRepo.findByEmail(email);
    }

    @Override
    public UserDTO update(UserDTO userDTO) {
        User user = userRepo.findById(userDTO.getId()).orElseThrow(() -> new EntityNotFoundException("User " + userDTO.getId() + " does not exist!"));
        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        User userSaved = userRepo.save(user);
        return userConverter.convertToDto(userSaved);
    }

    @Override
    public Page<UserResponse> getAll(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("id").descending());
        return userConverter.convertToResponse(userRepo.findAll(pageable));
    }

    @Override
    public Integer countByRoleName(String name) {
        return userRepo.countByRoleName(name);
    }


    private void sendMailForgotPassword(User user, String siteURL) {
        try {
            String toAddress = user.getEmail();
            System.out.println("Gửi tới: " + toAddress);
            String fromAddress = "testheva@gmail.com";
            String senderName = "FRUIT SHOP";
            String subject = "Reset your password.";
            String content = "Dear [[name]],<br>"
                    + "Please click the link below to reset your password:<br>"
                    + "<h3><a href=\"[[URL]]\" target=\"_self\">RESET NOW</a></h3>"
                    + "Thank you,<br>"
                    + "FRUIT SHOP.";

            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message);

            helper.setFrom(fromAddress, senderName);
            helper.setTo(toAddress);
            helper.setSubject(subject);

            content = content.replace("[[name]]", user.getLastName() + " " + user.getFirstName());
            String verifyURL = siteURL + "/auth/reset?code=" + user.getVerificationCode();

            content = content.replace("[[URL]]", verifyURL);

            helper.setText(content, true);
            mailSender.send(message);
        } catch (UnsupportedEncodingException | MessagingException e) {
            e.printStackTrace();
        }

    }

    private void sendVerificationEmail(User user, String siteURL) {
        try {
            String toAddress = user.getEmail();
            System.out.println("Send to: " + toAddress);
            String fromAddress = "testheva@gmail.com";
            String senderName = "FRUIT SHOP";
            String subject = "Please verify your registration";
            String content = "Dear [[name]],<br>"
                    + "Please click the link below to verify your registration:<br>"
                    + "<h3><a href=\"[[URL]]\" target=\"_self\">VERIFY</a></h3>"
                    + "Thank you,<br>"
                    + "FRUIT SHOP.";

            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message);

            helper.setFrom(fromAddress, senderName);
            helper.setTo(toAddress);
            helper.setSubject(subject);

            content = content.replace("[[name]]", user.getLastName() + " " + user.getFirstName());
            String verifyURL = siteURL + "/auth/verify?code=" + user.getVerificationCode();

            content = content.replace("[[URL]]", verifyURL);

            helper.setText(content, true);
            mailSender.send(message);
        } catch (UnsupportedEncodingException | MessagingException e) {
            e.printStackTrace();
        }

    }


    public static void myCopyProperties(Object src, Object target) {
        BeanUtils.copyProperties(src, target, getNullPropertyNames(src));
    }

    private static String[] getNullPropertyNames(Object source) {
        final BeanWrapper wrappedSource = new BeanWrapperImpl(source);
        return Stream.of(wrappedSource.getPropertyDescriptors())
                .map(FeatureDescriptor::getName)
                .filter(propertyName -> wrappedSource.getPropertyValue(propertyName) == null)
                .toArray(String[]::new);
    }


    public void increaseFailedAttempts(User user) {
        if (user.getFailedAttempt() == null || user.getFailedAttempt() == 0) {
            user.setFailedAttempt(1);
        } else {
            user.setFailedAttempt(user.getFailedAttempt() + 1);
        }
        userRepo.save(user);
    }

    public void resetFailedAttempts(String email) {
        User u = getUserByEmail(email);
        if (u.getFailedAttempt() != null && u.getFailedAttempt() > 0) {
            u.setFailedAttempt(0);
            userRepo.save(u);
        }
    }

    public void lock(User u) {
        u.setStatus(EStatus.INACTIVE.getName());
        u.setLockTime(new Date());
        userRepo.save(u);
    }

    public Boolean unlockWhenTimeExpired(User u) {
        Long lockTimeInMillis = null;
        if (u.getLockTime() != null) {
            lockTimeInMillis = u.getLockTime().getTime();
        }

        Long currentTimeInMillis = System.currentTimeMillis();

        if (lockTimeInMillis != null && lockTimeInMillis + LOCK_TIME_DURATION < currentTimeInMillis) {
            u.setStatus(EStatus.ACTIVE.getName());
            u.setLockTime(null);
            u.setFailedAttempt(0);
            userRepo.save(u);

            return true;
        }

        return false;
    }
}
