package com.nhnacademy.coupon.coupon.categorycoupon.dto;

import lombok.Builder;

/**
 * 카테고리 쿠폰 반환 Response Dto.
 *
 * @author 김병우
 * @param categoryId 카테고리 아이디
 * @param couponFormId 쿠폰 폼 아이디
 */
@Builder
public record ReadCategoryCouponResponse(Long categoryId, Long couponFormId) {
}
