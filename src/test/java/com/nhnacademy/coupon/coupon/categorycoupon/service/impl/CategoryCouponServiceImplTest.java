package com.nhnacademy.coupon.coupon.categorycoupon.service.impl;

import com.nhnacademy.coupon.coupon.categorycoupon.dto.ReadCategoryCouponResponse;
import com.nhnacademy.coupon.coupon.categorycoupon.exception.CategoryCouponNotExistException;
import com.nhnacademy.coupon.coupon.categorycoupon.repository.CategoryCouponRepository;
import com.nhnacademy.coupon.entity.categorycoupon.CategoryCoupon;
import com.nhnacademy.coupon.entity.couponform.CouponForm;
import jakarta.persistence.Entity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CategoryCouponServiceImplTest {

    @Mock
    private CategoryCouponRepository categoryCouponRepository;

    @InjectMocks
    private CategoryCouponServiceImpl categoryCouponService;


    @Test
    void testCreate() {
        CouponForm couponForm = new CouponForm();
        Long categoryId = 1L;
        CategoryCoupon categoryCoupon = new CategoryCoupon(couponForm, categoryId);
        categoryCoupon.setId(1L);

        when(categoryCouponRepository.save(any(CategoryCoupon.class))).thenReturn(categoryCoupon);

        Long createdId = categoryCouponService.create(couponForm, categoryId);

        verify(categoryCouponRepository, times(1)).save(any(CategoryCoupon.class));
    }

    @Test
    void testRead() {
        Long categoryCouponId = 1L;
        CategoryCoupon categoryCoupon = new CategoryCoupon();
        categoryCoupon.setId(categoryCouponId);

        when(categoryCouponRepository.findById(categoryCouponId)).thenReturn(Optional.of(categoryCoupon));

        CategoryCoupon foundCategoryCoupon = categoryCouponService.read(categoryCouponId);

        assertNotNull(foundCategoryCoupon);
        assertEquals(categoryCouponId, foundCategoryCoupon.getId());
        verify(categoryCouponRepository, times(1)).findById(categoryCouponId);
    }

    @Test
    void testRead_NotFound() {
        Long categoryCouponId = 1L;

        when(categoryCouponRepository.findById(categoryCouponId)).thenReturn(Optional.empty());

        assertThrows(CategoryCouponNotExistException.class, () -> categoryCouponService.read(categoryCouponId));
        verify(categoryCouponRepository, times(1)).findById(categoryCouponId);
    }
}