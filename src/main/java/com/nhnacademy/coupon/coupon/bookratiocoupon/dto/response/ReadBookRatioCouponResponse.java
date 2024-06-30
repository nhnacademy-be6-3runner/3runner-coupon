package com.nhnacademy.coupon.coupon.bookratiocoupon.dto.response;

import lombok.Builder;

/**
 * 서비스 내에서 사용 Response Dto
 *
 * @param bookRatioCouponId 북 비율 쿠폰 아이디
 * @param rate 쿠폰 할인율
 * @param max 쿠폰 최대 할인가
 * @param bookCouponId 북 쿠폰 아이디
 */
@Builder
public record ReadBookRatioCouponResponse(
        Long bookRatioCouponId,
        double rate,
        Long max,
        Long bookCouponId) {
}
