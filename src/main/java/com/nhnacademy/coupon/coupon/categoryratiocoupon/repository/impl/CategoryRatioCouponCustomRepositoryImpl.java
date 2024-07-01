package com.nhnacademy.coupon.coupon.categoryratiocoupon.repository.impl;

import com.nhnacademy.coupon.coupon.categoryratiocoupon.repository.CategoryRatioCouponCustomRepository;
import com.nhnacademy.coupon.entity.categorycoupon.QCategoryCoupon;
import com.nhnacademy.coupon.entity.categoryratiocoupon.QCategoryRatioCoupon;
import com.nhnacademy.coupon.entity.couponform.QCouponForm;
import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * QueryDsl 카테고리 비율 쿠폰 저장소 구현체.
 *
 * @author 김병우
 */
@Slf4j
@Repository
public class CategoryRatioCouponCustomRepositoryImpl implements CategoryRatioCouponCustomRepository {
    private final JPAQueryFactory jpaQueryFactory;
    private final QCouponForm qCouponForm = QCouponForm.couponForm;
    private final QCategoryCoupon qCategoryCoupon = QCategoryCoupon.categoryCoupon;
    private final QCategoryRatioCoupon qCategoryRatioCoupon = QCategoryRatioCoupon.categoryRatioCoupon;

    public CategoryRatioCouponCustomRepositoryImpl(EntityManager entityManager) {
        this.jpaQueryFactory = new JPAQueryFactory(entityManager);
    }

    /**
     * 카테고리 비율 쿠폰 튜플 반환
     *
     * @param couponFormId 쿠폰폼아이디
     * @return 튜플반환
     */
    @Override
    public Optional<Tuple> findCategoryRatioCoupon(Long couponFormId) {
        return Optional.ofNullable(jpaQueryFactory
                .select(
                        qCategoryRatioCoupon.id,
                        qCategoryCoupon.id,
                        qCategoryCoupon.categoryId,
                        qCategoryCoupon.couponForm.id,
                        qCouponForm.startDate,
                        qCouponForm.endDate,
                        qCouponForm.createdAt,
                        qCouponForm.name,
                        qCouponForm.code,
                        qCategoryRatioCoupon.discountRate,
                        qCategoryRatioCoupon.maxDiscount,
                        qCouponForm.maxPrice,
                        qCouponForm.minPrice
                )
                .from(qCategoryCoupon)
                .join(qCategoryCoupon.couponForm, qCouponForm)
                .join(qCategoryRatioCoupon).on(qCategoryRatioCoupon.categoryCoupon.id.eq(qCategoryCoupon.id))
                .where(qCouponForm.id.eq(couponFormId))
                .fetchOne());
    }
}
