package com.nhnacademy.coupon.coupon.ratiocoupon.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nhnacademy.coupon.coupon.coupontype.exception.CouponTypeDoesNotExistException;
import com.nhnacademy.coupon.coupon.coupontype.repository.CouponTypeRepository;
import com.nhnacademy.coupon.coupon.fixedcoupon.exception.FixedCouponDoesNotExistException;
import com.nhnacademy.coupon.coupon.ratiocoupon.dto.response.ReadRatioCouponResponse;
import com.nhnacademy.coupon.coupon.ratiocoupon.repository.RatioCouponRepository;
import com.nhnacademy.coupon.coupon.ratiocoupon.service.RatioCouponService;
import com.nhnacademy.coupon.entity.coupontype.CouponType;
import com.nhnacademy.coupon.entity.ratiocoupon.RatioCoupon;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 비율 쿠폰 서비스 구현체
 *
 * @author 김병우
 */
@Service
@RequiredArgsConstructor
@Transactional
public class RatioCouponServiceImpl implements RatioCouponService {
    private final ObjectMapper objectMapper;
    private final RatioCouponRepository ratioCouponRepository;
    private final CouponTypeRepository couponTypeRepository;

    /**
     * 비율 쿠폰 생성.
     *
     * @param discountRate 할인비율
     * @param discountMaxPrice 최대할인가격
     * @return 쿠폰타입 아이디
     */
    @Override
    public Long create(double discountRate, int discountMaxPrice) {
        String type = createJsonType(discountRate,discountMaxPrice);

        CouponType couponType = new CouponType(type);
        couponTypeRepository.save(couponType);

        RatioCoupon ratioCoupon = new RatioCoupon(couponType, discountRate, discountMaxPrice);
        ratioCouponRepository.save(ratioCoupon);

        return couponType.getId();
    }

    /**
     * 비율 쿠폰 읽기.
     *
     * @param couponTypeId 쿠폰타입아이디
     * @return 반환Dto
     */
    @Override
    public ReadRatioCouponResponse read(Long couponTypeId) {
        CouponType couponType = couponTypeRepository
                .findById(couponTypeId)
                .orElseThrow(()-> new CouponTypeDoesNotExistException(couponTypeId+"가 없습니다"));

        RatioCoupon ratioCoupon = ratioCouponRepository
                .findByCouponType(couponType)
                .orElseThrow(
                        ()->new FixedCouponDoesNotExistException(couponType.getType()+"가 없습니다")
                );


        return ReadRatioCouponResponse.builder()
                .ratioCouponId(ratioCoupon.getId())
                .couponTypeId(couponTypeId)
                .discountRate(ratioCoupon.getDiscountRate())
                .discountMaxPrice(ratioCoupon.getDiscountMaxPrice())
                .build();
    }

    private String createJsonType(double discountRate, int discountMaxPrice) {
        try {
            return objectMapper.writeValueAsString(new RatioCouponType(discountRate, discountMaxPrice));
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error converting to JSON", e);
        }
    }

    private static class RatioCouponType {
        public double discountRate;
        public int discountMaxPrice;

        public RatioCouponType(double discountRate, int discountMaxPrice) {
            this.discountRate = discountRate;
            this.discountMaxPrice = discountMaxPrice;
        }
    }
}
