package com.nhnacademy.coupon.coupon.ratiocoupon.dto.response;

import lombok.Builder;

@Builder
public record ReadRatioCouponResponse(
        Long ratioCouponId,
        Long couponTypeId,
        double discountRate,
        int discountMaxPrice) {
}
