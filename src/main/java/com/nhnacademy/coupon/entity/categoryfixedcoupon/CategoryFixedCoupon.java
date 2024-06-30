package com.nhnacademy.coupon.entity.categoryfixedcoupon;


import com.nhnacademy.coupon.entity.categorycoupon.CategoryCoupon;
import com.nhnacademy.coupon.entity.couponform.CouponForm;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter@Setter
public class CategoryFixedCoupon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @MapsId
    @OneToOne
    private CategoryCoupon categoryCoupon;

    @Setter
    private int price;

    public CategoryFixedCoupon(CategoryCoupon categoryCoupon, int price) {
        this.categoryCoupon = categoryCoupon;
        this.price = price;
    }
}
