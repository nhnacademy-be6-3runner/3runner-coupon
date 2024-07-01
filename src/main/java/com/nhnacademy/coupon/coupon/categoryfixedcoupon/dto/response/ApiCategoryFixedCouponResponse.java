package com.nhnacademy.coupon.coupon.categoryfixedcoupon.dto.response;

import java.time.ZonedDateTime;
import java.util.UUID;

/**
 * 카테고리고정쿠폰 Response Dto
 *
 * @param categoryFixedCouponId 카테고리 고정 쿠폰 아이디
 * @param categoryCouponId 카테고리 고정 아이디
 * @param categoryId 카테고리 아이디
 * @param couponFormId 쿠폰 폼 아이디
 * @param startDate 쿠폰시작일
 * @param endDate 쿠폰만료일
 * @param createdAt 쿠폰생성일
 * @param name 쿠폰이름
 * @param code 쿠폰코드
 * @param price 쿠폰할인가격
 * @param maxPrice 쿠폰최대사용가격
 * @param minPrice 쿠폰최소사용가격
 */
public record ApiCategoryFixedCouponResponse(
        Long categoryFixedCouponId,
        Long categoryCouponId,
        Long categoryId,
        Long couponFormId,
        ZonedDateTime startDate,
        ZonedDateTime endDate,
        ZonedDateTime createdAt,
        String name,
        UUID code,
        int price,
        Integer maxPrice,
        Integer minPrice
) {
}
