package com.nhnacademy.coupon.coupon.categoryfixedcoupon.repository.impl;

import com.nhnacademy.coupon.coupon.categoryfixedcoupon.repository.CategoryFixedCouponCustomRepository;
import com.nhnacademy.coupon.entity.categorycoupon.QCategoryCoupon;
import com.nhnacademy.coupon.entity.categoryfixedcoupon.QCategoryFixedCoupon;
import com.nhnacademy.coupon.entity.couponform.QCouponForm;
import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * QueryDsl 카테고리 고정 쿠폰 저장소 구현체.
 *
 * @author 김병우
 */
@Slf4j
@Repository
public class CategoryFixedCouponCustomRepositoryImpl implements CategoryFixedCouponCustomRepository {
    private final JPAQueryFactory jpaQueryFactory;
    private final QCouponForm qCouponForm = QCouponForm.couponForm;
    private final QCategoryCoupon qCategoryCoupon = QCategoryCoupon.categoryCoupon;
    private final QCategoryFixedCoupon qCategoryFixedCoupon = QCategoryFixedCoupon.categoryFixedCoupon;

    public CategoryFixedCouponCustomRepositoryImpl(EntityManager entityManager) {
        this.jpaQueryFactory = new JPAQueryFactory(entityManager);
    }

    @Override
    public Optional<Tuple> findCategoryFixedCoupon(Long couponFormId) {
        return Optional.ofNullable(jpaQueryFactory
                .select(
                        qCategoryFixedCoupon.id,
                        qCategoryCoupon.id,
                        qCategoryCoupon.categoryId,
                        qCategoryCoupon.couponForm.id,
                        qCouponForm.startDate,
                        qCouponForm.endDate,
                        qCouponForm.createdAt,
                        qCouponForm.name,
                        qCouponForm.code,
                        qCategoryFixedCoupon.price,
                        qCouponForm.maxPrice,
                        qCouponForm.minPrice
                )
                .from(qCategoryCoupon)
                .join(qCategoryCoupon.couponForm, qCouponForm)
                .join(qCategoryFixedCoupon).on(qCategoryFixedCoupon.categoryCoupon.id.eq(qCategoryCoupon.id))
                .where(qCouponForm.id.eq(couponFormId))
                .fetchOne());
    }
}
