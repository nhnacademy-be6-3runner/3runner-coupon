package com.nhnacademy.coupon.coupon.fixedcoupon.service;

import com.nhnacademy.coupon.coupon.fixedcoupon.dto.response.ReadFixedCouponResponse;
import com.nhnacademy.coupon.entity.coupontype.CouponType;
import com.nhnacademy.coupon.entity.fixedcoupon.FixedCoupon;

public interface FixedCouponService {
    Long create(int discountPrice);
    ReadFixedCouponResponse read(Long couponTypeId);
}
