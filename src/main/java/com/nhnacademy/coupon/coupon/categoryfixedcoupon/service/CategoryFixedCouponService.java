package com.nhnacademy.coupon.coupon.categoryfixedcoupon.service;

import com.nhnacademy.coupon.coupon.categoryfixedcoupon.dto.request.ApiCategoryFixedCouponRequest;
import com.nhnacademy.coupon.coupon.categoryfixedcoupon.dto.response.ApiCategoryFixedCouponResponse;
import com.nhnacademy.coupon.coupon.categoryfixedcoupon.dto.response.ReadCategoryFixedCouponResponse;

/**
 * 카테고리 고정 쿠폰 서비스 인터페이스.
 *
 * @author 김병우
 */
public interface CategoryFixedCouponService {
    Long create(ApiCategoryFixedCouponRequest apiCategoryFixedCouponRequest);
    ReadCategoryFixedCouponResponse read(Long categoryFixedCouponId);
    ApiCategoryFixedCouponResponse readAllData(Long categoryFixedCouponId);
}
