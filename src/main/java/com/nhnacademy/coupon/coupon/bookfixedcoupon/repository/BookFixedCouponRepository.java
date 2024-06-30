package com.nhnacademy.coupon.coupon.bookfixedcoupon.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nhnacademy.coupon.entity.bookfixedcoupon.BookFixedCoupon;

/**
 * 고정 북 쿠폰 JPA 저장소.
 *
 * @author 김병우
 */
public interface BookFixedCouponRepository extends JpaRepository<BookFixedCoupon, Long> {
}
