package com.nhnacademy.coupon.entity.bookratiocoupon;

import com.nhnacademy.coupon.entity.bookcoupon.BookCoupon;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Getter@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class BookRatioCoupon {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@MapsId
	@OneToOne
	BookCoupon bookCoupon;

	@Setter
	private double discountRate;

	@Setter
	private Long maxDiscount;

	public BookRatioCoupon(BookCoupon bookCoupon, double discountRate, Long maxDiscount) {
		this.bookCoupon = bookCoupon;
		this.discountRate = discountRate;
		this.maxDiscount = maxDiscount;
	}
}
