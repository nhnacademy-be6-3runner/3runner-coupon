package com.nhnacademy.coupon.entity.couponForm;


import com.nhnacademy.coupon.entity.BookCoupon.BookCoupon;
import com.nhnacademy.coupon.entity.CatoryCoupon.CategoryCoupon;
import com.nhnacademy.coupon.entity.FixedCoupon.FixedCoupon;
import com.nhnacademy.coupon.entity.RatioCoupon.RatioCoupon;
import com.nhnacademy.coupon.entity.couponpolicy.CouponPolicy;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.ZonedDateTime;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
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

    @ManyToOne
    @Setter
    RatioCoupon ratioCoupon;

    @ManyToOne
    @Setter
    CategoryCoupon categoryCoupon;

    @ManyToOne
    @Setter
    FixedCoupon fixedCoupon;

    @ManyToOne
    @Setter
    BookCoupon bookCoupon;

    @ManyToOne
    @Setter
    CouponPolicy couponPolicy;



    @PrePersist
    protected void onCreate() {
        this.createdAt = ZonedDateTime.now();
    }

    public CouponForm(ZonedDateTime startDate,ZonedDateTime endDate, String name, RatioCoupon ratioCoupon, FixedCoupon fixedCoupon,CategoryCoupon categoryCoupon, BookCoupon bookCoupon) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.name = name;
        this.ratioCoupon = ratioCoupon;
        this.fixedCoupon = fixedCoupon;
        this.bookCoupon = bookCoupon;
    }




}
