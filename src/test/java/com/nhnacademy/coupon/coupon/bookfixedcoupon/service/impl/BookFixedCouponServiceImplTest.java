package com.nhnacademy.coupon.coupon.bookfixedcoupon.service.impl;

import com.nhnacademy.coupon.coupon.bookcoupon.service.BookCouponService;
import com.nhnacademy.coupon.coupon.bookfixedcoupon.dto.request.ApiBookFixedCouponRequest;
import com.nhnacademy.coupon.coupon.bookfixedcoupon.dto.response.ApiBookFixedCouponResponse;
import com.nhnacademy.coupon.coupon.bookfixedcoupon.dto.response.ReadBookFixedCouponResponse;
import com.nhnacademy.coupon.coupon.bookfixedcoupon.exception.BookFixedCouponNotExistException;
import com.nhnacademy.coupon.coupon.bookfixedcoupon.repository.BookFixedCouponCustomRepository;
import com.nhnacademy.coupon.coupon.bookfixedcoupon.repository.BookFixedCouponRepository;
import com.nhnacademy.coupon.coupon.couponform.exception.CouponFormNotExistException;
import com.nhnacademy.coupon.coupon.couponform.service.CouponFormService;
import com.nhnacademy.coupon.entity.bookfixedcoupon.BookFixedCoupon;
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
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

class BookFixedCouponServiceImplTest {

    @Mock
    private BookCouponService bookCouponService;

    @Mock
    private CouponFormService couponFormService;

    @Mock
    private BookFixedCouponRepository bookFixedCouponRepository;

    @Mock
    private BookFixedCouponCustomRepository bookFixedCouponCustomRepository;

    @InjectMocks
    private BookFixedCouponServiceImpl bookFixedCouponService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreate() {
        ApiBookFixedCouponRequest request = new ApiBookFixedCouponRequest(
                1L,
                ZonedDateTime.now(),
                ZonedDateTime.now().plusDays(1),
                ZonedDateTime.now(),
                "Test Coupon",
                UUID.randomUUID(),
                100,
                1,
                500
        );

        when(couponFormService.create(any(), any(), any(), any(), anyInt(), anyInt())).thenReturn(1L);
        when(couponFormService.read(anyLong())).thenReturn(null);
        when(bookCouponService.create(any(), anyLong())).thenReturn(1L);
        when(bookCouponService.read(anyLong())).thenReturn(null);

        Long bookFixedCouponId = 1L;
        BookFixedCoupon bookFixedCoupon = new BookFixedCoupon(null, request.price());
        bookFixedCoupon.setId(bookFixedCouponId);

        when(bookFixedCouponRepository.save(any(BookFixedCoupon.class))).thenReturn(bookFixedCoupon);

        Long createdId = bookFixedCouponService.create(request);

        verify(couponFormService, times(1)).create(any(), any(), any(), any(), anyInt(), anyInt());
        verify(bookCouponService, times(1)).create(any(), anyLong());
        verify(bookFixedCouponRepository, times(1)).save(any(BookFixedCoupon.class));
    }

    @Test
    void testRead_NotFound() {
        Long bookFixedCouponId = 1L;

        when(bookFixedCouponRepository.findById(bookFixedCouponId)).thenReturn(Optional.empty());

        assertThrows(BookFixedCouponNotExistException.class, () -> bookFixedCouponService.read(bookFixedCouponId));
        verify(bookFixedCouponRepository, times(1)).findById(bookFixedCouponId);
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

        when(bookFixedCouponCustomRepository.findBookFixedCoupon(couponFormId)).thenReturn(Optional.of(tuple));

        ApiBookFixedCouponResponse response = bookFixedCouponService.readAllData(couponFormId);

        assertNotNull(response);
        assertEquals(1L, response.bookFixedCouponId());
        assertEquals(2L, response.bookCouponId());
        assertEquals(3L, response.bookId());
        assertEquals(4L, response.couponFormId());
        verify(bookFixedCouponCustomRepository, times(1)).findBookFixedCoupon(couponFormId);
    }

    @Test
    void testReadAllData_NotFound() {
        Long couponFormId = 1L;

        when(bookFixedCouponCustomRepository.findBookFixedCoupon(couponFormId)).thenReturn(Optional.empty());

        assertThrows(CouponFormNotExistException.class, () -> bookFixedCouponService.readAllData(couponFormId));
        verify(bookFixedCouponCustomRepository, times(1)).findBookFixedCoupon(couponFormId);
    }
}