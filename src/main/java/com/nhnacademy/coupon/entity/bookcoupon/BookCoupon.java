package com.nhnacademy.coupon.entity.bookcoupon;

import com.nhnacademy.coupon.entity.bookfixedcoupon.BookFixedCoupon;
import com.nhnacademy.coupon.entity.bookratiocoupon.BookRatioCoupon;
import com.nhnacademy.coupon.entity.couponform.CouponForm;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class BookCoupon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @MapsId
    @OneToOne
    private CouponForm couponForm;

    private long bookId;

    public BookCoupon(CouponForm couponForm, long bookId) {
        this.couponForm = couponForm;
        this.bookId = bookId;
    }

}
