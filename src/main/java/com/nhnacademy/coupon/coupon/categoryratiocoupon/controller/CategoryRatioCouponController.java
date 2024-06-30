package com.nhnacademy.coupon.coupon.categoryratiocoupon.controller;

import com.nhnacademy.coupon.coupon.categoryratiocoupon.dto.request.ApiCategoryRatioCouponRequest;
import com.nhnacademy.coupon.coupon.categoryratiocoupon.dto.response.ApiCategoryRatioCouponResponse;
import com.nhnacademy.coupon.coupon.categoryratiocoupon.service.CategoryRatioCouponService;
import com.nhnacademy.coupon.global.util.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * 카테고리 비율 쿠폰 컨트롤러.
 *
 * @author 김병우
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/coupon/categoryRatios")
public class CategoryRatioCouponController {
    private final CategoryRatioCouponService categoryRatioCouponService;

    /**
     * 카테고리 비율 쿠폰 반환.
     *
     * @param couponFormId 쿠폰폼아이디
     * @return 반환Dto
     */
    @GetMapping("/{couponFormId}")
    public ApiResponse<ApiCategoryRatioCouponResponse> readCategoryRatioCoupon(
            @PathVariable("couponFormId") Long couponFormId
    ) {
        return ApiResponse.success(categoryRatioCouponService.readAllData(couponFormId));
    }

    /**
     * 카테고리 비율 쿠폰 생성.
     *
     * @param apiCategoryRatioCouponRequest 요청Dto
     * @return 카테고리 비율 쿠폰 아이디
     */
    @PostMapping()
    public ApiResponse<Long> createCategoryRatioCoupon(@RequestBody ApiCategoryRatioCouponRequest apiCategoryRatioCouponRequest){
        Long categoryRatioCouponId = categoryRatioCouponService.create(apiCategoryRatioCouponRequest);
        return ApiResponse.createSuccess(categoryRatioCouponId);
    }
}
