package com.nhnacademy.coupon.coupon.couponform.dto.request;

import lombok.Builder;

@Builder
public record CreateFixedCouponRequest(int discountPrice) {
}
