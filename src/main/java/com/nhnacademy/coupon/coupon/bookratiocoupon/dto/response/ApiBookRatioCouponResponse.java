package com.nhnacademy.coupon.coupon.bookratiocoupon.dto.response;

import lombok.Builder;

import java.time.ZonedDateTime;
import java.util.UUID;
/**
 * 북 비율 쿠폰 Response Dto
 *
 * @param bookRatioCouponId 북 비율 쿠폰 아이디
 * @param bookCouponId 북 쿠폰 아이디
 * @param bookId 북 아이디
 * @param couponFormId 쿠폰 폼 아이디
 * @param startDate 시작일
 * @param endDate 만료일
 * @param createdAt 생성일
 * @param name 쿠폰이름
 * @param code 쿠폰 코드
 * @param rate 쿠폰 할인율
 * @param max 쿠폰 최대 할인 가격
 * @param maxPrice 쿠폰 적용 최대 금액
 * @param minPrice 쿠폰 적용 최소 금액
 */
@Builder
public record ApiBookRatioCouponResponse(
        Long bookRatioCouponId,
        Long bookCouponId,
        Long bookId,
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
