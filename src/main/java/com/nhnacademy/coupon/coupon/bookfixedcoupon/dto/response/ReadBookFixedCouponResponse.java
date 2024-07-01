package com.nhnacademy.coupon.coupon.bookfixedcoupon.dto.response;

import lombok.Builder;

/**
 * 서비스내에서 사용 고정 북 쿠폰 Response DTO.
 *
 * @param bookFixedCouponId 고정 북 쿠폰 아이디
 * @param price 할인 가격
 * @param bookCouponId 북 쿠폰 아이디
 */
@Builder
public record ReadBookFixedCouponResponse(
        Long bookFixedCouponId,
        int price,
        Long bookCouponId) {
}
