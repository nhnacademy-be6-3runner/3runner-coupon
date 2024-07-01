package com.nhnacademy.coupon.coupon.couponform.service.impl;

import com.nhnacademy.coupon.coupon.couponform.dto.ReadCouponFormResponse;
import com.nhnacademy.coupon.coupon.couponform.exception.CouponFormNotExistException;
import com.nhnacademy.coupon.coupon.couponform.repository.CouponFormRepository;
import com.nhnacademy.coupon.coupon.couponform.service.CouponFormService;
import com.nhnacademy.coupon.entity.couponform.CouponForm;
import com.nhnacademy.coupon.global.config.RabbitMqConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZonedDateTime;
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
    private final CouponFormRepository couponFormRepository;
    private final RabbitTemplate rabbitTemplate;
    private static final String queueName1 = "3RUNNER-COUPON-EXPIRED-TODAY";
    private static final String queueName2 = "3RUNNER-COUPON-EXPIRED-IN-THREE-DAY";

    /**
     * 쿠폰폼 생성
     *
     * @param startDate 쿠폰시작일
     * @param endDate 쿠폰만료일
     * @param name 쿠폰이름
     * @param code 쿠폰 코드
     * @param maxPrice 쿠폰사용최대금액
     * @param minPrice 쿠폰사용최소금액
     * @return 쿠폼폼아이디
     */
    @Override
    public Long create(ZonedDateTime startDate, ZonedDateTime endDate, String name, UUID code, Integer maxPrice, Integer minPrice) {
        CouponForm couponForm = new CouponForm(startDate,endDate, name, code, maxPrice, minPrice);
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
                        .build())
                .toList();
    }
    /**
     * 쿠폰폼 스케쥴러 오후한시에 만료가 하루남은 쿠폰 메시지 보내는 메소드.
     *
     */
    @Override
    @Async
    @Scheduled(cron = "0 0 13 * * ?")
    public void sendNoticeCouponsExpiringToday(){
        List<CouponForm> couponsExpiringToday = couponFormRepository.findCouponsExpiringToday();
        rabbitTemplate.convertAndSend(queueName1, couponsExpiringToday);
    }


    /**
     * 쿠폰폼 스케쥴러 오후한시에 만료가 3일 남은 쿠폰 메시지 보내는 메소드.
     *
     */
    @Override
    @Async
    @Scheduled(cron = "0 0 13 * * ?")
    public void sendNoticeCouponsExpiringThreeDaysLater(){
        List<CouponForm> couponsExpiringThreeDaysLater = couponFormRepository.findCouponsExpiringThreeDaysLater();
        rabbitTemplate.convertAndSend(queueName2, couponsExpiringThreeDaysLater);
    }
}
