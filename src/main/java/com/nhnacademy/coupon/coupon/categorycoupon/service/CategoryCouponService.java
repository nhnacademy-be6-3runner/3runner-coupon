package com.nhnacademy.coupon.coupon.categorycoupon.service;

import com.nhnacademy.coupon.coupon.categorycoupon.dto.ReadCategoryCouponResponse;
import com.nhnacademy.coupon.entity.categorycoupon.CategoryCoupon;
import com.nhnacademy.coupon.entity.couponform.CouponForm;

public interface CategoryCouponService {
    Long create(CouponForm couponForm, Long categoryId);
    CategoryCoupon read(Long categoryCouponId);
}
