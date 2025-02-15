//package com.example.demo.config;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.mail.javamail.JavaMailSender;
//import org.springframework.mail.javamail.JavaMailSenderImpl;
//
//@Configuration
//public class EmailConfig {
//
//    @Bean
//    public JavaMailSender javaMailSender() {
//        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
//        mailSender.setHost("smtp.gmail.com");
//        mailSender.setPort(587);
//        mailSender.setUsername("testheva@gmail.com"); // Replace with your email
//        mailSender.setPassword("qcix unak alaj nlsi"); // Replace with your email password
//        mailSender.getJavaMailProperties().put("mail.smtp.auth", "true");
//        mailSender.getJavaMailProperties().put("mail.smtp.starttls.enable", "true");
//        return mailSender;
//    }
//}
