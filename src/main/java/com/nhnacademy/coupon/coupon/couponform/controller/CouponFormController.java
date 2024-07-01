package com.nhnacademy.coupon.coupon.couponform.controller;

import com.nhnacademy.coupon.coupon.couponform.dto.ReadCouponFormRequest;
import com.nhnacademy.coupon.coupon.couponform.dto.ReadCouponFormResponse;
import com.nhnacademy.coupon.coupon.couponform.service.CouponFormService;
import com.nhnacademy.coupon.global.util.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

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

    @PostMapping("/couponForms")
    public ApiResponse<List<ReadCouponFormResponse>> readPurchaseBook(
            @RequestBody ReadCouponFormRequest readCouponFormRequest
            ){
        return ApiResponse.success(couponFormService.readAll(readCouponFormRequest.couponFormIds()));
    }
}
