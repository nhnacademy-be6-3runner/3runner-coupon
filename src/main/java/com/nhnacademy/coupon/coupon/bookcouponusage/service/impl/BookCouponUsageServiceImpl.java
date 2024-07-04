package com.nhnacademy.coupon.coupon.bookcouponusage.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nhnacademy.coupon.coupon.bookcoupon.repository.BookCouponRepository;
import com.nhnacademy.coupon.coupon.bookcouponusage.feign.BookControllerClient;
import com.nhnacademy.coupon.coupon.bookcouponusage.feign.dto.BookForCouponResponse;
import com.nhnacademy.coupon.coupon.bookcouponusage.feign.dto.UserReadBookResponse;
import com.nhnacademy.coupon.coupon.bookcouponusage.repository.BookCouponUsageRepository;
import com.nhnacademy.coupon.coupon.bookcouponusage.service.BookCouponUsageService;
import com.nhnacademy.coupon.coupon.couponusage.exception.CouponUsageDoesNotExistException;
import com.nhnacademy.coupon.coupon.couponusage.repository.CouponUsageRespository;
import com.nhnacademy.coupon.entity.bookcoupon.BookCoupon;
import com.nhnacademy.coupon.entity.bookcouponusage.BookCouponUsage;
import com.nhnacademy.coupon.entity.couponusage.CouponUsage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * 북 쿠폰 사용처 서비스 구현체.
 *
 * @author 김병우
 */
@Transactional
@RequiredArgsConstructor
@Service
public class BookCouponUsageServiceImpl implements BookCouponUsageService {
    private final ObjectMapper objectMapper;
    private final CouponUsageRespository couponUsageRespository;
    private final BookCouponUsageRepository bookCouponUsageRepository;
    private final BookCouponRepository bookCouponRepository;
    private final BookControllerClient bookControllerClient;

    /**
     * 북 쿠폰 사용처 생성.
     *
     * @param bookIds 북아이디 리스트
     * @return 쿠폰사용처아이디
     */
    @Override
    public Long create(List<Long> bookIds) {
        StringBuilder usage = new StringBuilder("사용가능 도서 : ");


        List<BookForCouponResponse> books = bookControllerClient.readAllBooksForCoupon(bookIds).getBody().getData();

        for(BookForCouponResponse book : books){
            usage.append(book.title()).append(",");
        }

        CouponUsage couponUsage = new CouponUsage(usage.toString());
        couponUsageRespository.save(couponUsage);

        for(Long l : bookIds){
            Optional<BookCoupon> bookCouponOptional = bookCouponRepository.findById(l);
            BookCoupon bookCoupon;

            if(bookCouponOptional.isEmpty()){
                bookCoupon = new BookCoupon(l);
                bookCouponRepository.save(bookCoupon);
            } else {
                bookCoupon = bookCouponOptional.get();
            }

            BookCouponUsage bookCouponUsage = new BookCouponUsage(couponUsage, bookCoupon);

            bookCouponUsageRepository.save(bookCouponUsage);
        }

        return couponUsage.getId();
    }

    /**
     * 북 쿠폰 사용가능한 북 리스트 반환 메소드.
     *
     * @param couponUsageId 쿠폰 사용처 아이디
     * @return 사용처 사용가능한 북 아이디 리스트
     */
    @Override
    public List<Long> readBooks(Long couponUsageId) {

        return bookCouponUsageRepository.findBookIdsByCouponUsageId(couponUsageId);
    }
}
