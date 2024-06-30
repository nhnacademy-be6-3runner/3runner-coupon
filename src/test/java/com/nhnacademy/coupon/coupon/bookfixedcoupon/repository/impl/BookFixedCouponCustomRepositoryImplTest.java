package com.nhnacademy.coupon.coupon.bookfixedcoupon.repository.impl;

import com.nhnacademy.coupon.coupon.bookfixedcoupon.repository.BookFixedCouponCustomRepository;
import com.nhnacademy.coupon.entity.bookcoupon.BookCoupon;
import com.nhnacademy.coupon.entity.bookcoupon.QBookCoupon;
import com.nhnacademy.coupon.entity.bookfixedcoupon.BookFixedCoupon;
import com.nhnacademy.coupon.entity.bookfixedcoupon.QBookFixedCoupon;
import com.nhnacademy.coupon.entity.couponform.CouponForm;
import com.nhnacademy.coupon.entity.couponform.QCouponForm;
import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@Import(BookFixedCouponCustomRepositoryImpl.class)
class BookFixedCouponCustomRepositoryImplTest {

    @Autowired
    private BookFixedCouponCustomRepository bookFixedCouponCustomRepository;

    @PersistenceContext
    private EntityManager entityManager;

    @BeforeEach
    void setUp() {
        CouponForm couponForm = new CouponForm();
        couponForm.setStartDate(ZonedDateTime.now().minusDays(1));
        couponForm.setEndDate(ZonedDateTime.now().plusDays(1));
        couponForm.setName("Test Coupon");
        couponForm.setCode(UUID.randomUUID());
        couponForm.setMaxPrice(1000);
        couponForm.setMinPrice(100);

        entityManager.persist(couponForm);

        BookCoupon bookCoupon = new BookCoupon(couponForm, 1L);
        entityManager.persist(bookCoupon);

        BookFixedCoupon bookFixedCoupon = new BookFixedCoupon(bookCoupon, 500);
        entityManager.persist(bookFixedCoupon);

        entityManager.flush();
        entityManager.clear();
    }

    @Test
    void testFindBookFixedCoupon() {
        Long couponFormId = 1L; // This should match the ID of the inserted CouponForm
        Optional<Tuple> result = bookFixedCouponCustomRepository.findBookFixedCoupon(couponFormId);

        assertThat(result).isPresent();
        Tuple tuple = result.get();
        assertThat(tuple.get(QBookFixedCoupon.bookFixedCoupon.id)).isNotNull();
        assertThat(tuple.get(QBookCoupon.bookCoupon.id)).isNotNull();
        assertThat(tuple.get(QBookCoupon.bookCoupon.bookId)).isEqualTo(1L);
        assertThat(tuple.get(QCouponForm.couponForm.startDate)).isNotNull();
        assertThat(tuple.get(QCouponForm.couponForm.endDate)).isNotNull();
        assertThat(tuple.get(QCouponForm.couponForm.createdAt)).isNotNull();
        assertThat(tuple.get(QCouponForm.couponForm.name)).isEqualTo("Test Coupon");
        assertThat(tuple.get(QBookFixedCoupon.bookFixedCoupon.price)).isEqualTo(500);
        assertThat(tuple.get(QCouponForm.couponForm.maxPrice)).isEqualTo(1000);
        assertThat(tuple.get(QCouponForm.couponForm.minPrice)).isEqualTo(100);
    }
}