package com.nhnacademy.coupon.entity.RatioCoupon;


import com.nhnacademy.coupon.entity.couponForm.CouponForm;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class RatioCoupon {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Setter
    private double discountRate;

    @Setter
    private Long maxDiscount;

    @OneToMany(mappedBy = "ratioCoupon",fetch = FetchType.LAZY,cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<CouponForm> couponFormList = new HashSet<>();


}
