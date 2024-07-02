package com.nhnacademy.coupon.coupon.bookcouponusage.service;

import java.util.List;

public interface BookCouponUsageService {
    Long create(List<Long> bookIds);
    List<Long> readBooks(Long couponUsageId);
}
