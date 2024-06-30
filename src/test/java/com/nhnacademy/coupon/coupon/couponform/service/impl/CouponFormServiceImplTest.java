package com.nhnacademy.coupon.coupon.couponform.service.impl;

import com.nhnacademy.coupon.coupon.couponform.exception.CouponFormNotExistException;
import com.nhnacademy.coupon.coupon.couponform.repository.CouponFormRepository;
import com.nhnacademy.coupon.entity.couponform.CouponForm;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CouponFormServiceImplTest {

    @Mock
    private CouponFormRepository couponFormRepository;

    @Mock
    private RabbitTemplate rabbitTemplate;

    @InjectMocks
    private CouponFormServiceImpl couponFormService;

    @Test
    void testCreate() {
        ZonedDateTime startDate = ZonedDateTime.now();
        ZonedDateTime endDate = ZonedDateTime.now().plusDays(1);
        String name = "Test Coupon";
        UUID code = UUID.randomUUID();
        Integer maxPrice = 1000;
        Integer minPrice = 100;

        CouponForm couponForm = new CouponForm(startDate, endDate, name, code, maxPrice, minPrice);
        couponForm.setId(1L);

        when(couponFormRepository.save(any(CouponForm.class))).thenReturn(couponForm);

        Long createdId = couponFormService.create(startDate, endDate, name, code, maxPrice, minPrice);

        verify(couponFormRepository, times(1)).save(any(CouponForm.class));
    }

    @Test
    void testRead() {
        Long couponFormId = 1L;
        CouponForm couponForm = new CouponForm();
        couponForm.setId(couponFormId);

        when(couponFormRepository.findById(couponFormId)).thenReturn(Optional.of(couponForm));

        CouponForm foundCouponForm = couponFormService.read(couponFormId);

        assertNotNull(foundCouponForm);
        assertEquals(couponFormId, foundCouponForm.getId());
        verify(couponFormRepository, times(1)).findById(couponFormId);
    }

    @Test
    void testRead_NotFound() {
        Long couponFormId = 1L;

        when(couponFormRepository.findById(couponFormId)).thenReturn(Optional.empty());

        assertThrows(CouponFormNotExistException.class, () -> couponFormService.read(couponFormId));
        verify(couponFormRepository, times(1)).findById(couponFormId);
    }

    @Test
    void testSendNoticeCouponsExpiringToday() {
        List<CouponForm> couponsExpiringToday = Arrays.asList(
                new CouponForm(ZonedDateTime.now().minusDays(1), ZonedDateTime.now(), "Test Coupon 1", UUID.randomUUID(), 1000, 100),
                new CouponForm(ZonedDateTime.now().minusDays(1), ZonedDateTime.now(), "Test Coupon 2", UUID.randomUUID(), 2000, 200)
        );

        when(couponFormRepository.findCouponsExpiringToday()).thenReturn(couponsExpiringToday);

        couponFormService.sendNoticeCouponsExpiringToday();

        verify(rabbitTemplate, times(1)).convertAndSend("3RUNNER-COUPON-EXPIRED-TODAY", couponsExpiringToday);
    }

    @Test
    void testSendNoticeCouponsExpiringThreeDaysLater() {
        List<CouponForm> couponsExpiringThreeDaysLater = Arrays.asList(
                new CouponForm(ZonedDateTime.now().minusDays(1), ZonedDateTime.now().plusDays(3), "Test Coupon 1", UUID.randomUUID(), 1000, 100),
                new CouponForm(ZonedDateTime.now().minusDays(1), ZonedDateTime.now().plusDays(3), "Test Coupon 2", UUID.randomUUID(), 2000, 200)
        );

        when(couponFormRepository.findCouponsExpiringThreeDaysLater()).thenReturn(couponsExpiringThreeDaysLater);

        couponFormService.sendNoticeCouponsExpiringThreeDaysLater();

        verify(rabbitTemplate, times(1)).convertAndSend("3RUNNER-COUPON-EXPIRED-IN-THREE-DAY", couponsExpiringThreeDaysLater);
    }
}