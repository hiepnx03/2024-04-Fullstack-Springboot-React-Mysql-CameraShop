package com.example.laptop_be.controller;

import com.example.laptop_be.dao.ProductRepository;
import com.example.laptop_be.entity.Product;
import com.example.laptop_be.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/products")
public class ProductController {


}
