package com.nhnacademy.coupon.coupon.bookcouponusage.feign;

import com.nhnacademy.coupon.coupon.bookcouponusage.feign.dto.BookForCouponResponse;
import com.nhnacademy.coupon.coupon.bookcouponusage.feign.dto.UserReadBookResponse;
import com.nhnacademy.coupon.global.util.ApiResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "BookControllerClient", url = "http://${feign.bookstore.url}")
public interface BookControllerClient {
    @GetMapping("/bookstore/books/{bookId}")
    ApiResponse<UserReadBookResponse> readBook(@PathVariable("bookId") Long bookId);
    @GetMapping("bookstore/books/list")
    ApiResponse<List<BookForCouponResponse>> readAllBooksForCoupon(@RequestParam List<Long> ids);
}
