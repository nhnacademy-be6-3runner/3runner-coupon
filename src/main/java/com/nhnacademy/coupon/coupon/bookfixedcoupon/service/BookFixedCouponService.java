package com.nhnacademy.coupon.coupon.bookfixedcoupon.service;

import com.nhnacademy.coupon.coupon.bookfixedcoupon.dto.request.ApiBookFixedCouponRequest;
import com.nhnacademy.coupon.coupon.bookfixedcoupon.dto.response.ApiBookFixedCouponResponse;
import com.nhnacademy.coupon.coupon.bookfixedcoupon.dto.response.ReadBookFixedCouponResponse;
import com.nhnacademy.coupon.entity.bookfixedcoupon.BookFixedCoupon;
import com.querydsl.core.Tuple;

import java.util.List;

public interface BookFixedCouponService {
    Long create(ApiBookFixedCouponRequest apiBookFixedCouponRequest);
    ReadBookFixedCouponResponse read(Long bookFixedCouponId);
    ApiBookFixedCouponResponse readAllData(Long couponFormId);
}
