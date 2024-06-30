package com.nhnacademy.coupon.coupon.categoryfixedcoupon.repository;

import com.nhnacademy.coupon.entity.categoryfixedcoupon.CategoryFixedCoupon;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * JPA 카테고리 고정 쿠폰 저장소 인터페이스.
 *
 * @author 김병우
 */
public interface CategoryFixedCouponRepository extends JpaRepository<CategoryFixedCoupon, Long> {
}
