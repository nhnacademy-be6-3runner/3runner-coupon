package com.nhnacademy.coupon.coupon.categoryratiocoupon.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.time.ZonedDateTime;
import java.util.UUID;

/**
 * 카테고리 비율 쿠폰 Request Dto
 *
 * @param categoryId 카테고리아이디
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
@Builder
public record ApiCategoryRatioCouponRequest(
        @NotNull Long categoryId,
        @NotNull ZonedDateTime startDate,
        @NotNull ZonedDateTime endDate,
        @NotNull ZonedDateTime createdAt,
        @NotNull String name,
        @NotNull UUID code,
        @NotNull double rate,
        @NotNull Long max,
        @NotNull Integer maxPrice,
        @NotNull Integer minPrice
) {
}
