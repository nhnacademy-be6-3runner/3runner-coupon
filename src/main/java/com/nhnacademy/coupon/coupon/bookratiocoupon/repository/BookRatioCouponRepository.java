package com.nhnacademy.coupon.coupon.bookratiocoupon.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nhnacademy.coupon.entity.bookratiocoupon.BookRatioCoupon;

/**
 * JPA 북 비율 쿠폰 저장소 인터페이스.
 *
 * @author 김병우
 */
public interface BookRatioCouponRepository extends JpaRepository<BookRatioCoupon, Long> {
}
