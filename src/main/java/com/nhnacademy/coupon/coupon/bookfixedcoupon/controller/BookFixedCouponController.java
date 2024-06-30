package com.nhnacademy.coupon.coupon.bookfixedcoupon.controller;

import com.nhnacademy.coupon.coupon.bookfixedcoupon.dto.request.ApiBookFixedCouponRequest;
import com.nhnacademy.coupon.coupon.bookfixedcoupon.dto.response.ApiBookFixedCouponResponse;
import com.nhnacademy.coupon.coupon.bookfixedcoupon.service.BookFixedCouponService;
import com.nhnacademy.coupon.global.util.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * 고정 북 쿠폰 컨트롤러.
 *
 * @author 김병우
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/coupon/bookFixes")
public class BookFixedCouponController {
    private final BookFixedCouponService bookFixedCouponService;


    /**
     * 고정 북 쿠폰 읽기.
     *
     * @param couponFormId 쿠폰폼아이디
     * @return ApiResponse ApiBookFixedCouponResponse
     */
    @GetMapping("/{couponFormId}")
    public ApiResponse<ApiBookFixedCouponResponse> readBookFixedCoupon(
            @PathVariable("couponFormId") Long couponFormId
    ) {
        return ApiResponse.success(bookFixedCouponService.readAllData(couponFormId));
    }

    /**
     * 고정 북 쿠폰 생성.
     *
     * @param apiBookFixedCouponRequest 요청 Dto
     * @return ApiResponse Long
     */
    @PostMapping()
    public ApiResponse<Long> createBookFixedCoupon(@RequestBody ApiBookFixedCouponRequest apiBookFixedCouponRequest){
        Long bookFixedCouponId = bookFixedCouponService.create(apiBookFixedCouponRequest);
        return ApiResponse.createSuccess(bookFixedCouponId);
    }

}
