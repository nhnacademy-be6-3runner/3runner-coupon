package com.nhnacademy.coupon.coupon.categorycoupon.exception;

/**
 * 카테고리 쿠폰이 존재하지 않을 경우의 예외처리.
 *
 * @author 김병우
 */
public class CategoryCouponNotExistException extends RuntimeException{
    public CategoryCouponNotExistException(String message) {
        super(message);
    }
}
