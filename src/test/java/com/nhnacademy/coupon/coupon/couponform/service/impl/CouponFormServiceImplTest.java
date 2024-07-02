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

}