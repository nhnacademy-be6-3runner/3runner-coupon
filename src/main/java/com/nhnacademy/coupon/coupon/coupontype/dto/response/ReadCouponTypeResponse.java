package com.nhnacademy.coupon.coupon.coupontype.dto.response;

import lombok.Builder;

@Builder
public record ReadCouponTypeResponse(Long couponTypeId, String type) {
}
