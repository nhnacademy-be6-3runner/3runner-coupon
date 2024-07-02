package com.nhnacademy.coupon.coupon.couponform.listener;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nhnacademy.coupon.coupon.couponform.dto.request.CreateCouponFormRequest;
import com.nhnacademy.coupon.coupon.couponform.service.CouponFormService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class CouponFormListener {
    private static final String queueName1 = "3RUNNER-COUPON-ISSUED";
    private final CouponFormService couponFormService;
    private final ObjectMapper objectMapper;

    @RabbitListener(queues = queueName1)
    public void receiveMessage(String createCouponFormRequestJson) throws JsonProcessingException {
        log.info("{}",createCouponFormRequestJson);
        couponFormService.create(objectMapper.readValue(createCouponFormRequestJson, CreateCouponFormRequest.class));
    }
}
