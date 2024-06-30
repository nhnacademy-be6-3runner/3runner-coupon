package com.nhnacademy.coupon.categorycoupon.repository;


import com.nhnacademy.coupon.coupon.categorycoupon.repository.CategoryCouponRepository;
import com.nhnacademy.coupon.entity.categorycoupon.CategoryCoupon;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestPropertySource(locations = "classpath:application.yml")
class CategoryCouponRepositoryTest {

    @Mock
    private CategoryCouponRepository categoryCouponRepository;

    @BeforeEach
    void setUp(){ categoryCouponRepository.deleteAll(); }



    @Test
    void testFindById() {
        // given
        CategoryCoupon categoryCoupon = new CategoryCoupon();


		Mockito.when(categoryCouponRepository.save(Mockito.any(CategoryCoupon.class))).thenReturn(categoryCoupon);

		Mockito.when(categoryCouponRepository.findById(categoryCoupon.getId())).thenReturn(Optional.of(categoryCoupon));

		CategoryCoupon findCategoryCoupon  = categoryCouponRepository.findById(categoryCoupon.getId()).orElse(null);

		// then
        assertThat(findCategoryCoupon).isNotNull();
        assertThat(findCategoryCoupon.getId()).isEqualTo(categoryCoupon.getId());
        assertThat(findCategoryCoupon.getCategoryId()).isZero();
    }




}
