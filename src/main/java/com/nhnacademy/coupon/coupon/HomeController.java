package com.nhnacademy.coupon.coupon;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {
    @GetMapping("/coupon")
    public String home(){
        return "coupon_server";
    }
}