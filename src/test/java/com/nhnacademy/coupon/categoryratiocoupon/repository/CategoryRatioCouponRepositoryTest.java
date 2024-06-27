package com.nhnacademy.coupon.categoryratiocoupon.repository;


import com.nhnacademy.coupon.coupon.categoryratiocoupon.repository.CategoryRatioCouponRepository;
import com.nhnacademy.coupon.entity.categoryratiocoupon.CategoryRatioCoupon;
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
public class CategoryRatioCouponRepositoryTest {

    @Mock
    private CategoryRatioCouponRepository ratioCouponRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSaveRatioCouponRepository(){
        CategoryRatioCoupon ratioCoupon = new CategoryRatioCoupon();
        ratioCoupon.setDiscountRate(0.5);
        ratioCoupon.setMaxDiscount(1000L);

        when(ratioCouponRepository.save(ratioCoupon)).thenReturn(ratioCoupon);
        CategoryRatioCoupon savedRatioCoupon = ratioCouponRepository.save(ratioCoupon);

        assertThat(savedRatioCoupon).isNotNull();
        assertThat(savedRatioCoupon.getDiscountRate()).isEqualTo(0.5);

    }
    @Test
    void testFindByIdRatioCoupon(){
        CategoryRatioCoupon ratioCoupon = new CategoryRatioCoupon();
        when(ratioCouponRepository.save(ratioCoupon)).thenReturn(ratioCoupon);
        when(ratioCouponRepository.findById(0L)).thenReturn(Optional.of(ratioCoupon));

        CategoryRatioCoupon foundRatioCoupon = ratioCouponRepository.findById(0L).orElse(null);

        assertThat(foundRatioCoupon).isNotNull();
        assertThat(foundRatioCoupon.getMaxDiscount()).isNull();



    }
    @Test
    void testDeleteBookCoupon() {
        // given
        CategoryRatioCoupon ratioCoupon = new CategoryRatioCoupon();
        ratioCoupon.setDiscountRate(1L);

        when(ratioCouponRepository.save(ratioCoupon)).thenReturn(ratioCoupon);

        CategoryRatioCoupon saveRatioCoupon = ratioCouponRepository.save(ratioCoupon);
        // when
        ratioCouponRepository.deleteById(saveRatioCoupon.getId());
        CategoryRatioCoupon foundRatioCoupon = ratioCouponRepository.findById(saveRatioCoupon.getId()).orElse(null);

        // then
        assertThat(foundRatioCoupon).isNull();
    }

    @Test
    void testUpdateBookCoupon() {
        // given
        CategoryRatioCoupon ratioCoupon = new CategoryRatioCoupon();
        ratioCoupon.setMaxDiscount(1L);

        when(ratioCouponRepository.save(ratioCoupon)).thenReturn(ratioCoupon);

        CategoryRatioCoupon savedRatioCoupon = ratioCouponRepository.save(ratioCoupon);

        // when
        savedRatioCoupon.setMaxDiscount(2L);
        CategoryRatioCoupon updateRatioCoupon = ratioCouponRepository.save(savedRatioCoupon);

        // then
        assertThat(updateRatioCoupon).isNotNull();
        assertThat(updateRatioCoupon.getId()).isEqualTo(savedRatioCoupon.getId());
    }
}
