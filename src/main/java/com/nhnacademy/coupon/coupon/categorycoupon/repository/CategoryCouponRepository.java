package com.nhnacademy.coupon.coupon.categorycoupon.repository;

import com.nhnacademy.coupon.entity.categorycoupon.CategoryCoupon;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 카테고리 쿠폰 저장소 인터페이스.
 *
 * @author 김병우
 */
public interface CategoryCouponRepository extends JpaRepository<CategoryCoupon, Long> {
}
