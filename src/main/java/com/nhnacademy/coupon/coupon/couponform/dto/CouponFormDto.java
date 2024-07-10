package com.nhnacademy.coupon.coupon.couponform.dto;

import lombok.Builder;

import java.time.ZonedDateTime;
import java.util.UUID;

@Builder
public record CouponFormDto(Long id, String name) {
    }
