package com.nhnacademy.coupon.coupon.bookfixedcoupon.dto.request;

import jakarta.validation.constraints.NotNull;

import java.time.ZonedDateTime;
import java.util.UUID;

/**
 * 고정 북 할인 쿠폰 Request DTO.
 *
 * @author 김병우.
 * @param bookId 북아이디
 * @param startDate 쿠폰시작일
 * @param endDate 쿠폰만료일
 * @param createdAt 쿠폰 생성일
 * @param name 쿠폰 이름
 * @param code 쿠폰 코드
 * @param price 할인 금액
 * @param maxPrice 최대 사용 금액
 * @param minPrice 최소 사용 금액
 */
public record ApiBookFixedCouponRequest(
        @NotNull Long bookId,
        @NotNull ZonedDateTime startDate,
        @NotNull ZonedDateTime endDate,
        @NotNull ZonedDateTime createdAt,
        @NotNull String name,
        @NotNull UUID code,
        int price,
        @NotNull Integer maxPrice,
        @NotNull Integer minPrice
) {
}
