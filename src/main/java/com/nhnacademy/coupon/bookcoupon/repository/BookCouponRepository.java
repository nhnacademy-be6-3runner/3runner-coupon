package com.nhnacademy.coupon.bookcoupon.repository;

import com.nhnacademy.coupon.entity.BookCoupon.BookCoupon;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookCouponRepository extends JpaRepository<BookCoupon, Long> {
}
