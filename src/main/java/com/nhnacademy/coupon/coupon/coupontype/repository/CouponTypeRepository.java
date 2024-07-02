package com.nhnacademy.coupon.coupon.coupontype.repository;

import com.nhnacademy.coupon.entity.coupontype.CouponType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CouponTypeRepository extends JpaRepository<CouponType, Long> {
}
