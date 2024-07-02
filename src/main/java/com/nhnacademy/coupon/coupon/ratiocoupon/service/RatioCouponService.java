package com.nhnacademy.coupon.coupon.ratiocoupon.service;

import com.nhnacademy.coupon.coupon.ratiocoupon.dto.response.ReadRatioCouponResponse;

public interface RatioCouponService {
    Long create(double discountRate, int discountMaxPrice);
    ReadRatioCouponResponse read(Long couponTypeId);
}
