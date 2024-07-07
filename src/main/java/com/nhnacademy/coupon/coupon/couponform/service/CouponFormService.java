package com.nhnacademy.coupon.coupon.couponform.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.nhnacademy.coupon.coupon.couponform.dto.request.CreateCouponFormRequest;
import com.nhnacademy.coupon.coupon.couponform.dto.ReadCouponFormResponse;
import com.nhnacademy.coupon.entity.couponform.CouponForm;

import java.util.List;

/**
 * 쿠폰 폼 서비스 인터페이스.
 *
 * @author 김병우
 */
public interface CouponFormService {
    Long create(CreateCouponFormRequest readCouponFormRequest);
    CouponForm read(Long couponFormId);
    List<ReadCouponFormResponse> readAll(List<Long> couponFormIds);
    void sendNoticeCouponsExpiringThreeDaysLater() throws JsonProcessingException;
    List<ReadCouponFormResponse> readAllForms();
}
