package com.nhnacademy.coupon.couponpolicy.repository;

import com.nhnacademy.coupon.entity.RatioCoupon.RatioCoupon;
import com.nhnacademy.coupon.entity.couponForm.CouponForm;
import com.nhnacademy.coupon.entity.couponpolicy.CouponPolicy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@DataJpaTest
class CouponPolicyRepositoryTest {

    @Mock
    private CouponPolicyRepository couponPolicyRepository;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSaveCouponPolicy() {
        // given
        CouponPolicy couponPolicy = new CouponPolicy();
        couponPolicy.setMaxPrice(100);
        couponPolicy.setMinPrice(50);

        // Mocking repository behavior
        when(couponPolicyRepository.save(any(CouponPolicy.class))).thenReturn(couponPolicy);

        // when
        CouponPolicy savedCouponPolicy = couponPolicyRepository.save(couponPolicy);

        // then
        assertThat(savedCouponPolicy).isNotNull();
        assertThat(savedCouponPolicy.getMaxPrice()).isEqualTo(100);
        assertThat(savedCouponPolicy.getMinPrice()).isEqualTo(50);
    }

    @Test
    void testFindById() {
        // given
        CouponPolicy couponPolicy = new CouponPolicy(1L,200,100,null);


        // Mocking repository behavior
        when(couponPolicyRepository.findById(1L)).thenReturn(Optional.of(couponPolicy));

        // when
        Optional<CouponPolicy> foundCouponPolicyOptional = couponPolicyRepository.findById(1L);

        // then
        assertThat(foundCouponPolicyOptional).isPresent();
        CouponPolicy foundCouponPolicy = foundCouponPolicyOptional.get();
        assertThat(foundCouponPolicy.getId()).isEqualTo(1L);
        assertThat(foundCouponPolicy.getMaxPrice()).isEqualTo(200);
        assertThat(foundCouponPolicy.getMinPrice()).isEqualTo(100);
    }

    @Test
    void testDeleteCouponPolicy() {
        // given
        CouponPolicy couponPolicy = new CouponPolicy(1L,200,100,null);


        // Mocking repository behavior
        doNothing().when(couponPolicyRepository).deleteById(1L);
        when(couponPolicyRepository.findById(1L)).thenReturn(Optional.empty());

        // when
        couponPolicyRepository.deleteById(1L);
        // then
        Optional<CouponPolicy> deletedCouponPolicyOptional = couponPolicyRepository.findById(1L);
        assertThat(deletedCouponPolicyOptional).isEmpty();
    }

    @Test
    void testUpdateCouponPolicy() {
        // given
        CouponPolicy couponPolicy = new CouponPolicy();
        couponPolicy.setMaxPrice(10000);

        when(couponPolicyRepository.save(couponPolicy)).thenReturn(couponPolicy);

        CouponPolicy savedCouponPolicy = couponPolicyRepository.save(couponPolicy);

        // when
        savedCouponPolicy.setMaxPrice(20000);
        CouponPolicy updateCouponPolicy = couponPolicyRepository.save(savedCouponPolicy);

        // then
        assertThat(updateCouponPolicy).isNotNull();
        assertThat(updateCouponPolicy.getId()).isEqualTo(savedCouponPolicy.getId());
    }
}
