package com.nhnacademy.coupon.coupon.categorycouponusage.service;

import java.util.List;

public interface CategoryCouponUsageService {
    Long create(List<Long> categoryIds);
    List<Long> readCategorys(Long couponUsageId);
}
