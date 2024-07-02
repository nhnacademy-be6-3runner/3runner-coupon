package com.nhnacademy.coupon.coupon.couponusage.dto.response;

import lombok.Builder;
@Builder
public record ReadCouponUsageResponse(Long couponUsageId, String usage) {
}
