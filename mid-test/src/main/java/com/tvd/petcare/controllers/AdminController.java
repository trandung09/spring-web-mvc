package com.tvd.petcare.controllers;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admins")
@RequiredArgsConstructor
@Tag(name = "Admin-Controller")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AdminController {

    @GetMapping("/dashboard")
    public String index() {
        return "admin/index";
    }

    @GetMapping("/products")
    public String products() {
        return "admin/product-list";
    }
}
