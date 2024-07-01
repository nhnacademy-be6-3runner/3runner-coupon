package com.nhnacademy.coupon.coupon.bookratiocoupon.service;

import com.nhnacademy.coupon.coupon.bookratiocoupon.dto.request.ApiBookRatioCouponRequest;
import com.nhnacademy.coupon.coupon.bookratiocoupon.dto.response.ApiBookRatioCouponResponse;
import com.nhnacademy.coupon.coupon.bookratiocoupon.dto.response.ReadBookRatioCouponResponse;
import com.nhnacademy.coupon.entity.bookratiocoupon.BookRatioCoupon;

public interface BookRatioCouponService {
    Long create(ApiBookRatioCouponRequest apiBookRatioCouponRequest);
    ReadBookRatioCouponResponse read(Long bookRatioCouponId);
    ApiBookRatioCouponResponse readAllData(Long bookRatioCouponId);
}
