package com.nhnacademy.coupon.coupon.bookratiocoupon.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.time.ZonedDateTime;
import java.util.UUID;

/**
 * 북 비율 쿠폰 Request Dto
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
public record ApiBookRatioCouponRequest(
        @NotNull Long bookId,
        @NotNull ZonedDateTime startDate,
        @NotNull ZonedDateTime endDate,
        @NotNull ZonedDateTime createdAt,
        @NotNull String name,
        @NotNull UUID code,
        @NotNull Double rate,
        @NotNull Long max,
        @NotNull Integer maxPrice,
        @NotNull Integer minPrice
) {
}
