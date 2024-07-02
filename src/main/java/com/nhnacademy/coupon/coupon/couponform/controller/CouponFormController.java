package com.nhnacademy.coupon.coupon.couponform.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nhnacademy.coupon.coupon.couponform.dto.request.CreateCouponFormRequest;
import com.nhnacademy.coupon.coupon.couponform.dto.ReadCouponFormResponse;
import com.nhnacademy.coupon.coupon.couponform.dto.request.ReadCouponFormRequest;
import com.nhnacademy.coupon.coupon.couponform.service.CouponFormService;
import com.nhnacademy.coupon.global.util.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 쿠폰 폼 컨트롤러.
 *
 * @author 김병우
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/coupon")
public class CouponFormController {
    private final RabbitTemplate rabbitTemplate;
    private final CouponFormService couponFormService;
    private final ObjectMapper objectMapper;
    private static final String queueName1 = "3RUNNER-COUPON-ISSUED";


    @PostMapping("/members/forms")
    public ApiResponse<List<ReadCouponFormResponse>> readCouponForm(
            @RequestBody ReadCouponFormRequest readCouponFormRequest
            ){
        return ApiResponse.success(couponFormService.readAll(readCouponFormRequest.couponFormIds()));
    }

    @PostMapping("/forms")
    public ApiResponse<Long> createCouponForm(@RequestBody CreateCouponFormRequest createCouponFormRequest) throws JsonProcessingException {
        //rabbitTemplate.convertAndSend(queueName1, objectMapper.writeValueAsString(createCouponFormRequest));
        return ApiResponse.createSuccess(couponFormService.create(createCouponFormRequest));
    }
}
