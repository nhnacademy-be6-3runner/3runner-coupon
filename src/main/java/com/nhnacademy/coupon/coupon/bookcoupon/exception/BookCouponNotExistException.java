package com.nhnacademy.coupon.coupon.bookcoupon.exception;


/**
 * 북쿠폰이 존재하지 않을 경우의 예외처리.
 *
 * @author 김병우
 */
public class BookCouponNotExistException extends RuntimeException{
    public BookCouponNotExistException(String message) {
        super(message);
    }
}
