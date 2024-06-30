package com.nhnacademy.coupon.entity.couponform;

import java.time.ZonedDateTime;
import java.util.UUID;

import com.nhnacademy.coupon.entity.bookcoupon.BookCoupon;
import com.nhnacademy.coupon.entity.categorycoupon.CategoryCoupon;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrePersist;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor@Setter
public class CouponForm {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Setter
    ZonedDateTime startDate;
    @Setter
    ZonedDateTime endDate;
    ZonedDateTime createdAt;

    @Setter
    @NotNull
    String name;

    @Setter
    UUID code;

    @Setter
    Integer maxPrice;

    @Setter
    Integer minPrice;


    @PrePersist
    protected void onCreate() {
        this.createdAt = ZonedDateTime.now();
    }

    public CouponForm(ZonedDateTime startDate, ZonedDateTime endDate, String name, UUID code, Integer maxPrice, Integer minPrice) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.name = name;
        this.code = code;
        this.maxPrice = maxPrice;
        this.minPrice = minPrice;
    }
}
