package com.nhnacademy.coupon.global.exceptionHandler;

import lombok.Builder;

@Builder
public record ErrorResponseForm(String title, int status, String timestamp) {
}