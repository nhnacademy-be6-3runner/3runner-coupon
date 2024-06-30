package com.nhnacademy.coupon.coupon.categoryfixedcoupon.exception;

/**
 * 카테고리 고정 쿠폰이 존재하지 않을 경우의 예외처리.
 *
 * @author 김병우
 */
public class CategoryFixedCouponNotExistException extends RuntimeException{
    public CategoryFixedCouponNotExistException(String message) {
        super(message);
    }
}
