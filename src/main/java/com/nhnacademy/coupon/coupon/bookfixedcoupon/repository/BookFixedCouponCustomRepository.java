package com.nhnacademy.coupon.coupon.bookfixedcoupon.repository;

import com.querydsl.core.Tuple;

import java.util.Optional;

/**
 * QueryDsl 저장소 인터페이스.
 *
 * @author 김병우
 */
public interface BookFixedCouponCustomRepository {
    Optional<Tuple> findBookFixedCoupon(Long couponFormId);
}
