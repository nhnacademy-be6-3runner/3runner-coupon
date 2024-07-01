package com.nhnacademy.coupon.coupon.couponform.controller;

import com.nhnacademy.coupon.coupon.couponform.service.CouponFormService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 쿠폰 폼 컨트롤러.
 *
 * @author 김병우
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/coupon")
public class CouponFormController {
    private final CouponFormService couponFormService;

}
