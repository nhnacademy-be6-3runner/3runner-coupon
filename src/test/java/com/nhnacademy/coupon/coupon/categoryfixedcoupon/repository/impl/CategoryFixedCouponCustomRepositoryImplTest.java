package com.nhnacademy.coupon.coupon.categoryfixedcoupon.repository.impl;

import com.nhnacademy.coupon.coupon.categoryfixedcoupon.repository.CategoryFixedCouponCustomRepository;
import com.nhnacademy.coupon.entity.categorycoupon.CategoryCoupon;
import com.nhnacademy.coupon.entity.categoryfixedcoupon.CategoryFixedCoupon;
import com.nhnacademy.coupon.entity.couponform.CouponForm;
import com.querydsl.core.Tuple;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.ZonedDateTime;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@Import(CategoryFixedCouponCustomRepositoryImpl.class)
class CategoryFixedCouponCustomRepositoryImplTest {

    @Autowired
    private CategoryFixedCouponCustomRepository categoryFixedCouponCustomRepository;

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

        CategoryCoupon categoryCoupon = new CategoryCoupon(couponForm, 1L);
        entityManager.persist(categoryCoupon);

        CategoryFixedCoupon categoryFixedCoupon = new CategoryFixedCoupon(categoryCoupon, 500);
        entityManager.persist(categoryFixedCoupon);

        entityManager.flush();
        entityManager.clear();
    }

    @Test
    void testFindCategoryFixedCoupon() {
        Long couponFormId = 1L; // This should match the ID of the inserted CouponForm
        Optional<Tuple> result = categoryFixedCouponCustomRepository.findCategoryFixedCoupon(couponFormId);

        assertThat(result).isPresent();
        Tuple tuple = result.get();
        assertThat(tuple.get(0, Long.class)).isNotNull();
        assertThat(tuple.get(1, Long.class)).isNotNull();
        assertThat(tuple.get(2, Long.class)).isEqualTo(1L);
        assertThat(tuple.get(3, Long.class)).isNotNull();
        assertThat(tuple.get(4, ZonedDateTime.class)).isNotNull();
        assertThat(tuple.get(5, ZonedDateTime.class)).isNotNull();
        assertThat(tuple.get(6, ZonedDateTime.class)).isNotNull();
        assertThat(tuple.get(7, String.class)).isEqualTo("Test Coupon");
        assertThat(tuple.get(9, Integer.class)).isEqualTo(500);
        assertThat(tuple.get(10, Integer.class)).isEqualTo(1000);
        assertThat(tuple.get(11, Integer.class)).isEqualTo(100);
    }
}