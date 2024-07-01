package com.nhnacademy.coupon.coupon.bookfixedcoupon.exception;


/**
 * 고정 북 쿠폰이 존재하지 않을 경우의 예외처리.
 *
 * @author 김병우
 */
public class BookFixedCouponNotExistException extends RuntimeException{
    public BookFixedCouponNotExistException(String message) {
        super(message);
    }
}
