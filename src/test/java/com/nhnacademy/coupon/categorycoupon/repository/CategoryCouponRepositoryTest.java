package com.nhnacademy.coupon.categorycoupon.repository;


import com.nhnacademy.coupon.coupon.categorycoupon.repository.CategoryCouponRepository;
import com.nhnacademy.coupon.entity.CatoryCoupon.CategoryCoupon;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;

import static org.assertj.core.api.Assertions.assertThat;


@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestPropertySource(locations = "classpath:application.yml")
class CategoryCouponRepositoryTest {

    @Autowired
    private CategoryCouponRepository categoryCouponRepository;

    @BeforeEach
    void setUp(){ categoryCouponRepository.deleteAll(); }

    @Test
    void testSaveCategoryCoupon(){
        CategoryCoupon categoryCoupon = new CategoryCoupon();
        categoryCoupon.setCategoryId(1L);

        CategoryCoupon categoryCouponSaved = categoryCouponRepository.save(categoryCoupon);

        assertThat(categoryCouponSaved).isNotNull();
        assertThat(categoryCouponSaved.getCategoryId()).isPositive();
        assertThat(categoryCouponSaved.getCategoryId()).isEqualTo(1L);
    }

    @Test
    void testFindById() {
        // given
        CategoryCoupon categoryCoupon = new CategoryCoupon();
        categoryCoupon.setCategoryId(1L);
        CategoryCoupon savedCategoryCoupon = categoryCouponRepository.save(categoryCoupon);

        // when
        CategoryCoupon findCategoryCoupon = categoryCouponRepository.findById(savedCategoryCoupon.getId()).orElse(null);

        // then
        assertThat(findCategoryCoupon).isNotNull();
        assertThat(findCategoryCoupon.getId()).isEqualTo(savedCategoryCoupon.getId());
        assertThat(findCategoryCoupon.getCategoryId()).isEqualTo(1L);
    }

    @Test
    void testDeleteBookCoupon() {
        // given
        CategoryCoupon categoryCoupon = new CategoryCoupon();
        categoryCoupon.setCategoryId(1L);
        CategoryCoupon savedCategoryCoupon = categoryCouponRepository.save(categoryCoupon);

        // when
        categoryCouponRepository.deleteById(savedCategoryCoupon.getId());
        CategoryCoupon foundCategoryCoupon = categoryCouponRepository.findById(savedCategoryCoupon.getId()).orElse(null);

        // then
        assertThat(foundCategoryCoupon).isNull();
    }

    @Test
    void testUpdateBookCoupon() {
        // given
        CategoryCoupon categoryCoupon = new CategoryCoupon();
        categoryCoupon.setCategoryId(1L);
        CategoryCoupon savedCategoryCoupon = categoryCouponRepository.save(categoryCoupon);

        // when
        savedCategoryCoupon.setCategoryId(2L);
        CategoryCoupon updatedCategoryCoupon = categoryCouponRepository.save(categoryCoupon);

        // then
        assertThat(updatedCategoryCoupon).isNotNull();
        assertThat(updatedCategoryCoupon.getId()).isEqualTo(savedCategoryCoupon.getId());
        assertThat(updatedCategoryCoupon.getCategoryId()).isEqualTo(2L);
    }


}
