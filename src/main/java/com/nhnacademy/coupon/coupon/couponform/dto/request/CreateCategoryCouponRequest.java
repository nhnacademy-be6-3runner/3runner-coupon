package com.nhnacademy.coupon.coupon.couponform.dto.request;

import lombok.Builder;

import java.util.List;

@Builder
public record CreateCategoryCouponRequest(List<Long> categoryIds) {
}
