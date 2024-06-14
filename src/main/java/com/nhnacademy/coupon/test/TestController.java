package com.nhnacademy.coupon.test;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/coupon")
public class TestController {
    @GetMapping()
    public String test() {
        return "coupon";
    }
}
