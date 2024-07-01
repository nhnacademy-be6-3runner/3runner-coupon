package com.nhnacademy.coupon.coupon.bookfixedcoupon.service.impl;

import com.nhnacademy.coupon.coupon.bookcoupon.service.BookCouponService;
import com.nhnacademy.coupon.coupon.bookfixedcoupon.dto.request.ApiBookFixedCouponRequest;
import com.nhnacademy.coupon.coupon.bookfixedcoupon.dto.response.ApiBookFixedCouponResponse;
import com.nhnacademy.coupon.coupon.bookfixedcoupon.dto.response.ReadBookFixedCouponResponse;
import com.nhnacademy.coupon.coupon.bookfixedcoupon.exception.BookFixedCouponNotExistException;
import com.nhnacademy.coupon.coupon.bookfixedcoupon.repository.BookFixedCouponCustomRepository;
import com.nhnacademy.coupon.coupon.bookfixedcoupon.repository.BookFixedCouponRepository;
import com.nhnacademy.coupon.coupon.bookfixedcoupon.service.BookFixedCouponService;
import com.nhnacademy.coupon.coupon.couponform.exception.CouponFormNotExistException;
import com.nhnacademy.coupon.coupon.couponform.service.CouponFormService;
import com.nhnacademy.coupon.entity.bookfixedcoupon.BookFixedCoupon;
import com.querydsl.core.Tuple;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZonedDateTime;
import java.util.UUID;

/**
 * 고정 북 쿠폰 서비스 구현체.
 *
 * @author 김병우
 */
@Transactional
@Service
@RequiredArgsConstructor
public class BookFixedCouponServiceImpl implements BookFixedCouponService {
    private final BookCouponService bookCouponService;
    private final CouponFormService couponFormService;
    private final BookFixedCouponRepository bookFixedCouponRepository;
    private final BookFixedCouponCustomRepository bookFixedCouponCustomRepository;

    /**
     * 고정 북 쿠폰 생성.
     *
     * @param apiBookFixedCouponRequest 요청 dto
     * @return 고정 북 쿠폰 아이디
     */
    @Override
    public Long create(ApiBookFixedCouponRequest apiBookFixedCouponRequest) {
        long couponFormId = couponFormService.create(
                apiBookFixedCouponRequest.startDate(),
                apiBookFixedCouponRequest.endDate(),
                apiBookFixedCouponRequest.name(),
                UUID.randomUUID(),
                apiBookFixedCouponRequest.maxPrice(),
                apiBookFixedCouponRequest.minPrice()
        );

        long bookCouponId = bookCouponService.create(
                couponFormService.read(couponFormId),
                apiBookFixedCouponRequest.bookId()
        );

        BookFixedCoupon bookFixedCoupon = new BookFixedCoupon(bookCouponService.read(bookCouponId),apiBookFixedCouponRequest.price());
        bookFixedCouponRepository.save(bookFixedCoupon);
        return bookFixedCoupon.getId();
    }

    /**
     *  서비스 내에서 사용하는 고정북쿠폰 읽기.
     *
     * @param bookFixedCouponId 북쿠폰 아이디
     * @return Dto
     */
    @Override
    public ReadBookFixedCouponResponse read(Long bookFixedCouponId) {
        BookFixedCoupon bookFixedCoupon = bookFixedCouponRepository.findById(bookFixedCouponId).orElseThrow(()->new BookFixedCouponNotExistException(""));
        return ReadBookFixedCouponResponse.builder()
                .bookCouponId(bookFixedCoupon.getBookCoupon().getId())
                .price(bookFixedCoupon.getPrice())
                .bookFixedCouponId(bookFixedCouponId).build();
    }

    /**
     * 고정 북 쿠폰 api 읽기
     *
     * @param couponFormId 쿠폰 폼 아이디
     * @return Dto
     */
    @Override
    public ApiBookFixedCouponResponse readAllData(Long couponFormId) {
        Tuple tuple = bookFixedCouponCustomRepository
                .findBookFixedCoupon(couponFormId)
                .orElseThrow(() -> new CouponFormNotExistException("Next couponFormId: " + couponFormId));

        return new ApiBookFixedCouponResponse(
                tuple.get(0, Long.class),   // bookFixedCouponId
                tuple.get(1, Long.class),   // bookCouponId
                tuple.get(2, Long.class),   // bookId
                tuple.get(3, Long.class),   // couponFormId
                tuple.get(4, ZonedDateTime.class),   // startDate
                tuple.get(5, ZonedDateTime.class),   // endDate
                tuple.get(6, ZonedDateTime.class),   // createdAt
                tuple.get(7, String.class),   // name
                tuple.get(8, UUID.class),   // code
                tuple.get(9, Integer.class),   // price
                tuple.get(10, Integer.class),   // maxPrice
                tuple.get(11, Integer.class)    // minPrice
        );
    }
}
