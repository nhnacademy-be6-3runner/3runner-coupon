package com.nhnacademy.coupon.coupon.categorycouponusage.feign;

import com.nhnacademy.coupon.coupon.categorycouponusage.feign.dto.CategoryResponse;
import com.nhnacademy.coupon.global.util.ApiResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "CategoryControllerClient", url = "http://${feign.bookstore.url}")
public interface CategoryControllerClient {
    @GetMapping("/bookstore/categories/{categoryId}")
    ApiResponse<CategoryResponse> readCategory(@PathVariable Long categoryId);
}
