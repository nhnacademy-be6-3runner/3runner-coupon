package com.nhnacademy.coupon.entity.bookfixedcoupon;

import com.nhnacademy.coupon.entity.bookcoupon.BookCoupon;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter@Setter
public class BookFixedCoupon {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@MapsId
	@OneToOne
	BookCoupon bookCoupon;

	@Setter
	private int price;

	public BookFixedCoupon(BookCoupon bookCoupon, int price) {
		this.bookCoupon = bookCoupon;
		this.price = price;
	}
}
