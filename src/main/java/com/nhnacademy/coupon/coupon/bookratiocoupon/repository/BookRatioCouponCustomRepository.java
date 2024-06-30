package com.nhnacademy.coupon.coupon.bookratiocoupon.repository;

import com.querydsl.core.Tuple;

import java.util.Optional;

/**
 * QueryDsl 북 비율 쿠폰 인터페이스.
 *
 * @author 김병우
 */
public interface BookRatioCouponCustomRepository {
    Optional<Tuple> findBookRatioCoupon(Long couponFormId);
}
