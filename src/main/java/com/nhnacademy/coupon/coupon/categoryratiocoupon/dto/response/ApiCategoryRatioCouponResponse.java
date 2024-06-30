package com.nhnacademy.coupon.coupon.categoryratiocoupon.dto.response;

import java.time.ZonedDateTime;
import java.util.UUID;
/**
 * 카테고리 비율 쿠폰 Response Dto
 *
 * @param categoryRatioCouponId 카테고리고정쿠폰아이디
 * @param categoryCouponId 카테고리쿠폰아이디
 * @param categoryId 카테고리아이디
 * @param couponFormId 쿠폰폼아이디
 * @param startDate 쿠폰시작일
 * @param endDate 쿠폰만료일
 * @param createdAt 쿠폰생성일
 * @param name 쿠폰이름
 * @param code 쿠폰코드
 * @param rate 쿠폰할인율
 * @param max 쿠폰최대할인가
 * @param maxPrice 쿠폰최대사용가격
 * @param minPrice 쿠폰최소사용가격
 */
public record ApiCategoryRatioCouponResponse(
        Long categoryRatioCouponId,
        Long categoryCouponId,
        Long categoryId,
        Long couponFormId,
        ZonedDateTime startDate,
        ZonedDateTime endDate,
        ZonedDateTime createdAt,
        String name,
        UUID code,
        Double rate,
        Long max,
        Integer maxPrice,
        Integer minPrice
) {
}
