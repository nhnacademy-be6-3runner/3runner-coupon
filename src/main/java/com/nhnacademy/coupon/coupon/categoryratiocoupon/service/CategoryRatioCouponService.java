package com.nhnacademy.coupon.coupon.categoryratiocoupon.service;

import com.nhnacademy.coupon.coupon.categoryratiocoupon.dto.request.ApiCategoryRatioCouponRequest;
import com.nhnacademy.coupon.coupon.categoryratiocoupon.dto.response.ApiCategoryRatioCouponResponse;
import com.nhnacademy.coupon.coupon.categoryratiocoupon.dto.response.ReadCategoryRatioCouponResponse;

/**
 * 카테고리 비율 쿠폰 서비스 구현체.
 *
 * @author 김병우
 */
public interface CategoryRatioCouponService {
    Long create(ApiCategoryRatioCouponRequest apiCategoryRatioCouponRequest);
    ReadCategoryRatioCouponResponse read(Long categoryRatioCouponId);
    ApiCategoryRatioCouponResponse readAllData(Long categoryRatioCouponId);
}
