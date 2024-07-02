package com.nhnacademy.coupon.coupon.coupontype.service;

import com.nhnacademy.coupon.coupon.coupontype.dto.response.ReadCouponTypeResponse;

import java.util.List;

public interface CouponTypeService {
    List<ReadCouponTypeResponse> readAll();
}
