package com.nhnacademy.coupon.coupon.fixedcoupon.dto.response;

import lombok.Builder;

@Builder
public record ReadFixedCouponResponse(
        Long fixedCouponId,
        Long couponTypeId,
        int discountPrice) {
}
