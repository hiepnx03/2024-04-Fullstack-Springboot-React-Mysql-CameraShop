package com.example.camerashop_be.service;


import com.example.camerashop_be.dto.UserDTO;
import com.example.camerashop_be.dto.response.UserResponse;
import com.example.camerashop_be.entity.User;
import com.example.camerashop_be.repository.specs.UserSpecification;
import org.springframework.data.domain.Page;

import java.util.Date;
import java.util.List;

public interface IUserService {
    UserResponse add(UserDTO userDTO);

    UserResponse edit(UserDTO userDTO);

    List<UserDTO> getUsers();

    User getUserByToken(String token);

    //	UserDTO getUserById(long id);
    UserResponse getById(Long id);

    String getTokenByUserId(Long id);

    void register(UserDTO userDTO, String siteURL, boolean isSendMail);

    void forgotPassword(String email, String siteURL);

    Boolean verify(String verificationCode);

    void resetPassword(String verificationCode, String password);

    void changePassword(String email, String password);

    boolean checkExistByEmail(String email);

    User getUserByEmail(String email);

    UserDTO update(UserDTO userDTO);

    Page<UserResponse> getAll(int page, int size);

    Integer countByRoleName(String name);

    Page<UserResponse> filter(UserSpecification userSpecification, int page, int size);

    Integer totalOrdersInDay(Date date);

    Integer totalOrders();

    void increaseFailedAttempts(User user);

    void resetFailedAttempts(String email);

    void lock(User user);

    Boolean unlockWhenTimeExpired(User user);
}
