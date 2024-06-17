package com.nhnacademy.coupon.fixedcoupon.repository;


import com.nhnacademy.coupon.coupon.fixedcoupon.repository.FixedCouponRepository;
import com.nhnacademy.coupon.entity.FixedCoupon.FixedCoupon;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestPropertySource(locations = "classpath:application.yml")
class FixedCouponRepositoryTest {
    @Mock
    private FixedCouponRepository fixedCouponRepository;

    @BeforeEach
    void setUp(){ MockitoAnnotations.openMocks(this);}

    @Test
    void testSaveFixedCoupon(){
        FixedCoupon fixedCoupon = new FixedCoupon();
        fixedCoupon.setPrice(1000);

        when(fixedCouponRepository.save(fixedCoupon)).thenReturn(fixedCoupon);

        FixedCoupon savedFixedCoupon = fixedCouponRepository.save(fixedCoupon);

        assertThat(savedFixedCoupon).isNotNull();
        assertThat(savedFixedCoupon.getPrice()).isEqualTo(1000);

    }

    @Test
    void testFindByFixedCouponId(){
        FixedCoupon fixedCoupon = new FixedCoupon(0L,1000,null);

        when(fixedCouponRepository.findById(0L)).thenReturn(Optional.of(fixedCoupon));


        FixedCoupon foundFixedCoupon = fixedCouponRepository.findById(0L).orElse(null);


        assertThat(foundFixedCoupon).isNotNull();
        assertThat(foundFixedCoupon.getId()).isEqualTo(fixedCoupon.getId());
        assertThat(foundFixedCoupon.getPrice()).isEqualTo(fixedCoupon.getPrice());

    }

    @Test
    void testDeleteFixedCoupon(){
        FixedCoupon fixedCoupon = new FixedCoupon(0L,1000,null);

        doNothing().when(fixedCouponRepository).deleteById(0L);
        when(fixedCouponRepository.findById(0L)).thenReturn(Optional.empty());

        fixedCouponRepository.deleteById(0L);
        FixedCoupon deletedFixedCoupon = fixedCouponRepository.findById(0L).orElse(null);

        assertThat(deletedFixedCoupon).isNull();

    }

    @Test
    void testUpdateFixedCoupon(){
        FixedCoupon fixedCoupon = new FixedCoupon(0L,1000,null);
        when(fixedCouponRepository.save(fixedCoupon)).thenReturn(fixedCoupon);

        fixedCoupon.setPrice(500);
        FixedCoupon updatedFixedCoupon = fixedCouponRepository.save(fixedCoupon);

        assertThat(updatedFixedCoupon).isNotNull();
        assertThat(updatedFixedCoupon.getPrice()).isEqualTo(500);
        assertThat(updatedFixedCoupon.getId()).isEqualTo(fixedCoupon.getId());
    }


}
