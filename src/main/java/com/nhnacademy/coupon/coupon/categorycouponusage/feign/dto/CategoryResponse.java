package com.nhnacademy.coupon.coupon.categorycouponusage.feign.dto;

import lombok.*;

/**
 * 카테고리 + 부모 카테고리 조회
 */

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class CategoryResponse {
	private long id;
	private String name;
	private CategoryResponse parent;

	public CategoryResponse(long id, String name) {
		this.id = id;
		this.name = name;
	}
}