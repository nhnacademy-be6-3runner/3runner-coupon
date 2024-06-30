package com.nhnacademy.coupon.coupon.bookcoupon.service.impl;

import com.nhnacademy.coupon.coupon.bookcoupon.exception.BookCouponNotExistException;
import com.nhnacademy.coupon.coupon.bookcoupon.repository.BookCouponRepository;
import com.nhnacademy.coupon.entity.bookcoupon.BookCoupon;
import com.nhnacademy.coupon.entity.couponform.CouponForm;
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
class BookCouponServiceImplTest {

    @Mock
    private BookCouponRepository bookCouponRepository;

    @InjectMocks
    private BookCouponServiceImpl bookCouponService;

    @Test
    void testCreate() {
        CouponForm couponForm = new CouponForm(); // 필요한 필드 설정
        long bookId = 1L;
        BookCoupon bookCoupon = new BookCoupon(couponForm, bookId);
        bookCoupon.setId(1L);

        when(bookCouponRepository.save(any(BookCoupon.class))).thenReturn(bookCoupon);

        Long createdId = bookCouponService.create(couponForm, bookId);

        verify(bookCouponRepository, times(1)).save(any(BookCoupon.class));
    }

    @Test
    void testRead() {
        Long bookCouponId = 1L;
        BookCoupon bookCoupon = new BookCoupon();
        bookCoupon.setId(bookCouponId);

        when(bookCouponRepository.findById(bookCouponId)).thenReturn(Optional.of(bookCoupon));

        BookCoupon foundBookCoupon = bookCouponService.read(bookCouponId);

        assertNotNull(foundBookCoupon);
        assertEquals(bookCouponId, foundBookCoupon.getId());
        verify(bookCouponRepository, times(1)).findById(bookCouponId);
    }

    @Test
    void testRead_NotFound() {
        Long bookCouponId = 1L;

        when(bookCouponRepository.findById(bookCouponId)).thenReturn(Optional.empty());

        assertThrows(BookCouponNotExistException.class, () -> bookCouponService.read(bookCouponId));
        verify(bookCouponRepository, times(1)).findById(bookCouponId);
    }
}