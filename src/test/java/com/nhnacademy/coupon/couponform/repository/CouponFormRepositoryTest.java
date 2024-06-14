package com.nhnacademy.coupon.couponform.repository;

import com.nhnacademy.coupon.bookcoupon.repository.BookCouponRepository;
import com.nhnacademy.coupon.categorycoupon.repository.CategoryCouponRepository;
import com.nhnacademy.coupon.entity.BookCoupon.BookCoupon;
import com.nhnacademy.coupon.entity.CatoryCoupon.CategoryCoupon;
import com.nhnacademy.coupon.entity.FixedCoupon.FixedCoupon;
import com.nhnacademy.coupon.entity.RatioCoupon.RatioCoupon;
import com.nhnacademy.coupon.entity.couponForm.CouponForm;
import com.nhnacademy.coupon.fixedcoupon.repository.FixedCouponRepository;
import com.nhnacademy.coupon.ratiocoupon.repository.RatioCouponRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.hamcrest.Matchers.any;
import static org.mockito.Mockito.when;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestPropertySource(locations = "classpath:application.yml")
class CouponFormRepositoryTest {

    @Mock
    private CouponFormRepository couponFormRepository;
    @Mock
    private RatioCouponRepository ratioCouponRepository;
    @Mock
    private CategoryCouponRepository categoryCouponRepository;
    @Mock
    private FixedCouponRepository fixedCouponRepository;
    @Mock
    private BookCouponRepository bookCouponRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSaveCouponForm() {
        // given
        CouponForm couponForm = new CouponForm(ZonedDateTime.now()
                ,ZonedDateTime.now().plusDays(10),
                "Test Coupon",
                new RatioCoupon(),
                new FixedCoupon(),
                new CategoryCoupon(),
                new BookCoupon());

        when(couponFormRepository.save(couponForm)).thenReturn(couponForm);

        // when
        CouponForm savedCouponForm = couponFormRepository.save(couponForm);

        // then
        assertThat(savedCouponForm).isNotNull();
        assertThat(savedCouponForm.getName()).isEqualTo("Test Coupon");
    }

    @Test
    void testFindById() {
        // given
        CouponForm couponForm = new CouponForm(1L,null,null,ZonedDateTime.now(),"Test Coupon",null,null,null,null,null);



        when(couponFormRepository.findById(1L)).thenReturn(Optional.of(couponForm));

        // when
        CouponForm foundCouponForm = couponFormRepository.findById(1L).orElse(null);

        // then
        assertThat(foundCouponForm).isNotNull();
        assertThat(foundCouponForm.getId()).isEqualTo(1L);
        assertThat(foundCouponForm.getName()).isEqualTo("Test Coupon");
    }



    @Test
    void testDeleteCouponForm() {
        // given
        CouponForm couponForm = new CouponForm(ZonedDateTime.now()
                ,ZonedDateTime.now().plusDays(10),
                "Test Coupon",
                new RatioCoupon(),
                new FixedCoupon(),
                new CategoryCoupon(),
                new BookCoupon());
        when(couponFormRepository.save(couponForm)).thenReturn(couponForm);


        CouponForm savedCouponForm = couponFormRepository.save(couponForm);

        // when
        couponFormRepository.deleteById(savedCouponForm.getId());
        CouponForm foundCouponForm = couponFormRepository.findById(savedCouponForm.getId()).orElse(null);

        // then
        assertThat(foundCouponForm).isNull();
    }

    @Test
    void testUpdateCouponForm() {
        // given
        CouponForm couponForm = new CouponForm(ZonedDateTime.now()
                ,ZonedDateTime.now().plusDays(10),
                "Test Coupon",
                new RatioCoupon(),
                new FixedCoupon(),
                new CategoryCoupon(),
                new BookCoupon());
        when(couponFormRepository.save(couponForm)).thenReturn(couponForm);


        CouponForm savedCouponForm = couponFormRepository.save(couponForm);

        // when
        savedCouponForm.setName("Updated Coupon");
        CouponForm updatedCouponForm = couponFormRepository.save(savedCouponForm);

        // then
        assertThat(updatedCouponForm).isNotNull();
        assertThat(updatedCouponForm.getId()).isEqualTo(savedCouponForm.getId());
        assertThat(updatedCouponForm.getName()).isEqualTo("Updated Coupon");
    }



    @Test
    void testFindByDateRange() {
        // given
        CouponForm couponForm1 = new CouponForm();
        couponForm1.setStartDate(ZonedDateTime.now().minusDays(5));
        couponForm1.setEndDate(ZonedDateTime.now().plusDays(5));
        couponForm1.setName("Date Range Coupon 1");
        when(couponFormRepository.save(couponForm1)).thenReturn(couponForm1);

        // You need to set these entities properly
        RatioCoupon ratioCoupon1 = new RatioCoupon();
        CategoryCoupon categoryCoupon1 = new CategoryCoupon();
        FixedCoupon fixedCoupon1 = new FixedCoupon();
        BookCoupon bookCoupon1 = new BookCoupon();
        ratioCouponRepository.save(ratioCoupon1);
        categoryCouponRepository.save(categoryCoupon1);
        fixedCouponRepository.save(fixedCoupon1);
        bookCouponRepository.save(bookCoupon1);
        when(ratioCouponRepository.save(ratioCoupon1)).thenReturn(ratioCoupon1);


        couponForm1.setRatioCoupon(ratioCoupon1);
        couponForm1.setCategoryCoupon(categoryCoupon1);
        couponForm1.setFixedCoupon(fixedCoupon1);
        couponForm1.setBookCoupon(bookCoupon1);
        when(couponFormRepository.save(couponForm1)).thenReturn(couponForm1);

        CouponForm couponForm2 = new CouponForm();
        couponForm2.setStartDate(ZonedDateTime.now().minusDays(15));
        couponForm2.setEndDate(ZonedDateTime.now().minusDays(10));
        couponForm2.setName("Date Range Coupon 2");
        when(couponFormRepository.save(couponForm2)).thenReturn(couponForm2);

        // You need to set these entities properly
        RatioCoupon ratioCoupon2 = new RatioCoupon();
        CategoryCoupon categoryCoupon2 = new CategoryCoupon();
        FixedCoupon fixedCoupon2 = new FixedCoupon();
        BookCoupon bookCoupon2 = new BookCoupon();

        couponForm2.setRatioCoupon(ratioCoupon2);
        couponForm2.setCategoryCoupon(categoryCoupon2);
        couponForm2.setFixedCoupon(fixedCoupon2);
        couponForm2.setBookCoupon(bookCoupon2);

        couponFormRepository.save(couponForm1);
        couponFormRepository.save(couponForm2);

        // when
        List<CouponForm> foundCoupons = couponFormRepository.findAll();
        long count = foundCoupons.stream().filter(coupon -> coupon.getStartDate().isBefore(ZonedDateTime.now()) && coupon.getEndDate().isAfter(ZonedDateTime.now())).count();

        // then
        assertThat(count).isEqualTo(0);
    }
}
