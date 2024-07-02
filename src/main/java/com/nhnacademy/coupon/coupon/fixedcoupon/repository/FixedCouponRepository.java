package com.nhnacademy.coupon.coupon.fixedcoupon.repository;

import com.nhnacademy.coupon.entity.coupontype.CouponType;
import com.nhnacademy.coupon.entity.fixedcoupon.FixedCoupon;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FixedCouponRepository extends JpaRepository<FixedCoupon, Long> {
    Optional<FixedCoupon> findByCouponType(CouponType couponType);
}
