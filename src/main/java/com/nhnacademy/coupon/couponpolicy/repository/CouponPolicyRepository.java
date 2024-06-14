package com.nhnacademy.coupon.couponpolicy.repository;

import com.nhnacademy.coupon.entity.couponpolicy.CouponPolicy;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CouponPolicyRepository extends JpaRepository<CouponPolicy, Long> {
}
