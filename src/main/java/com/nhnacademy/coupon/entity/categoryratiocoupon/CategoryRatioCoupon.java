package com.nhnacademy.coupon.entity.categoryratiocoupon;


import com.nhnacademy.coupon.entity.categorycoupon.CategoryCoupon;
import com.nhnacademy.coupon.entity.couponform.CouponForm;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class CategoryRatioCoupon {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @MapsId
    @OneToOne
    private CategoryCoupon couponForm;

    @Setter
    private double discountRate;

    @Setter
    private Long maxDiscount;






}
