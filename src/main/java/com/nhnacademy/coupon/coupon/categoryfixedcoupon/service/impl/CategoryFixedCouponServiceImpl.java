package com.nhnacademy.coupon.coupon.categoryfixedcoupon.service.impl;

import com.nhnacademy.coupon.coupon.categorycoupon.service.CategoryCouponService;
import com.nhnacademy.coupon.coupon.categoryfixedcoupon.dto.request.ApiCategoryFixedCouponRequest;
import com.nhnacademy.coupon.coupon.categoryfixedcoupon.dto.response.ApiCategoryFixedCouponResponse;
import com.nhnacademy.coupon.coupon.categoryfixedcoupon.dto.response.ReadCategoryFixedCouponResponse;
import com.nhnacademy.coupon.coupon.categoryfixedcoupon.exception.CategoryFixedCouponNotExistException;
import com.nhnacademy.coupon.coupon.categoryfixedcoupon.repository.CategoryFixedCouponCustomRepository;
import com.nhnacademy.coupon.coupon.categoryfixedcoupon.repository.CategoryFixedCouponRepository;
import com.nhnacademy.coupon.coupon.categoryfixedcoupon.service.CategoryFixedCouponService;
import com.nhnacademy.coupon.coupon.couponform.exception.CouponFormNotExistException;
import com.nhnacademy.coupon.coupon.couponform.service.CouponFormService;
import com.nhnacademy.coupon.entity.categoryfixedcoupon.CategoryFixedCoupon;
import com.querydsl.core.Tuple;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZonedDateTime;
import java.util.UUID;

/**
 * 카테고리 고정 쿠폰 서비스 구현체.
 *
 * @author 김병우
 */
@Transactional
@Service
@RequiredArgsConstructor
public class CategoryFixedCouponServiceImpl implements CategoryFixedCouponService {
    private final CategoryFixedCouponCustomRepository categoryFixedCouponCustomRepository;
    private final CategoryFixedCouponRepository categoryFixedCouponRepository;
    private final CategoryCouponService categoryCouponService;
    private final CouponFormService couponFormService;

    /**
     * 카테고리 고정 쿠폰 생성
     *
     * @param apiCategoryFixedCouponRequest 요청Dto
     * @return 카테고리고정쿠폰아이디
     */
    @Override
    public Long create(ApiCategoryFixedCouponRequest apiCategoryFixedCouponRequest) {
        long couponFormId = couponFormService.create(
                apiCategoryFixedCouponRequest.startDate(),
                apiCategoryFixedCouponRequest.endDate(),
                apiCategoryFixedCouponRequest.name(),
                UUID.randomUUID(),
                apiCategoryFixedCouponRequest.maxPrice(),
                apiCategoryFixedCouponRequest.minPrice()
        );

        long bookCouponId = categoryCouponService.create(
                couponFormService.read(couponFormId),
                apiCategoryFixedCouponRequest.categoryId()
        );

        CategoryFixedCoupon categoryFixedCoupon = new CategoryFixedCoupon(
                categoryCouponService.read(bookCouponId),
                apiCategoryFixedCouponRequest.price()
        );

        categoryFixedCouponRepository.save(categoryFixedCoupon);
        return categoryFixedCoupon.getId();
    }
    /**
     * 카테고리 고정 쿠폰 읽기
     *
     * @param categoryFixedCouponId 카테고리 고정 쿠폰 아이디
     * @return 반환 DTo
     */
    @Override
    public ReadCategoryFixedCouponResponse read(Long categoryFixedCouponId) {
        CategoryFixedCoupon categoryFixedCoupon = categoryFixedCouponRepository.findById(categoryFixedCouponId).orElseThrow(()-> new CategoryFixedCouponNotExistException(""));
        return ReadCategoryFixedCouponResponse.builder()
                .categoryCouponId(categoryFixedCoupon.getCategoryCoupon().getId())
                .categoryFixedCouponId(categoryFixedCoupon.getId())
                .price(categoryFixedCoupon.getPrice()).build();
    }

    /**
     * 카테고리고정 쿠폰 전체 읽기.
     *
     * @param couponFormId 쿠폰폼아이디
     * @return 반환DTo
     */
    @Override
    public ApiCategoryFixedCouponResponse readAllData(Long couponFormId){
        Tuple tuple = categoryFixedCouponCustomRepository
                .findCategoryFixedCoupon(couponFormId)
                .orElseThrow(() -> new CouponFormNotExistException("Next couponFormId: " + couponFormId));

        return new ApiCategoryFixedCouponResponse(
                tuple.get(0, Long.class),   // bookRatioCouponId
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
