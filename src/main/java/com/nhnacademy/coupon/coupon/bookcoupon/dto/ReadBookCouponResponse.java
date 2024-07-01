package com.nhnacademy.coupon.coupon.bookcoupon.dto;

import lombok.Builder;

/**
 * 북쿠폰 Response DTO.
 *
 * @author 김병운
 * @param bookCouponId 북쿠폰아이디
 * @param bookId 북아이디
 * @param couponFormId 쿠폰폼아이디
 */
@Builder
public record ReadBookCouponResponse(
        Long bookCouponId,
        Long bookId,
        Long couponFormId) {
}
