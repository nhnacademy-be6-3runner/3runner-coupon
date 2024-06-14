package com.nhnacademy.coupon.bookcoupon.repository;

import com.nhnacademy.coupon.coupon.bookcoupon.repository.BookCouponRepository;
import com.nhnacademy.coupon.entity.BookCoupon.BookCoupon;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestPropertySource (locations = "classpath:application.yml")

class BookCouponRepositoryTest {

    @Mock
    private BookCouponRepository bookCouponRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSaveBookCoupon() {
        // given
        BookCoupon bookCoupon = new BookCoupon();
        bookCoupon.setBookId(1L);

        when(bookCouponRepository.save(bookCoupon)).thenReturn(bookCoupon);


        // when
        BookCoupon savedBookCoupon = bookCouponRepository.save(bookCoupon);

        // then
        assertThat(savedBookCoupon).isNotNull();
        assertThat(savedBookCoupon.getBookId()).isEqualTo(1L);
    }

    @Test
    void testFindById() {
        // given
        BookCoupon bookCoupon = new BookCoupon(0L,1L,null);
        when(bookCouponRepository.save(bookCoupon)).thenReturn(bookCoupon);
        BookCoupon savedBookCoupon = bookCouponRepository.save(bookCoupon);
        when(bookCouponRepository.findById(0L)).thenReturn(Optional.of(bookCoupon));

        // when
        BookCoupon foundBookCoupon = bookCouponRepository.findById(savedBookCoupon.getId()).orElse(null);


        // then
        assertThat(foundBookCoupon).isNotNull();
        assertThat(foundBookCoupon.getId()).isEqualTo(savedBookCoupon.getId());
        assertThat(foundBookCoupon.getBookId()).isEqualTo(1L);
    }

    @Test
    void testDeleteBookCoupon() {
        // given
        BookCoupon bookCoupon = new BookCoupon();
        bookCoupon.setBookId(1L);

        when(bookCouponRepository.save(bookCoupon)).thenReturn(bookCoupon);

        BookCoupon savedBookCoupon = bookCouponRepository.save(bookCoupon);
        // when
        bookCouponRepository.deleteById(savedBookCoupon.getId());
        BookCoupon foundBookCoupon = bookCouponRepository.findById(savedBookCoupon.getId()).orElse(null);

        // then
        assertThat(foundBookCoupon).isNull();
    }

    @Test
    void testUpdateBookCoupon() {
        // given
        BookCoupon bookCoupon = new BookCoupon();
        bookCoupon.setBookId(1L);

        when(bookCouponRepository.save(bookCoupon)).thenReturn(bookCoupon);

        BookCoupon savedBookCoupon = bookCouponRepository.save(bookCoupon);

        // when
        savedBookCoupon.setBookId(2L);
        BookCoupon updatedBookCoupon = bookCouponRepository.save(savedBookCoupon);

        // then
        assertThat(updatedBookCoupon).isNotNull();
        assertThat(updatedBookCoupon.getId()).isEqualTo(savedBookCoupon.getId());
        assertThat(updatedBookCoupon.getBookId()).isEqualTo(2L);
    }
}
