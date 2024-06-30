package com.nhnacademy.coupon.coupon.categoryfixedcoupon.controller;

import com.nhnacademy.coupon.coupon.categoryfixedcoupon.dto.request.ApiCategoryFixedCouponRequest;
import com.nhnacademy.coupon.coupon.categoryfixedcoupon.dto.response.ApiCategoryFixedCouponResponse;
import com.nhnacademy.coupon.coupon.categoryfixedcoupon.service.CategoryFixedCouponService;
import com.nhnacademy.coupon.global.util.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * 카테고리 고정 쿠폰 컨트롤러.
 *
 * @author 김병우
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/coupon/categoryFixes")
public class CategoryFixedCouponController {
    private final CategoryFixedCouponService categoryFixedCouponService;

    /**
     * 카테고리 고정 쿠폰 읽기.
     *
     * @param couponFormId 쿠폰폼아이디
     * @return 반환 Dto
     */
    @GetMapping("/{couponFormId}")
    public ApiResponse<ApiCategoryFixedCouponResponse> readCategoryFixedCoupon(
            @PathVariable("couponFormId") Long couponFormId
    ) {
        return ApiResponse.success(categoryFixedCouponService.readAllData(couponFormId));
    }

    /**
     * 카테고리 고정 쿠폰 생성.
     *
     * @param apiCategoryFixedCouponRequest 요청 Dto.
     * @return 카테고리 고정 쿠폰 아이디
     */
    @PostMapping()
    public ApiResponse<Long> createCategoryFixedCoupon(@RequestBody ApiCategoryFixedCouponRequest apiCategoryFixedCouponRequest){
        Long categoryFixedCouponId = categoryFixedCouponService.create(apiCategoryFixedCouponRequest);
        return ApiResponse.createSuccess(categoryFixedCouponId);
    }

}
