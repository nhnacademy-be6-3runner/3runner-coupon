package com.nhnacademy.coupon.coupon.categoryfixedcoupon.dto.response;

import lombok.Builder;

/**
 * 카테고리 고정 쿠폰 반환 Dto
 *
 * @param categoryFixedCouponId 카테고리 고정 쿠폰 아이디
 * @param price 쿠폰 가격
 * @param categoryCouponId 카테고리 쿠폰 아이디
 */
@Builder
public record ReadCategoryFixedCouponResponse(
        Long categoryFixedCouponId,
        int price,
        Long categoryCouponId) {
}
