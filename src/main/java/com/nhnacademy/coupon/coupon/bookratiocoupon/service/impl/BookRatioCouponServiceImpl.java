package com.nhnacademy.coupon.coupon.bookratiocoupon.service.impl;

import com.nhnacademy.coupon.coupon.bookcoupon.service.BookCouponService;
import com.nhnacademy.coupon.coupon.bookratiocoupon.dto.request.ApiBookRatioCouponRequest;
import com.nhnacademy.coupon.coupon.bookratiocoupon.dto.response.ApiBookRatioCouponResponse;
import com.nhnacademy.coupon.coupon.bookratiocoupon.dto.response.ReadBookRatioCouponResponse;
import com.nhnacademy.coupon.coupon.bookratiocoupon.exception.BookRatioCouponNotExistException;
import com.nhnacademy.coupon.coupon.bookratiocoupon.repository.BookRatioCouponCustomRepository;
import com.nhnacademy.coupon.coupon.bookratiocoupon.repository.BookRatioCouponRepository;
import com.nhnacademy.coupon.coupon.bookratiocoupon.service.BookRatioCouponService;
import com.nhnacademy.coupon.coupon.couponform.exception.CouponFormNotExistException;
import com.nhnacademy.coupon.coupon.couponform.service.CouponFormService;
import com.nhnacademy.coupon.entity.bookratiocoupon.BookRatioCoupon;
import com.querydsl.core.Tuple;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZonedDateTime;
import java.util.UUID;

/**
 * 북 비율 쿠폰 서비스 구현체.
 *
 * @author 김병우
 */
@Transactional
@Service
@RequiredArgsConstructor
public class BookRatioCouponServiceImpl implements BookRatioCouponService {
    private final BookRatioCouponCustomRepository  bookRatioCouponCustomRepository;
    private final BookRatioCouponRepository bookRatioCouponRepository;
    private final CouponFormService couponFormService;
    private final BookCouponService bookCouponService;

    /**
     * 북 비율 쿠폰 생성
     *
     * @param apiBookRatioCouponRequest 요청DTO
     * @return 북 비율 쿠폰 아이디
     */
    @Override
    public Long create(ApiBookRatioCouponRequest apiBookRatioCouponRequest) {
        long couponFormId = couponFormService.create(
                apiBookRatioCouponRequest.startDate(),
                apiBookRatioCouponRequest.endDate(),
                apiBookRatioCouponRequest.name(),
                UUID.randomUUID(),
                apiBookRatioCouponRequest.maxPrice(),
                apiBookRatioCouponRequest.minPrice()
        );

        long bookCouponId = bookCouponService.create(
                couponFormService.read(couponFormId),
                apiBookRatioCouponRequest.bookId()
        );

        BookRatioCoupon bookRatioCoupon = new BookRatioCoupon(
                bookCouponService.read(bookCouponId),
                apiBookRatioCouponRequest.rate(),
                apiBookRatioCouponRequest.max()
        );

        bookRatioCouponRepository.save(bookRatioCoupon);
        return bookRatioCoupon.getId();
    }

    /**
     * 서비스 내에서만 사용하는 북 비율 쿠폰 아이디 읽기.
     *
     * @param bookRatioCouponId 북 비율 쿠폰 아이디
     * @return 반환 DTO
     */
    @Override
    public ReadBookRatioCouponResponse read(Long bookRatioCouponId) {
        BookRatioCoupon bookRatioCoupon = bookRatioCouponRepository.findById(bookRatioCouponId).orElseThrow(()->new BookRatioCouponNotExistException(""));
        return ReadBookRatioCouponResponse.builder()
                .bookCouponId(bookRatioCoupon.getBookCoupon().getId())
                .bookRatioCouponId(bookRatioCoupon.getId())
                .max(bookRatioCoupon.getMaxDiscount())
                .rate(bookRatioCoupon.getDiscountRate())
                .build();
    }

    /**
     * 북 비율 쿠폰 모든 정보 반환
     *
     * @param couponFormId 쿠폰폼아이디
     * @return 반환 DTO
     */
    @Override
    public ApiBookRatioCouponResponse readAllData(Long couponFormId) {
        Tuple tuple = bookRatioCouponCustomRepository
                .findBookRatioCoupon(couponFormId)
                .orElseThrow(() -> new CouponFormNotExistException("Next couponFormId: " + couponFormId));

        return new ApiBookRatioCouponResponse(
                tuple.get(0, Long.class),   // bookRatioCouponId
                tuple.get(1, Long.class),   // bookCouponId
                tuple.get(2, Long.class),   // bookId
                tuple.get(3, Long.class),   // couponFormId
                tuple.get(4, ZonedDateTime.class),   // startDate
                tuple.get(5, ZonedDateTime.class),   // endDate
                tuple.get(6, ZonedDateTime.class),   // createdAt
                tuple.get(7, String.class),   // name
                tuple.get(8, UUID.class),   // code
                tuple.get(9, Double.class),   // price
                tuple.get(10, Long.class),   // price
                tuple.get(11, Integer.class),   // maxPrice
                tuple.get(12, Integer.class)    // minPrice
        );
    }
}
