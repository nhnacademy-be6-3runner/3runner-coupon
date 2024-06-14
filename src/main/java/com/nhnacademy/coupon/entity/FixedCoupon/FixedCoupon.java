package com.nhnacademy.coupon.entity.FixedCoupon;


import com.nhnacademy.coupon.entity.couponForm.CouponForm;
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
@Getter
public class FixedCoupon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Setter
    private int price;

    @OneToMany(mappedBy ="fixedCoupon", fetch = FetchType.LAZY ,cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<CouponForm> couponFormList = new HashSet<>();


}
