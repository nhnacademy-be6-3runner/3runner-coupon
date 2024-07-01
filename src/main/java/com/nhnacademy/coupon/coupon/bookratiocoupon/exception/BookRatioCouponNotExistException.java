package com.nhnacademy.coupon.coupon.bookratiocoupon.exception;

/**
 * 비율 북 쿠폰이 존재하지 않을 경우의 예외처리.
 *
 * @author 김병우
 */
public class BookRatioCouponNotExistException extends RuntimeException{
    public BookRatioCouponNotExistException(String message) {
        super(message);
    }
}
