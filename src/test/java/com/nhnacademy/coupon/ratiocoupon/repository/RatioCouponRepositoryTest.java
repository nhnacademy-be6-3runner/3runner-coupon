package com.nhnacademy.coupon.ratiocoupon.repository;


import com.nhnacademy.coupon.coupon.ratiocoupon.repository.RatioCouponRepository;
import com.nhnacademy.coupon.entity.RatioCoupon.RatioCoupon;
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
public class RatioCouponRepositoryTest {

    @Mock
    private RatioCouponRepository ratioCouponRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSaveRatioCouponRepository(){
        RatioCoupon ratioCoupon = new RatioCoupon();
        ratioCoupon.setDiscountRate(0.5);
        ratioCoupon.setMaxDiscount(1000L);

        when(ratioCouponRepository.save(ratioCoupon)).thenReturn(ratioCoupon);
        RatioCoupon savedRatioCoupon = ratioCouponRepository.save(ratioCoupon);

        assertThat(savedRatioCoupon).isNotNull();
        assertThat(savedRatioCoupon.getDiscountRate()).isEqualTo(0.5);

    }
    @Test
    void testFindByIdRatioCoupon(){
        RatioCoupon ratioCoupon = new RatioCoupon(0L,0.5,null,null);
        when(ratioCouponRepository.save(ratioCoupon)).thenReturn(ratioCoupon);
        RatioCoupon savedRatioCoupon = ratioCouponRepository.save(ratioCoupon);
        when(ratioCouponRepository.findById(0L)).thenReturn(Optional.of(ratioCoupon));

        RatioCoupon foundRatioCoupon = ratioCouponRepository.findById(0L).orElse(null);

        assertThat(foundRatioCoupon).isNotNull();
        assertThat(foundRatioCoupon.getDiscountRate()).isEqualTo(0.5);
        assertThat(foundRatioCoupon.getMaxDiscount()).isNull();



    }
    @Test
    void testDeleteBookCoupon() {
        // given
        RatioCoupon ratioCoupon = new RatioCoupon();
        ratioCoupon.setDiscountRate(1L);

        when(ratioCouponRepository.save(ratioCoupon)).thenReturn(ratioCoupon);

        RatioCoupon saveRatioCoupon = ratioCouponRepository.save(ratioCoupon);
        // when
        ratioCouponRepository.deleteById(saveRatioCoupon.getId());
        RatioCoupon foundRatioCoupon = ratioCouponRepository.findById(saveRatioCoupon.getId()).orElse(null);

        // then
        assertThat(foundRatioCoupon).isNull();
    }

    @Test
    void testUpdateBookCoupon() {
        // given
        RatioCoupon ratioCoupon = new RatioCoupon();
        ratioCoupon.setMaxDiscount(1L);

        when(ratioCouponRepository.save(ratioCoupon)).thenReturn(ratioCoupon);

        RatioCoupon savedRatioCoupon = ratioCouponRepository.save(ratioCoupon);

        // when
        savedRatioCoupon.setMaxDiscount(2L);
        RatioCoupon updateRatioCoupon = ratioCouponRepository.save(savedRatioCoupon);

        // then
        assertThat(updateRatioCoupon).isNotNull();
        assertThat(updateRatioCoupon.getId()).isEqualTo(savedRatioCoupon.getId());
    }
}
