package com.nhnacademy.coupon.coupon.bookratiocoupon.repository.impl;

import com.nhnacademy.coupon.coupon.bookratiocoupon.repository.BookRatioCouponCustomRepository;
import com.nhnacademy.coupon.coupon.bookratiocoupon.repository.BookRatioCouponRepository;
import com.nhnacademy.coupon.entity.bookcoupon.QBookCoupon;
import com.nhnacademy.coupon.entity.bookfixedcoupon.QBookFixedCoupon;
import com.nhnacademy.coupon.entity.bookratiocoupon.QBookRatioCoupon;
import com.nhnacademy.coupon.entity.couponform.QCouponForm;
import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * QueryDsl 북 비율 쿠폰 저장소 구현체.
 *
 * @author 김병우
 */
@Repository
public class BookRatioCouponCustomRepositoryImpl implements BookRatioCouponCustomRepository {
    private final JPAQueryFactory jpaQueryFactory;
    private final QCouponForm qCouponForm = QCouponForm.couponForm;
    private final QBookCoupon qBookCoupon = QBookCoupon.bookCoupon;
    private final QBookRatioCoupon qBookRatioCoupon = QBookRatioCoupon.bookRatioCoupon;

    public BookRatioCouponCustomRepositoryImpl(EntityManager entityManager) {
        this.jpaQueryFactory = new JPAQueryFactory(entityManager);
    }

    /**
     * 북 비율 쿠폰 정보 반환
     *
     * @param couponFormId 쿠폰 폼아이디
     * @return 북 비율 쿠폰 튜플
     */
    @Override
    public Optional<Tuple> findBookRatioCoupon(Long couponFormId) {

        return Optional.ofNullable(jpaQueryFactory
                .select(
                        qBookRatioCoupon.id,
                        qBookCoupon.id,
                        qBookCoupon.bookId,
                        qBookCoupon.couponForm.id,
                        qCouponForm.startDate,
                        qCouponForm.endDate,
                        qCouponForm.createdAt,
                        qCouponForm.name,
                        qCouponForm.code,
                        qBookRatioCoupon.discountRate,
                        qBookRatioCoupon.maxDiscount,
                        qCouponForm.maxPrice,
                        qCouponForm.minPrice
                )
                .from(qBookCoupon)
                .join(qBookCoupon.couponForm, qCouponForm)
                .join(qBookRatioCoupon).on(qBookRatioCoupon.bookCoupon.id.eq(qBookCoupon.id))
                .where(qCouponForm.id.eq(couponFormId))
                .fetchOne());
    }
}
