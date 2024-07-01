package com.nhnacademy.coupon.coupon.bookfixedcoupon.dto.response;

import lombok.Builder;

import java.time.ZonedDateTime;
import java.util.UUID;

/**
 * 고정 북 할인 쿠폰 Response DTO.
 *
 * @author 김병우.
 * @param bookFixedCouponId 고정북할인쿠폰 아이디
 * @param bookCouponId 북쿠폰 아이디
 * @param bookId 북아이디
 * @param couponFormId 쿠폰폼 아이디
 * @param startDate 쿠폰시작일
 * @param endDate 쿠폰만료일
 * @param createdAt 쿠폰 생성일
 * @param name 쿠폰 이름
 * @param code 쿠폰 코드
 * @param price 할인 금액
 * @param maxPrice 최대 사용 금액
 * @param minPrice 최소 사용 금액
 */

@Builder
public record ApiBookFixedCouponResponse(
        Long bookFixedCouponId,
        Long bookCouponId,
        Long bookId,
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
