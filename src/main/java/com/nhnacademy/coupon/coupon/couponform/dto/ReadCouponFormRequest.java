package com.nhnacademy.coupon.coupon.couponform.dto;

import lombok.Value;

import java.util.List;

public record ReadCouponFormRequest(List<Long> couponFormIds) {
}
