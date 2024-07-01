package com.nhnacademy.coupon.coupon.bookcoupon.service;

import com.nhnacademy.coupon.entity.bookcoupon.BookCoupon;
import com.nhnacademy.coupon.entity.couponform.CouponForm;

public interface BookCouponService {
    Long create(CouponForm couponForm, long bookId);
    BookCoupon read(Long bookCouponId);
}
