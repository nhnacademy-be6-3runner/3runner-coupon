package com.nhnacademy.coupon.coupon.couponform.dto.request;

import lombok.Builder;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;

@Builder
public record ReadCouponFormRequest(
        List<Long> couponFormIds) {
}
