package com.nhnacademy.coupon.coupon.couponform.service;

import com.nhnacademy.coupon.entity.couponform.CouponForm;

import java.time.ZonedDateTime;
import java.util.UUID;

/**
 * 쿠폰 폼 서비스 인터페이스.
 *
 * @author 김병우
 */
public interface CouponFormService {
    Long create(ZonedDateTime startDate, ZonedDateTime endDate, String name, UUID code, Integer maxPrice, Integer minPrice);
    CouponForm read(Long couponFormId);
    void sendNoticeCouponsExpiringToday();
    void sendNoticeCouponsExpiringThreeDaysLater();
}
