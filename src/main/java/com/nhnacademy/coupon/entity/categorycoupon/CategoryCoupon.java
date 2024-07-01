package com.nhnacademy.coupon.entity.categorycoupon;


import com.nhnacademy.coupon.entity.categoryfixedcoupon.CategoryFixedCoupon;
import com.nhnacademy.coupon.entity.categoryratiocoupon.CategoryRatioCoupon;
import com.nhnacademy.coupon.entity.couponform.CouponForm;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter@Setter
public class CategoryCoupon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @MapsId
    @OneToOne
    private CouponForm couponForm;

    @Setter
    private long categoryId;

    public CategoryCoupon(CouponForm couponForm, long categoryId) {
        this.couponForm = couponForm;
        this.categoryId = categoryId;
    }
}
