package com.nhnacademy.coupon.coupon.bookfixedcoupon.repository.impl;

import com.nhnacademy.coupon.coupon.bookfixedcoupon.repository.BookFixedCouponCustomRepository;
import com.nhnacademy.coupon.entity.bookcoupon.QBookCoupon;
import com.nhnacademy.coupon.entity.bookfixedcoupon.QBookFixedCoupon;
import com.nhnacademy.coupon.entity.couponform.QCouponForm;
import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import java.util.Optional;

/**
 * 고정 북 쿠폰 QueryDsl 저장소 구현체.
 *
 * @author 김병우
 */
@Slf4j
@Repository
public class BookFixedCouponCustomRepositoryImpl implements BookFixedCouponCustomRepository {
    private final JPAQueryFactory jpaQueryFactory;
    private final QCouponForm qCouponForm = QCouponForm.couponForm;
    private final QBookCoupon qBookCoupon = QBookCoupon.bookCoupon;
    private final QBookFixedCoupon qBookFixedCoupon = QBookFixedCoupon.bookFixedCoupon;

    public BookFixedCouponCustomRepositoryImpl(EntityManager entityManager) {
        this.jpaQueryFactory = new JPAQueryFactory(entityManager);
    }

    /**
     * 고정 북 할인 쿠폰에 대한 모든 정보 읽기.
     *
     * @param couponFormId 북쿠폰 아이디
     * @return 고정 북 할인 쿠폰 튜플
     */
    @Override
    public Optional<Tuple> findBookFixedCoupon(Long couponFormId) {
        return Optional.ofNullable(jpaQueryFactory
                .select(
                        qBookFixedCoupon.id,
                        qBookCoupon.id,
                        qBookCoupon.bookId,
                        qBookCoupon.couponForm.id,
                        qCouponForm.startDate,
                        qCouponForm.endDate,
                        qCouponForm.createdAt,
                        qCouponForm.name,
                        qCouponForm.code,
                        qBookFixedCoupon.price,
                        qCouponForm.maxPrice,
                        qCouponForm.minPrice
                )
                .from(qBookCoupon)
                .join(qBookCoupon.couponForm, qCouponForm)
                .join(qBookFixedCoupon).on(qBookFixedCoupon.bookCoupon.id.eq(qBookCoupon.id))
                .where(qCouponForm.id.eq(couponFormId))
                .fetchOne());
    }
}
