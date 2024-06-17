package com.nhnacademy.coupon.entity.couponpolicy;


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
public class CouponPolicy {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;

    @Setter
    Integer maxPrice;
    @Setter
    Integer minPrice;

    @OneToMany(mappedBy ="couponPolicy", fetch = FetchType.LAZY ,cascade = CascadeType.ALL, orphanRemoval = true)
    Set<CouponForm> couponFormList = new HashSet<>();

}
