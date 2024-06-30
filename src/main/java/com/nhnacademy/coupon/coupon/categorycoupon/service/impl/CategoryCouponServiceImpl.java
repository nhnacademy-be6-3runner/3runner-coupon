package com.nhnacademy.coupon.coupon.categorycoupon.service.impl;

import com.nhnacademy.coupon.coupon.categorycoupon.exception.CategoryCouponNotExistException;
import com.nhnacademy.coupon.coupon.categorycoupon.repository.CategoryCouponRepository;
import com.nhnacademy.coupon.coupon.categorycoupon.service.CategoryCouponService;
import com.nhnacademy.coupon.entity.categorycoupon.CategoryCoupon;
import com.nhnacademy.coupon.entity.couponform.CouponForm;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 카테고리 쿠폰 서비스 구현체.
 *
 * @author 김병우
 */
@Transactional
@RequiredArgsConstructor
@Service
public class CategoryCouponServiceImpl implements CategoryCouponService {
    private final CategoryCouponRepository categoryCouponRepository;

    /**
     * 카테고리쿠폰 생성.
     *
     * @param couponForm 쿠폰 폼
     * @param categoryId 카테고리 아이디
     * @return 카테고리쿠폰 아이디
     */
    @Override
    public Long create(CouponForm couponForm, Long categoryId) {
        CategoryCoupon categoryCoupon = new CategoryCoupon(couponForm,categoryId);
        categoryCouponRepository.save(categoryCoupon);
        return categoryCoupon.getId();
    }

    /**
     * 카테고리 쿠폰 읽기.
     *
     * @param categoryCouponId 카테고리 쿠폰 아이디
     * @return 카테고리 쿠폰
     */
    @Override
    public CategoryCoupon read(Long categoryCouponId) {
        return categoryCouponRepository.findById(categoryCouponId).orElseThrow(()->new CategoryCouponNotExistException(""));
    }
}
