package com.nhnacademy.coupon.coupon.fixedcoupon.exception;

public class FixedCouponDoesNotExistException extends RuntimeException{
    public FixedCouponDoesNotExistException(String message) {
        super(message);
    }
}
