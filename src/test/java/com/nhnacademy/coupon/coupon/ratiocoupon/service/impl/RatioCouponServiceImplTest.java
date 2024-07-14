package com.nhnacademy.coupon.coupon.ratiocoupon.service.impl;

import com.nhnacademy.coupon.coupon.coupontype.exception.CouponTypeDoesNotExistException;
import com.nhnacademy.coupon.coupon.coupontype.repository.CouponTypeRepository;
import com.nhnacademy.coupon.coupon.ratiocoupon.dto.response.ReadRatioCouponResponse;
import com.nhnacademy.coupon.coupon.ratiocoupon.repository.RatioCouponRepository;
import com.nhnacademy.coupon.entity.coupontype.CouponType;
import com.nhnacademy.coupon.entity.ratiocoupon.RatioCoupon;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class RatioCouponServiceImplTest {

    @Mock
    private RatioCouponRepository ratioCouponRepository;

    @Mock
    private CouponTypeRepository couponTypeRepository;

    @InjectMocks
    private RatioCouponServiceImpl ratioCouponService;

    @Captor
    private ArgumentCaptor<CouponType> couponTypeCaptor;

    @Captor
    private ArgumentCaptor<RatioCoupon> ratioCouponCaptor;

    private double discountRate;
    private int discountMaxPrice;
    private CouponType couponType;
    private RatioCoupon ratioCoupon;

    @BeforeEach
    void setUp() {
        discountRate = 0.1;
        discountMaxPrice = 1000;
        couponType = new CouponType("할인율 : " + discountRate + ", 최대할인가 : " + discountMaxPrice);
        ratioCoupon = new RatioCoupon(couponType, discountRate, discountMaxPrice);
    }

    @Test
    void create_shouldCreateAndSaveCouponTypeAndRatioCoupon() {
        // Act
        ratioCouponService.create(discountRate, discountMaxPrice);

        // Assert
        verify(couponTypeRepository).save(couponTypeCaptor.capture());
        verify(ratioCouponRepository).save(ratioCouponCaptor.capture());

//        CouponType capturedCouponType = couponTypeCaptor.getValue();
//        RatioCoupon capturedRatioCoupon = ratioCouponCaptor.getValue();
//
//        assertEquals("할인율 : " + discountRate + ", 최대할인가 : " + discountMaxPrice, capturedCouponType.getType());
//        assertEquals(capturedCouponType, capturedRatioCoupon.getCouponType());
//        assertEquals(discountRate, capturedRatioCoupon.getDiscountRate());
//        assertEquals(discountMaxPrice, capturedRatioCoupon.getDiscountMaxPrice());
    }

    @Test
    void read_shouldReturnRatioCouponResponse_whenCouponTypeExists() {
        // Arrange
        Long couponTypeId = 1L;
        when(couponTypeRepository.findById(couponTypeId)).thenReturn(Optional.of(couponType));
        when(ratioCouponRepository.findByCouponType(couponType)).thenReturn(Optional.of(ratioCoupon));

        // Act
        ReadRatioCouponResponse response = ratioCouponService.read(couponTypeId);

        // Assert
        assertEquals(ratioCoupon.getId(), response.ratioCouponId());
        assertEquals(couponTypeId, response.couponTypeId());
        assertEquals(discountRate, response.discountRate());
        assertEquals(discountMaxPrice, response.discountMaxPrice());
    }

    @Test
    void read_shouldThrowException_whenCouponTypeDoesNotExist() {
        // Arrange
        Long couponTypeId = 1L;
        when(couponTypeRepository.findById(couponTypeId)).thenReturn(Optional.empty());

        // Act & Assert
        CouponTypeDoesNotExistException exception = assertThrows(
                CouponTypeDoesNotExistException.class,
                () -> ratioCouponService.read(couponTypeId)
        );

        assertEquals(couponTypeId + "가 없습니다", exception.getMessage());
    }

    @Test
    void read_shouldReturnDefaultRatioCouponResponse_whenRatioCouponDoesNotExist() {
        // Arrange
        Long couponTypeId = 1L;
        when(couponTypeRepository.findById(couponTypeId)).thenReturn(Optional.of(couponType));
        when(ratioCouponRepository.findByCouponType(couponType)).thenReturn(Optional.empty());

        // Act
        ReadRatioCouponResponse response = ratioCouponService.read(couponTypeId);

        // Assert
        assertNull(response.ratioCouponId());
        assertEquals(couponTypeId, response.couponTypeId());
        assertEquals(0, response.discountRate());
        assertEquals(0, response.discountMaxPrice());
    }

}