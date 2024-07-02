package com.nhnacademy.coupon.coupon.couponform.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nhnacademy.coupon.coupon.couponform.dto.request.CreateCouponFormRequest;
import com.nhnacademy.coupon.coupon.couponform.dto.ReadCouponFormResponse;
import com.nhnacademy.coupon.coupon.couponform.exception.CouponFormNotExistException;
import com.nhnacademy.coupon.coupon.couponform.repository.CouponFormRepository;
import com.nhnacademy.coupon.coupon.couponform.service.CouponFormService;
import com.nhnacademy.coupon.coupon.coupontype.exception.CouponTypeDoesNotExistException;
import com.nhnacademy.coupon.coupon.coupontype.repository.CouponTypeRepository;
import com.nhnacademy.coupon.coupon.couponusage.exception.CouponUsageDoesNotExistException;
import com.nhnacademy.coupon.coupon.couponusage.repository.CouponUsageRespository;
import com.nhnacademy.coupon.entity.couponform.CouponForm;
import com.nhnacademy.coupon.entity.coupontype.CouponType;
import com.nhnacademy.coupon.entity.couponusage.CouponUsage;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

/**
 * 쿠폰폼 서비스 구현체.
 *
 * @author 김병우
 */
@Service
@RequiredArgsConstructor
@Transactional
public class CouponFormServiceImpl implements CouponFormService {
    private final CouponUsageRespository couponUsageRespository;
    private final CouponTypeRepository couponTypeRepository;
    private final CouponFormRepository couponFormRepository;
    private final RabbitTemplate rabbitTemplate;
    private final ObjectMapper objectMapper;
    private static final String queueName2 = "3RUNNER-COUPON-EXPIRED-IN-THREE-DAY";

    /**
     * 쿠폰폼생성.
     *
     * @param createCouponFormRequest Dto 폼
     * @return 쿠폰폼 아이디
     */
    @Override
    public Long create(CreateCouponFormRequest createCouponFormRequest) {
        CouponUsage couponUsage = couponUsageRespository
                .findById(createCouponFormRequest.couponUsageId())
                .orElseThrow(
                        ()-> new CouponUsageDoesNotExistException(createCouponFormRequest.couponUsageId()+"가 존재하지 않습니다.")
                );

        CouponType couponType = couponTypeRepository
                .findById(createCouponFormRequest.couponTypeId())
                .orElseThrow(
                        ()-> new CouponTypeDoesNotExistException(createCouponFormRequest.couponTypeId()+"가 존재하지 않습니다.")
                );

        CouponForm couponForm = new CouponForm(
                createCouponFormRequest.startDate(),
                createCouponFormRequest.endDate(),
                createCouponFormRequest.name(),
                UUID.randomUUID(),
                createCouponFormRequest.maxPrice(),
                createCouponFormRequest.minPrice(),
                couponType,
                couponUsage
        );
        couponFormRepository.save(couponForm);
        return couponForm.getId();
    }

    /**
     * 쿠폰폼 읽기
     *
     * @param couponFormId 쿠폰폼아이디
     * @return 쿠폰폼
     */
    @Override
    public CouponForm read(Long couponFormId) {
        return couponFormRepository.findById(couponFormId).orElseThrow(()->new CouponFormNotExistException(""));
    }

    @Override
    public List<ReadCouponFormResponse> readAll(List<Long> couponFormIds) {
        List<CouponForm> couponForms = couponFormRepository.findAllByIdIn(couponFormIds);
        return couponForms.stream()
                .map(couponForm -> ReadCouponFormResponse.builder()
                        .couponFormId(couponForm.getId())
                        .startDate(couponForm.getStartDate())
                        .endDate(couponForm.getEndDate())
                        .createdAt(couponForm.getCreatedAt())
                        .name(couponForm.getName())
                        .code(couponForm.getCode())
                        .maxPrice(couponForm.getMaxPrice())
                        .minPrice(couponForm.getMinPrice())
                        .couponType(couponForm.getCouponType())
                        .couponUsage(couponForm.getCouponUsage())
                        .build())
                .toList();
    }
    /**
     * 쿠폰폼 스케쥴러 오후한시에 만료가 3일 남은 쿠폰 메시지 보내는 메소드.
     *
     */
    @Override
    @Async
    @Scheduled(cron = "0 0 13 * * ?")
    public void sendNoticeCouponsExpiringThreeDaysLater() throws JsonProcessingException {
        List<CouponForm> couponsExpiringThreeDaysLater = couponFormRepository.findCouponsExpiringThreeDaysLater();
        rabbitTemplate.convertAndSend(queueName2, objectMapper.writeValueAsString(couponsExpiringThreeDaysLater));
    }
}