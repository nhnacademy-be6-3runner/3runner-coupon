package com.nhnacademy.coupon.coupon.bookcoupon.repository;

import com.nhnacademy.coupon.entity.bookcoupon.BookCoupon;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 북쿠폰 리포지토리.
 *
 * @author 김병우
 */
public interface BookCouponRepository extends JpaRepository<BookCoupon, Long> {
}
