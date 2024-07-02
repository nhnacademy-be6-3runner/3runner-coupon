package com.nhnacademy.coupon.coupon.ratiocoupon.repository;

import com.nhnacademy.coupon.entity.coupontype.CouponType;
import com.nhnacademy.coupon.entity.fixedcoupon.FixedCoupon;
import com.nhnacademy.coupon.entity.ratiocoupon.RatioCoupon;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RatioCouponRepository extends JpaRepository<RatioCoupon, Long> {
    Optional<RatioCoupon> findByCouponType(CouponType couponType);
}
