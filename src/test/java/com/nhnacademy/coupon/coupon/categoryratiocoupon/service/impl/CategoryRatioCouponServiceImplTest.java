package com.nhnacademy.coupon.coupon.categoryratiocoupon.service.impl;

import com.nhnacademy.coupon.coupon.categorycoupon.service.CategoryCouponService;
import com.nhnacademy.coupon.coupon.categoryfixedcoupon.dto.response.ApiCategoryFixedCouponResponse;
import com.nhnacademy.coupon.coupon.categoryfixedcoupon.dto.response.ReadCategoryFixedCouponResponse;
import com.nhnacademy.coupon.coupon.categoryfixedcoupon.exception.CategoryFixedCouponNotExistException;
import com.nhnacademy.coupon.coupon.categoryfixedcoupon.repository.CategoryFixedCouponCustomRepository;
import com.nhnacademy.coupon.coupon.categoryfixedcoupon.repository.CategoryFixedCouponRepository;
import com.nhnacademy.coupon.coupon.categoryratiocoupon.dto.request.ApiCategoryRatioCouponRequest;
import com.nhnacademy.coupon.coupon.categoryratiocoupon.dto.response.ApiCategoryRatioCouponResponse;
import com.nhnacademy.coupon.coupon.categoryratiocoupon.dto.response.ReadCategoryRatioCouponResponse;
import com.nhnacademy.coupon.coupon.categoryratiocoupon.exception.CategoryRatioCouponNotExistException;
import com.nhnacademy.coupon.coupon.categoryratiocoupon.repository.CategoryRatioCouponCustomRepository;
import com.nhnacademy.coupon.coupon.categoryratiocoupon.repository.CategoryRatioCouponRepository;
import com.nhnacademy.coupon.coupon.couponform.exception.CouponFormNotExistException;
import com.nhnacademy.coupon.coupon.couponform.service.CouponFormService;
import com.nhnacademy.coupon.entity.categorycoupon.CategoryCoupon;
import com.nhnacademy.coupon.entity.categoryratiocoupon.CategoryRatioCoupon;
import com.nhnacademy.coupon.entity.couponform.CouponForm;
import com.querydsl.core.Tuple;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.ZonedDateTime;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

class CategoryRatioCouponServiceImplTest {

    @Mock
    private CategoryRatioCouponCustomRepository categoryRatioCouponCustomRepository;

    @Mock
    private CategoryRatioCouponRepository categoryRatioCouponRepository;

    @Mock
    private CategoryCouponService categoryCouponService;

    @Mock
    private CouponFormService couponFormService;

    @InjectMocks
    private CategoryRatioCouponServiceImpl categoryRatioCouponService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreate() {
        ApiCategoryRatioCouponRequest request = new ApiCategoryRatioCouponRequest(
                1L,
                ZonedDateTime.now(),
                ZonedDateTime.now(),
                ZonedDateTime.now().plusDays(1),
                "Test Coupon",
                UUID.randomUUID(),
                100,
                1L,
                2,
                200
        );

        when(couponFormService.create(any(), any(), any(), any(), anyInt(), anyInt())).thenReturn(1L);
        when(couponFormService.read(anyLong())).thenReturn(mock(CouponForm.class));
        when(categoryCouponService.create(any(), anyLong())).thenReturn(1L);
        when(categoryCouponService.read(anyLong())).thenReturn(mock(CategoryCoupon.class));

        Long categoryRatioCouponId = 1L;
        CategoryRatioCoupon categoryRatioCoupon = new CategoryRatioCoupon(mock(CategoryCoupon.class), request.rate(), request.max());
        categoryRatioCoupon.setId(categoryRatioCouponId);

        when(categoryRatioCouponRepository.save(any(CategoryRatioCoupon.class))).thenReturn(categoryRatioCoupon);

        Long createdId = categoryRatioCouponService.create(request);

        verify(couponFormService, times(1)).create(any(), any(), any(), any(), anyInt(), anyInt());
        verify(categoryCouponService, times(1)).create(any(), anyLong());
        verify(categoryRatioCouponRepository, times(1)).save(any(CategoryRatioCoupon.class));
    }

    @Test
    void testRead_NotFound() {
        Long categoryRatioCouponId = 1L;

        when(categoryRatioCouponRepository.findById(categoryRatioCouponId)).thenReturn(Optional.empty());

        assertThrows(CategoryRatioCouponNotExistException.class, () -> categoryRatioCouponService.read(categoryRatioCouponId));
        verify(categoryRatioCouponRepository, times(1)).findById(categoryRatioCouponId);
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
        when(tuple.get(9, Double.class)).thenReturn(0.2);
        when(tuple.get(10, Long.class)).thenReturn(200L);
        when(tuple.get(11, Integer.class)).thenReturn(1000);
        when(tuple.get(12, Integer.class)).thenReturn(100);

        when(categoryRatioCouponCustomRepository.findCategoryRatioCoupon(couponFormId)).thenReturn(Optional.of(tuple));

        ApiCategoryRatioCouponResponse response = categoryRatioCouponService.readAllData(couponFormId);

        assertNotNull(response);
        assertEquals(1L, response.categoryRatioCouponId());
        assertEquals(2L, response.categoryCouponId());
        assertEquals(3L, response.categoryId());
        assertEquals(4L, response.couponFormId());
        assertNotNull(response.startDate());
        assertNotNull(response.endDate());
        assertNotNull(response.createdAt());
        assertEquals("Test Coupon", response.name());
        assertNotNull(response.code());
        assertEquals(0.2, response.rate());
        assertEquals(200L, response.max());
        assertEquals(1000, response.maxPrice());
        assertEquals(100, response.minPrice());
        verify(categoryRatioCouponCustomRepository, times(1)).findCategoryRatioCoupon(couponFormId);
    }

    @Test
    void testReadAllData_NotFound() {
        Long couponFormId = 1L;

        when(categoryRatioCouponCustomRepository.findCategoryRatioCoupon(couponFormId)).thenReturn(Optional.empty());

        assertThrows(CouponFormNotExistException.class, () -> categoryRatioCouponService.readAllData(couponFormId));
        verify(categoryRatioCouponCustomRepository, times(1)).findCategoryRatioCoupon(couponFormId);
    }
}