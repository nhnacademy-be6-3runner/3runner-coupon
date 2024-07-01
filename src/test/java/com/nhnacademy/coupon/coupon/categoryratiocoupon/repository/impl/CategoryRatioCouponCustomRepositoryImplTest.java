package com.nhnacademy.coupon.coupon.categoryratiocoupon.repository.impl;

import com.nhnacademy.coupon.coupon.categoryratiocoupon.repository.CategoryRatioCouponCustomRepository;
import com.nhnacademy.coupon.entity.categorycoupon.CategoryCoupon;
import com.nhnacademy.coupon.entity.categoryratiocoupon.CategoryRatioCoupon;
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
@Import(CategoryRatioCouponCustomRepositoryImpl.class)
class CategoryRatioCouponCustomRepositoryImplTest {

    @Autowired
    private CategoryRatioCouponCustomRepository categoryRatioCouponCustomRepository;

    @PersistenceContext
    private EntityManager entityManager;

    @BeforeEach
    void setUp() {
        CouponForm couponForm = new CouponForm();
        couponForm.setStartDate(ZonedDateTime.now().minusDays(1));
        couponForm.setEndDate(ZonedDateTime.now().plusDays(1));
        couponForm.setCreatedAt(ZonedDateTime.now());
        couponForm.setName("Test Coupon");
        couponForm.setCode(UUID.randomUUID());
        couponForm.setMaxPrice(1000);
        couponForm.setMinPrice(100);

        entityManager.persist(couponForm);

        CategoryCoupon categoryCoupon = new CategoryCoupon(couponForm, 1L);
        entityManager.persist(categoryCoupon);

        CategoryRatioCoupon categoryRatioCoupon = new CategoryRatioCoupon(categoryCoupon, 0.2, 200L);
        entityManager.persist(categoryRatioCoupon);

        entityManager.flush();
        entityManager.clear();
    }

    @Test
    void testFindCategoryRatioCoupon() {
        Long couponFormId = 1L; // This should match the ID of the inserted CouponForm
        Optional<Tuple> result = categoryRatioCouponCustomRepository.findCategoryRatioCoupon(couponFormId);

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
        assertThat(tuple.get(9, Double.class)).isEqualTo(0.2);
        assertThat(tuple.get(10, Long.class)).isEqualTo(200);
        assertThat(tuple.get(11, Integer.class)).isEqualTo(1000);
        assertThat(tuple.get(12, Integer.class)).isEqualTo(100);
    }
}