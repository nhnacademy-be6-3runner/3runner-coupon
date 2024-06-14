package com.nhnacademy.coupon.entity.CatoryCoupon;


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
public class CategoryCoupon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    private long categoryId;

    @OneToMany(mappedBy ="categoryCoupon", fetch = FetchType.LAZY ,cascade = CascadeType.ALL, orphanRemoval = true)
    Set<CouponForm> couponFormList = new HashSet<>();

}
