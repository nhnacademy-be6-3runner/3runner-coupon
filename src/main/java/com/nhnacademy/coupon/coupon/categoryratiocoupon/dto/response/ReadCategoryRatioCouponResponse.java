package com.nhnacademy.coupon.coupon.categoryratiocoupon.dto.response;

import lombok.Builder;

/**
 * 카테도리 비율 쿠폰 Response Dto
 *
 * @param categoryRatioCouponId 카테고리 비율 쿠폰 아이디
 * @param rate 쿠폰 할인율
 * @param max 최대 쿠폰 할인가
 * @param categoryCouponId 카테고리쿠폰 아이디
 */
@Builder
public record ReadCategoryRatioCouponResponse(
        Long categoryRatioCouponId,
        double rate,
        Long max,
        Long categoryCouponId) {
}
