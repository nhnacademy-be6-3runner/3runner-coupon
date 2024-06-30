package com.nhnacademy.coupon.coupon.categoryratiocoupon.service.impl;

import com.nhnacademy.coupon.coupon.categorycoupon.service.CategoryCouponService;
import com.nhnacademy.coupon.coupon.categoryratiocoupon.dto.request.ApiCategoryRatioCouponRequest;
import com.nhnacademy.coupon.coupon.categoryratiocoupon.dto.response.ApiCategoryRatioCouponResponse;
import com.nhnacademy.coupon.coupon.categoryratiocoupon.dto.response.ReadCategoryRatioCouponResponse;
import com.nhnacademy.coupon.coupon.categoryratiocoupon.exception.CategoryRatioCouponNotExistException;
import com.nhnacademy.coupon.coupon.categoryratiocoupon.repository.CategoryRatioCouponCustomRepository;
import com.nhnacademy.coupon.coupon.categoryratiocoupon.repository.CategoryRatioCouponRepository;
import com.nhnacademy.coupon.coupon.categoryratiocoupon.service.CategoryRatioCouponService;
import com.nhnacademy.coupon.coupon.couponform.exception.CouponFormNotExistException;
import com.nhnacademy.coupon.coupon.couponform.service.CouponFormService;
import com.nhnacademy.coupon.entity.categoryratiocoupon.CategoryRatioCoupon;
import com.querydsl.core.Tuple;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZonedDateTime;
import java.util.UUID;

/**
 * 카테고리 비율 쿠폰 서비스 인터페이스.
 *
 * @author 김병우
 */
@Transactional
@Service
@RequiredArgsConstructor
public class CategoryRatioCouponServiceImpl implements CategoryRatioCouponService {
    private final CategoryRatioCouponCustomRepository categoryRatioCouponCustomRepository;
    private final CategoryCouponService categoryCouponService;
    private final CouponFormService couponFormService;
    private final CategoryRatioCouponRepository categoryRatioCouponRepository;

    /**
     * 카테고리 비율 쿠폰 생성.
     *
     * @param apiCategoryRatioCouponRequest requst dto
     * @return 카테고리비율 쿠폰 아이디
     */
    @Override
    public Long create(ApiCategoryRatioCouponRequest apiCategoryRatioCouponRequest) {
        long couponFormId = couponFormService.create(
                apiCategoryRatioCouponRequest.startDate(),
                apiCategoryRatioCouponRequest.endDate(),
                apiCategoryRatioCouponRequest.name(),
                UUID.randomUUID(),
                apiCategoryRatioCouponRequest.maxPrice(),
                apiCategoryRatioCouponRequest.minPrice()
        );

        long bookCouponId = categoryCouponService.create(
                couponFormService.read(couponFormId),
                apiCategoryRatioCouponRequest.categoryId()
        );

        CategoryRatioCoupon categoryRatioCoupon = new CategoryRatioCoupon(
                categoryCouponService.read(bookCouponId),
                apiCategoryRatioCouponRequest.rate(),
                apiCategoryRatioCouponRequest.max()
        );

        categoryRatioCouponRepository.save(categoryRatioCoupon);
        return categoryRatioCoupon.getId();
    }

    /**
     * 카테고리비율쿠폰 읽기.
     *
     * @param categoryRatioCouponId 카테고리 비율 쿠폰 아이디
     * @return 반환Dto
     */
    @Override
    public ReadCategoryRatioCouponResponse read(Long categoryRatioCouponId) {
        CategoryRatioCoupon categoryRatioCoupon = categoryRatioCouponRepository.findById(categoryRatioCouponId).orElseThrow(()-> new CategoryRatioCouponNotExistException(""));
        return ReadCategoryRatioCouponResponse.builder()
                .categoryCouponId(categoryRatioCoupon.getCategoryCoupon().getId())
                .categoryRatioCouponId(categoryRatioCoupon.getId())
                .rate(categoryRatioCoupon.getDiscountRate())
                .max(categoryRatioCoupon.getMaxDiscount()).build();
    }

    /**
     * 카테고리 비율 쿠폰 반환.
     *
     * @param couponFormId 쿠폰폼아이디
     * @return 반환Dto
     */
    @Override
    public ApiCategoryRatioCouponResponse readAllData(Long couponFormId) {
        Tuple tuple = categoryRatioCouponCustomRepository
                .findCategoryRatioCoupon(couponFormId)
                .orElseThrow(() -> new CouponFormNotExistException("Next couponFormId: " + couponFormId));

        return new ApiCategoryRatioCouponResponse(
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
