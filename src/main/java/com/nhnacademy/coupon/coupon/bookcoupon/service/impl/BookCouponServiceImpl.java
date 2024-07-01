package com.nhnacademy.coupon.coupon.bookcoupon.service.impl;

import com.nhnacademy.coupon.coupon.bookcoupon.dto.ReadBookCouponResponse;
import com.nhnacademy.coupon.coupon.bookcoupon.exception.BookCouponNotExistException;
import com.nhnacademy.coupon.coupon.bookcoupon.repository.BookCouponRepository;
import com.nhnacademy.coupon.coupon.bookcoupon.service.BookCouponService;
import com.nhnacademy.coupon.entity.bookcoupon.BookCoupon;
import com.nhnacademy.coupon.entity.couponform.CouponForm;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 북쿠폰 서비스.
 *
 * @author 김병우
 */
@Transactional
@Service
@RequiredArgsConstructor
public class BookCouponServiceImpl implements BookCouponService {
    private final BookCouponRepository bookCouponRepository;

    /**
     * 북쿠폰 생성.
     *
     * @param couponForm 쿠폰폼
     * @param bookId 북아이디
     * @return 북쿠폰 아이디
     */
    @Override
    public Long create(CouponForm couponForm, long bookId) {
        BookCoupon bookCoupon = new BookCoupon(couponForm,bookId);
        bookCouponRepository.save(bookCoupon);
        return bookCoupon.getId();
    }

    /**
     * 북쿠폰 읽기.
     *
     * @param bookCouponId 북쿠폰아이디
     * @return 북쿠폰
     */
    @Override
    public BookCoupon read(Long bookCouponId) {
        return bookCouponRepository.findById(bookCouponId).orElseThrow(()->new BookCouponNotExistException(""));
    }
}
