package com.tvd.petcare.controllers;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("")
@RequiredArgsConstructor
@Tag(name = "Customer-Controller")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CustomerController {

    @GetMapping("")
    public String index() {
        return "index";
    }
}
