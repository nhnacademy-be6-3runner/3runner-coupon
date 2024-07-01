package com.nhnacademy.coupon.coupon.categoryratiocoupon.repository;

import com.nhnacademy.coupon.entity.categoryratiocoupon.CategoryRatioCoupon;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * JPA 카테고리 비율 쿠폰 저장소 인터페이스.
 *
 * @author 김병우
 */
public interface CategoryRatioCouponRepository extends JpaRepository<CategoryRatioCoupon, Long> {
}
