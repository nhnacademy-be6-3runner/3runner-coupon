package com.nhnacademy.coupon.coupon.categoryratiocoupon.repository;

import com.querydsl.core.Tuple;

import java.util.List;
import java.util.Optional;
/**
 * QueryDsl 카테고리 비율 쿠폰 저장소 인터페이스.
 *
 * @author 김병우
 */
public interface CategoryRatioCouponCustomRepository {
    Optional<Tuple> findCategoryRatioCoupon(Long couponFormId);
}
