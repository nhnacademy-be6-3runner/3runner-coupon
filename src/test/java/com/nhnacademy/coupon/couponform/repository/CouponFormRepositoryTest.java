package com.nhnacademy.coupon.couponform.repository;

import com.nhnacademy.coupon.coupon.bookcoupon.repository.BookCouponRepository;
import com.nhnacademy.coupon.coupon.categorycoupon.repository.CategoryCouponRepository;
import com.nhnacademy.coupon.coupon.couponform.repository.CouponFormRepository;
import com.nhnacademy.coupon.entity.bookcoupon.BookCoupon;
import com.nhnacademy.coupon.entity.categorycoupon.CategoryCoupon;
import com.nhnacademy.coupon.entity.categoryfixedcoupon.CategoryFixedCoupon;
import com.nhnacademy.coupon.entity.categoryratiocoupon.CategoryRatioCoupon;
import com.nhnacademy.coupon.entity.couponform.CouponForm;
import com.nhnacademy.coupon.coupon.categoryfixedcoupon.repository.CategoryFixedCouponRepository;
import com.nhnacademy.coupon.coupon.categoryratiocoupon.repository.CategoryRatioCouponRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.test.context.TestPropertySource;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestPropertySource(locations = "classpath:application.yml")
class CouponFormRepositoryTest {

    @Mock
    private CouponFormRepository couponFormRepository;
    @Mock
    private CategoryRatioCouponRepository ratioCouponRepository;
    @Mock
    private CategoryCouponRepository categoryCouponRepository;
    @Mock
    private CategoryFixedCouponRepository fixedCouponRepository;
    @Mock
    private BookCouponRepository bookCouponRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSaveCouponForm() {
        // given
        CouponForm couponForm = new CouponForm(1L,ZonedDateTime.now(),ZonedDateTime.now()
            ,ZonedDateTime.now().plusDays(10),
            "Test Coupon",10,100 );

        when(couponFormRepository.save(couponForm)).thenReturn(couponForm);

        // when
        CouponForm savedCouponForm = couponFormRepository.save(couponForm);

        // then
        assertThat(savedCouponForm).isNotNull();
        assertThat(savedCouponForm.getName()).isEqualTo("Test Coupon");
    }

    @Test
    void testFindById() {
        // given
        CouponForm couponForm = new CouponForm(1L,ZonedDateTime.now(),ZonedDateTime.now()
            ,ZonedDateTime.now().plusDays(10),
            "Test Coupon",10,100 );




        when(couponFormRepository.findById(1L)).thenReturn(Optional.of(couponForm));

        // when
        CouponForm foundCouponForm = couponFormRepository.findById(1L).orElse(null);

        // then
        assertThat(foundCouponForm).isNotNull();
        assertThat(foundCouponForm.getId()).isEqualTo(1L);
        assertThat(foundCouponForm.getName()).isEqualTo("Test Coupon");
    }



    @Test
    void testDeleteCouponForm() {
        // given
        CouponForm couponForm = new CouponForm(ZonedDateTime.now()
                ,ZonedDateTime.now().plusDays(10),
                "Test Coupon",10,100 );
        when(couponFormRepository.save(couponForm)).thenReturn(couponForm);


        CouponForm savedCouponForm = couponFormRepository.save(couponForm);

        // when
        couponFormRepository.deleteById(savedCouponForm.getId());
        CouponForm foundCouponForm = couponFormRepository.findById(savedCouponForm.getId()).orElse(null);

        // then
        assertThat(foundCouponForm).isNull();
    }

    @Test
    void testUpdateCouponForm() {
        // given
        CouponForm couponForm = new CouponForm(ZonedDateTime.now()
            ,ZonedDateTime.now().plusDays(10),
            "Test Coupon",10,100 );
        when(couponFormRepository.save(couponForm)).thenReturn(couponForm);


        CouponForm savedCouponForm = couponFormRepository.save(couponForm);

        // when
        savedCouponForm.setName("Updated Coupon");
        CouponForm updatedCouponForm = couponFormRepository.save(savedCouponForm);

        // then
        assertThat(updatedCouponForm).isNotNull();
        assertThat(updatedCouponForm.getId()).isEqualTo(savedCouponForm.getId());
        assertThat(updatedCouponForm.getName()).isEqualTo("Updated Coupon");
    }



}
