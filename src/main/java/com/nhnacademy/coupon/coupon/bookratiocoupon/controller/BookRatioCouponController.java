package com.nhnacademy.coupon.coupon.bookratiocoupon.controller;


import com.nhnacademy.coupon.coupon.bookfixedcoupon.dto.request.ApiBookFixedCouponRequest;
import com.nhnacademy.coupon.coupon.bookfixedcoupon.dto.response.ApiBookFixedCouponResponse;
import com.nhnacademy.coupon.coupon.bookfixedcoupon.service.BookFixedCouponService;
import com.nhnacademy.coupon.coupon.bookratiocoupon.dto.request.ApiBookRatioCouponRequest;
import com.nhnacademy.coupon.coupon.bookratiocoupon.dto.response.ApiBookRatioCouponResponse;
import com.nhnacademy.coupon.coupon.bookratiocoupon.service.BookRatioCouponService;
import com.nhnacademy.coupon.global.util.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 *  북 비율 쿠폰 컨트롤러.
 *
 * @author 김병우
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/coupon/bookRatios")
public class BookRatioCouponController {
    private final BookRatioCouponService bookRatioCouponService;

    /**
     * 북 비율 쿠폰 읽기.
     *
     * @param couponFormId 쿠폰 폼 아이디
     * @return Dto
     */
    @GetMapping("/{couponFormId}")
    public ApiResponse<ApiBookRatioCouponResponse> readBookRatioCoupon(
            @PathVariable("couponFormId") Long couponFormId
    ) {
        return ApiResponse.success(bookRatioCouponService.readAllData(couponFormId));
    }

    /**
     * 북 비율 쿠폰 생성.
     *
     * @param apiBookRatioCouponRequest Dto
     * @return 북 비율 쿠폰 아이디
     */
    @PostMapping()
    public ApiResponse<Long> createBookRatioCoupon(@RequestBody ApiBookRatioCouponRequest apiBookRatioCouponRequest){
        Long bookFixedCouponId = bookRatioCouponService.create(apiBookRatioCouponRequest);
        return ApiResponse.createSuccess(bookFixedCouponId);
    }
}
