package com.nhnacademy.coupon.coupon.couponform.dto.request;

import lombok.Builder;

import java.time.ZonedDateTime;
import java.util.UUID;

@Builder
public record CreateCouponFormRequest(
        ZonedDateTime startDate,
        ZonedDateTime endDate,
        String name,
        Integer maxPrice,
        Integer minPrice,
        Long couponTypeId,
        Long couponUsageId) {
}
