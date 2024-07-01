package com.nhnacademy.coupon.coupon.bookratiocoupon.service.impl;

import com.nhnacademy.coupon.coupon.bookcoupon.service.BookCouponService;
import com.nhnacademy.coupon.coupon.bookratiocoupon.dto.request.ApiBookRatioCouponRequest;
import com.nhnacademy.coupon.coupon.bookratiocoupon.dto.response.ApiBookRatioCouponResponse;
import com.nhnacademy.coupon.coupon.bookratiocoupon.dto.response.ReadBookRatioCouponResponse;
import com.nhnacademy.coupon.coupon.bookratiocoupon.exception.BookRatioCouponNotExistException;
import com.nhnacademy.coupon.coupon.bookratiocoupon.repository.BookRatioCouponCustomRepository;
import com.nhnacademy.coupon.coupon.bookratiocoupon.repository.BookRatioCouponRepository;
import com.nhnacademy.coupon.coupon.couponform.exception.CouponFormNotExistException;
import com.nhnacademy.coupon.coupon.couponform.service.CouponFormService;
import com.nhnacademy.coupon.entity.bookcoupon.BookCoupon;
import com.nhnacademy.coupon.entity.bookratiocoupon.BookRatioCoupon;
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
class BookRatioCouponServiceImplTest {

    @Mock
    private BookRatioCouponCustomRepository bookRatioCouponCustomRepository;

    @Mock
    private BookRatioCouponRepository bookRatioCouponRepository;

    @Mock
    private CouponFormService couponFormService;

    @Mock
    private BookCouponService bookCouponService;

    @InjectMocks
    private BookRatioCouponServiceImpl bookRatioCouponService;


    @Test
    void testCreate() {
        ApiBookRatioCouponRequest request = new ApiBookRatioCouponRequest(
                1L,
                ZonedDateTime.now(),
                ZonedDateTime.now(),
                ZonedDateTime.now().plusDays(1),
                "Test Coupon",
                UUID.randomUUID(),
                0.2,
                1L,
                2,
                200
        );

        when(couponFormService.create(any(), any(), any(), any(), anyInt(), anyInt())).thenReturn(1L);
        when(couponFormService.read(anyLong())).thenReturn(mock(CouponForm.class));
        when(bookCouponService.create(any(), anyLong())).thenReturn(1L);
        when(bookCouponService.read(anyLong())).thenReturn(mock(BookCoupon.class));

        Long bookRatioCouponId = 1L;
        BookRatioCoupon bookRatioCoupon = new BookRatioCoupon(mock(BookCoupon.class), request.rate(), request.max());
        bookRatioCoupon.setId(bookRatioCouponId);

        when(bookRatioCouponRepository.save(any(BookRatioCoupon.class))).thenReturn(bookRatioCoupon);

        Long createdId = bookRatioCouponService.create(request);

        verify(couponFormService, times(1)).create(any(), any(), any(), any(), anyInt(), anyInt());
        verify(bookCouponService, times(1)).create(any(), anyLong());
        verify(bookRatioCouponRepository, times(1)).save(any(BookRatioCoupon.class));
    }

    @Test
    void testRead_NotFound() {
        Long bookRatioCouponId = 1L;

        when(bookRatioCouponRepository.findById(bookRatioCouponId)).thenReturn(Optional.empty());

        assertThrows(BookRatioCouponNotExistException.class, () -> bookRatioCouponService.read(bookRatioCouponId));
        verify(bookRatioCouponRepository, times(1)).findById(bookRatioCouponId);
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

        when(bookRatioCouponCustomRepository.findBookRatioCoupon(couponFormId)).thenReturn(Optional.of(tuple));

        ApiBookRatioCouponResponse response = bookRatioCouponService.readAllData(couponFormId);

        assertNotNull(response);
        assertEquals(1L, response.bookRatioCouponId());
        assertEquals(2L, response.bookCouponId());
        assertEquals(3L, response.bookId());
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
        verify(bookRatioCouponCustomRepository, times(1)).findBookRatioCoupon(couponFormId);
    }

    @Test
    void testReadAllData_NotFound() {
        Long couponFormId = 1L;

        when(bookRatioCouponCustomRepository.findBookRatioCoupon(couponFormId)).thenReturn(Optional.empty());

        assertThrows(CouponFormNotExistException.class, () -> bookRatioCouponService.readAllData(couponFormId));
        verify(bookRatioCouponCustomRepository, times(1)).findBookRatioCoupon(couponFormId);
    }
}