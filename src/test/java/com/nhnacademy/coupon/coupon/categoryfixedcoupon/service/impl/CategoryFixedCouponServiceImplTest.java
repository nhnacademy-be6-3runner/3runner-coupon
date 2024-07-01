package com.nhnacademy.coupon.coupon.categoryfixedcoupon.service.impl;

import com.nhnacademy.coupon.coupon.categorycoupon.service.CategoryCouponService;
import com.nhnacademy.coupon.coupon.categoryfixedcoupon.dto.request.ApiCategoryFixedCouponRequest;
import com.nhnacademy.coupon.coupon.categoryfixedcoupon.dto.response.ApiCategoryFixedCouponResponse;
import com.nhnacademy.coupon.coupon.categoryfixedcoupon.dto.response.ReadCategoryFixedCouponResponse;
import com.nhnacademy.coupon.coupon.categoryfixedcoupon.exception.CategoryFixedCouponNotExistException;
import com.nhnacademy.coupon.coupon.categoryfixedcoupon.repository.CategoryFixedCouponCustomRepository;
import com.nhnacademy.coupon.coupon.categoryfixedcoupon.repository.CategoryFixedCouponRepository;
import com.nhnacademy.coupon.coupon.couponform.exception.CouponFormNotExistException;
import com.nhnacademy.coupon.coupon.couponform.service.CouponFormService;
import com.nhnacademy.coupon.entity.categorycoupon.CategoryCoupon;
import com.nhnacademy.coupon.entity.categoryfixedcoupon.CategoryFixedCoupon;
import com.nhnacademy.coupon.entity.couponform.CouponForm;
import com.querydsl.core.Tuple;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.ZonedDateTime;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CategoryFixedCouponServiceImplTest {

    @Mock
    private CategoryFixedCouponCustomRepository categoryFixedCouponCustomRepository;

    @Mock
    private CategoryFixedCouponRepository categoryFixedCouponRepository;

    @Mock
    private CategoryCouponService categoryCouponService;

    @Mock
    private CouponFormService couponFormService;

    @InjectMocks
    private CategoryFixedCouponServiceImpl categoryFixedCouponService;


    @Test
    void testCreate() {
        ApiCategoryFixedCouponRequest request = new ApiCategoryFixedCouponRequest(
                1L,
                ZonedDateTime.now(),
                ZonedDateTime.now(),
                ZonedDateTime.now().plusDays(1),
                "Test Coupon",
                UUID.randomUUID(),
                100,
                1,
                500
        );

        when(couponFormService.create(any(), any(), any(), any(), anyInt(), anyInt())).thenReturn(1L);
        when(couponFormService.read(anyLong())).thenReturn(mock(CouponForm.class));
        when(categoryCouponService.create(any(), anyLong())).thenReturn(1L);
        when(categoryCouponService.read(anyLong())).thenReturn(mock(CategoryCoupon.class));

        Long categoryFixedCouponId = 1L;
        CategoryFixedCoupon categoryFixedCoupon = new CategoryFixedCoupon(mock(CategoryCoupon.class), request.price());
        categoryFixedCoupon.setId(categoryFixedCouponId);

        when(categoryFixedCouponRepository.save(any(CategoryFixedCoupon.class))).thenReturn(categoryFixedCoupon);

        Long createdId = categoryFixedCouponService.create(request);

        verify(couponFormService, times(1)).create(any(), any(), any(), any(), anyInt(), anyInt());
        verify(categoryCouponService, times(1)).create(any(), anyLong());
        verify(categoryFixedCouponRepository, times(1)).save(any(CategoryFixedCoupon.class));
    }
    @Test
    void testRead_NotFound() {
        Long categoryFixedCouponId = 1L;

        when(categoryFixedCouponRepository.findById(categoryFixedCouponId)).thenReturn(Optional.empty());

        assertThrows(CategoryFixedCouponNotExistException.class, () -> categoryFixedCouponService.read(categoryFixedCouponId));
        verify(categoryFixedCouponRepository, times(1)).findById(categoryFixedCouponId);
    }

    @Test
    void testReadAllData() {
        Long couponFormId = 1L;
        Tuple tuple = mock(Tuple.class);

        when(tuple.get(0, Long.class)).thenReturn(1L);
        when(tuple.get(1, Long.class)).thenReturn(2L);
        when(tuple.get(2, Long.class)).thenReturn(3L);
        when(tuple.get(3, Long.class)).thenReturn(4L);
        when(tuple.get(4, ZonedDateTime.class)).thenReturn(ZonedDateTime.now());
        when(tuple.get(5, ZonedDateTime.class)).thenReturn(ZonedDateTime.now().plusDays(1));
        when(tuple.get(6, ZonedDateTime.class)).thenReturn(ZonedDateTime.now().minusDays(1));
        when(tuple.get(7, String.class)).thenReturn("Test Coupon");
        when(tuple.get(8, UUID.class)).thenReturn(UUID.randomUUID());
        when(tuple.get(9, Integer.class)).thenReturn(500);
        when(tuple.get(10, Integer.class)).thenReturn(1000);
        when(tuple.get(11, Integer.class)).thenReturn(100);

        when(categoryFixedCouponCustomRepository.findCategoryFixedCoupon(couponFormId)).thenReturn(Optional.of(tuple));

        ApiCategoryFixedCouponResponse response = categoryFixedCouponService.readAllData(couponFormId);

        assertNotNull(response);
        assertEquals(1L, response.categoryFixedCouponId());
        assertEquals(2L, response.categoryCouponId());
        assertEquals(3L, response.categoryId());
        assertEquals(4L, response.couponFormId());
        assertNotNull(response.startDate());
        assertNotNull(response.endDate());
        assertNotNull(response.createdAt());
        assertEquals("Test Coupon", response.name());
        assertNotNull(response.code());
        assertEquals(500, response.price());
        assertEquals(1000, response.maxPrice());
        assertEquals(100, response.minPrice());
        verify(categoryFixedCouponCustomRepository, times(1)).findCategoryFixedCoupon(couponFormId);
    }

    @Test
    void testReadAllData_NotFound() {
        Long couponFormId = 1L;

        when(categoryFixedCouponCustomRepository.findCategoryFixedCoupon(couponFormId)).thenReturn(Optional.empty());

        assertThrows(CouponFormNotExistException.class, () -> categoryFixedCouponService.readAllData(couponFormId));
        verify(categoryFixedCouponCustomRepository, times(1)).findCategoryFixedCoupon(couponFormId);
    }
}