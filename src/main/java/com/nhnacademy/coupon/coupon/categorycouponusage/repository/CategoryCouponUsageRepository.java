package com.nhnacademy.coupon.coupon.categorycouponusage.repository;

import com.nhnacademy.coupon.entity.categorycouponusage.CategoryCouponUsage;
import com.nhnacademy.coupon.entity.couponusage.CouponUsage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CategoryCouponUsageRepository extends JpaRepository<CategoryCouponUsage, Long> {
    @Query("SELECT c.id FROM CategoryCouponUsage c WHERE c.couponUsage = :couponUsage")
    List<Long> findIdByCouponUsage(@Param("couponUsage") CouponUsage couponUsage);
}
