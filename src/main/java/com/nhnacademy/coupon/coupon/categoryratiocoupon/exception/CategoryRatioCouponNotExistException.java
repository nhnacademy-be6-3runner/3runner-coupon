package com.nhnacademy.coupon.coupon.categoryratiocoupon.exception;

/**
 * 카테고리 비율 쿠폰이 존재하지 않을 경우의 예외처리.
 *
 * @author 김병우
 */
public class CategoryRatioCouponNotExistException extends RuntimeException{
    public CategoryRatioCouponNotExistException(String message) {
        super(message);
    }
}
