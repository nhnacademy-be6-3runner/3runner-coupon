package com.nhnacademy.coupon.categoryfixedcoupon.repository;


import com.nhnacademy.coupon.coupon.categoryfixedcoupon.repository.CategoryFixedCouponRepository;
import com.nhnacademy.coupon.entity.categoryfixedcoupon.CategoryFixedCoupon;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestPropertySource(locations = "classpath:application.yml")
class CategoryFixedCouponRepositoryTest {
    @Mock
    private CategoryFixedCouponRepository fixedCouponRepository;

    @BeforeEach
    void setUp(){ MockitoAnnotations.openMocks(this);}

    @Test
    void testSaveFixedCoupon(){
        CategoryFixedCoupon fixedCoupon = new CategoryFixedCoupon();
        fixedCoupon.setPrice(1000);

        when(fixedCouponRepository.save(fixedCoupon)).thenReturn(fixedCoupon);

        CategoryFixedCoupon savedFixedCoupon = fixedCouponRepository.save(fixedCoupon);

        assertThat(savedFixedCoupon).isNotNull();
        assertThat(savedFixedCoupon.getPrice()).isEqualTo(1000);

    }

    @Test
    void testFindByFixedCouponId(){
        CategoryFixedCoupon fixedCoupon = new CategoryFixedCoupon();

        when(fixedCouponRepository.findById(0L)).thenReturn(Optional.of(fixedCoupon));


        CategoryFixedCoupon foundFixedCoupon = fixedCouponRepository.findById(0L).orElse(null);


        assertThat(foundFixedCoupon).isNotNull();
        assertThat(foundFixedCoupon.getId()).isEqualTo(fixedCoupon.getId());
        assertThat(foundFixedCoupon.getPrice()).isEqualTo(fixedCoupon.getPrice());

    }


}
