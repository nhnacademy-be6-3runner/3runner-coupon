package com.nhnacademy.coupon.bookcoupon.repository;

import com.nhnacademy.coupon.entity.bookcoupon.BookCoupon;
import com.nhnacademy.coupon.coupon.bookcoupon.repository.BookCouponRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class BookCouponRepositoryTest {

    @Mock
    private BookCouponRepository bookCouponRepository;

    @Test
    public void testFindById() {
        BookCoupon bookCoupon = new BookCoupon();

        Mockito.when(bookCouponRepository.save(Mockito.any(BookCoupon.class))).thenReturn(bookCoupon);

        Mockito.when(bookCouponRepository.findById(bookCoupon.getId())).thenReturn(Optional.of(bookCoupon));

        BookCoupon found = bookCouponRepository.findById(bookCoupon.getId()).orElse(null);

        assertThat(found).isNotNull();
        assertThat(found.getId()).isEqualTo(bookCoupon.getId());
    }
}
