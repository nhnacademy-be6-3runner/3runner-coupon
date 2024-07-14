package com.nhnacademy.coupon.coupon.couponform.controller;

import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;

import com.nhnacademy.coupon.coupon.bookcouponusage.service.BookCouponUsageService;
import com.nhnacademy.coupon.coupon.categorycouponusage.service.CategoryCouponUsageService;
import com.nhnacademy.coupon.coupon.coupontype.dto.response.ReadCouponTypeResponse;
import com.nhnacademy.coupon.coupon.coupontype.service.CouponTypeService;
import com.nhnacademy.coupon.coupon.couponusage.dto.response.ReadCouponUsageResponse;
import com.nhnacademy.coupon.coupon.couponusage.service.CouponUsageService;
import com.nhnacademy.coupon.coupon.fixedcoupon.dto.response.ReadFixedCouponResponse;
import com.nhnacademy.coupon.coupon.fixedcoupon.service.FixedCouponService;
import com.nhnacademy.coupon.coupon.ratiocoupon.dto.response.ReadRatioCouponResponse;
import com.nhnacademy.coupon.coupon.ratiocoupon.service.RatioCouponService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;

import java.util.Arrays;
import java.util.List;

class CouponPolicyControllerTest extends CouponPolicyBaseDocumentTest {
    @MockBean
    private BookCouponUsageService bookCouponUsageService;

    @MockBean
    private CategoryCouponUsageService categoryCouponUsageService;

    @MockBean
    private FixedCouponService fixedCouponService;

    @MockBean
    private RatioCouponService ratioCouponService;

    @MockBean
    private CouponTypeService couponTypeService;

    @MockBean
    private CouponUsageService couponUsageService;

    @Test
    void testCreateFixedCouponPolicy() throws Exception {
        String requestBody = "{\"discountPrice\": 1000}";

        this.mockMvc.perform(RestDocumentationRequestBuilders.post("/coupon/types/fixes")
                        .contentType("application/json")
                        .content(requestBody))
                .andExpect(status().isOk())
                .andDo(document("create-fixed-coupon-policy",
                        preprocessRequest(prettyPrint()), preprocessResponse(prettyPrint()),
                        requestFields(
                                fieldWithPath("discountPrice").description("The discount price for the fixed coupon")
                        ),
                        responseFields(
                                fieldWithPath("header.resultCode").description("The result code of the response"),
                                fieldWithPath("header.successful").description("Whether the request was successful"),
                                fieldWithPath("body.data").description("ID of the created coupon")
                        )));
    }

    @Test
    void testCreateRatioCouponPolicy() throws Exception {
        String requestBody = "{\"discountRate\": 0.1, \"discountMaxPrice\": 1000}";

        this.mockMvc.perform(RestDocumentationRequestBuilders.post("/coupon/types/ratios")
                        .contentType("application/json")
                        .content(requestBody))
                .andExpect(status().isOk())
                .andDo(document("create-ratio-coupon-policy",
                        preprocessRequest(prettyPrint()), preprocessResponse(prettyPrint()),
                        requestFields(
                                fieldWithPath("discountRate").description("The discount rate for the ratio coupon"),
                                fieldWithPath("discountMaxPrice").description("The maximum discount price for the ratio coupon")
                        ),
                        responseFields(
                                fieldWithPath("header.resultCode").description("The result code of the response"),
                                fieldWithPath("header.successful").description("Whether the request was successful"),
                                fieldWithPath("body.data").description("ID of the created coupon")
                        )));
    }
    @Test
    void testCreateCategoryCouponPolicy() throws Exception {
        String requestBody = "{\"categoryIds\": [1, 2, 3]}";

        this.mockMvc.perform(RestDocumentationRequestBuilders.post("/coupon/usages/categories")
                        .contentType("application/json")
                        .content(requestBody))
                .andExpect(status().isOk())
                .andDo(document("create-category-coupon-policy",
                        preprocessRequest(prettyPrint()), preprocessResponse(prettyPrint()),
                        requestFields(
                                fieldWithPath("categoryIds").description("List of category IDs where the coupon can be used")
                        ),
                        responseFields(
                                fieldWithPath("header.resultCode").description("The result code of the response"),
                                fieldWithPath("header.successful").description("Whether the request was successful"),
                                fieldWithPath("body.data").description("ID of the created category coupon usage")
                        )));
    }

    @Test
    void testCreateBookCouponPolicy() throws Exception {
        String requestBody = "{\"bookIds\": [1, 2, 3]}";

        this.mockMvc.perform(RestDocumentationRequestBuilders.post("/coupon/usages/books")
                        .contentType("application/json")
                        .content(requestBody))
                .andExpect(status().isOk())
                .andDo(document("create-book-coupon-policy",
                        preprocessRequest(prettyPrint()), preprocessResponse(prettyPrint()),
                        requestFields(
                                fieldWithPath("bookIds").description("List of book IDs where the coupon can be used")
                        ),
                        responseFields(
                                fieldWithPath("header.resultCode").description("The result code of the response"),
                                fieldWithPath("header.successful").description("Whether the request was successful"),
                                fieldWithPath("body.data").description("ID of the created book coupon usage")
                        )));
    }

    @Test
    void testReadAllTypes() throws Exception {
        List<ReadCouponTypeResponse> responses = Arrays.asList(
                new ReadCouponTypeResponse(1L, "Fixed Coupon"),
                new ReadCouponTypeResponse(2L, "Ratio Coupon")
        );

        when(couponTypeService.readAll()).thenReturn(responses);

        this.mockMvc.perform(RestDocumentationRequestBuilders.get("/coupon/types"))
                .andExpect(status().isOk())
                .andDo(document("read-all-types",
                        preprocessRequest(prettyPrint()), preprocessResponse(prettyPrint()),
                        responseFields(
                                fieldWithPath("header.resultCode").description("The result code of the response"),
                                fieldWithPath("header.successful").description("Whether the request was successful"),
                                fieldWithPath("body.data[].couponTypeId").description("ID of the coupon type"),
                                fieldWithPath("body.data[].type").description("Name of the coupon type")
                        )));
    }

    @Test
    void testReadFixedType() throws Exception {
        ReadFixedCouponResponse response = new ReadFixedCouponResponse(1L, 1L, 1000);

        when(fixedCouponService.read(1L)).thenReturn(response);

        this.mockMvc.perform(RestDocumentationRequestBuilders.get("/coupon/types/fixes/{couponTypeId}", 1L))
                .andExpect(status().isOk())
                .andDo(document("read-fixed-type",
                        preprocessRequest(prettyPrint()), preprocessResponse(prettyPrint()),
                        pathParameters(
                                parameterWithName("couponTypeId").description("ID of the coupon type")
                        ),
                        responseFields(
                                fieldWithPath("header.resultCode").description("The result code of the response"),
                                fieldWithPath("header.successful").description("Whether the request was successful"),
                                fieldWithPath("body.data.fixedCouponId").description("ID of the fixed coupon"),
                                fieldWithPath("body.data.couponTypeId").description("ID of the coupon type"),
                                fieldWithPath("body.data.discountPrice").description("Discount price of the fixed coupon")
                        )));
    }

    @Test
    void testReadRatioType() throws Exception {
        ReadRatioCouponResponse response = new ReadRatioCouponResponse(1L, 1L, 0.1, 1000);

        when(ratioCouponService.read(1L)).thenReturn(response);

        this.mockMvc.perform(RestDocumentationRequestBuilders.get("/coupon/types/ratios/{couponTypeId}", 1L))
                .andExpect(status().isOk())
                .andDo(document("read-ratio-type",
                        preprocessRequest(prettyPrint()), preprocessResponse(prettyPrint()),
                        pathParameters(
                                parameterWithName("couponTypeId").description("ID of the coupon type")
                        ),
                        responseFields(
                                fieldWithPath("header.resultCode").description("The result code of the response"),
                                fieldWithPath("header.successful").description("Whether the request was successful"),
                                fieldWithPath("body.data.ratioCouponId").description("ID of the ratio coupon"),
                                fieldWithPath("body.data.couponTypeId").description("ID of the coupon type"),
                                fieldWithPath("body.data.discountRate").description("Discount rate of the ratio coupon"),
                                fieldWithPath("body.data.discountMaxPrice").description("Maximum discount price of the ratio coupon")
                        )));
    }

    @Test
    void testReadAllUsages() throws Exception {
        List<ReadCouponUsageResponse> responses = Arrays.asList(
                new ReadCouponUsageResponse(1L, "Usage 1"),
                new ReadCouponUsageResponse(2L, "Usage 2")
        );

        when(couponUsageService.readAll()).thenReturn(responses);

        this.mockMvc.perform(RestDocumentationRequestBuilders.get("/coupon/usages"))
                .andExpect(status().isOk())
                .andDo(document("read-all-usages",
                        preprocessRequest(prettyPrint()), preprocessResponse(prettyPrint()),
                        responseFields(
                                fieldWithPath("header.resultCode").description("The result code of the response"),
                                fieldWithPath("header.successful").description("Whether the request was successful"),
                                fieldWithPath("body.data[].couponUsageId").description("ID of the coupon usage"),
                                fieldWithPath("body.data[].usage").description("Description of the coupon usage")
                        )));
    }

    @Test
    void testReadCategoryUsages() throws Exception {
        List<Long> categoryIds = Arrays.asList(1L, 2L);

        when(categoryCouponUsageService.readCategorys(1L)).thenReturn(categoryIds);

        this.mockMvc.perform(RestDocumentationRequestBuilders.get("/coupon/usages/categories/{couponTypeId}", 1L))
                .andExpect(status().isOk())
                .andDo(document("read-category-usages",
                        preprocessRequest(prettyPrint()), preprocessResponse(prettyPrint()),
                        pathParameters(
                                parameterWithName("couponTypeId").description("ID of the coupon type")
                        ),
                        responseFields(
                                fieldWithPath("header.resultCode").description("The result code of the response"),
                                fieldWithPath("header.successful").description("Whether the request was successful"),
                                fieldWithPath("body.data[]").description("List of category IDs where the coupon can be used")
                        )));
    }

    @Test
    void testReadBookUsages() throws Exception {
        List<Long> bookIds = Arrays.asList(1L, 2L);

        when(bookCouponUsageService.readBooks(1L)).thenReturn(bookIds);

        this.mockMvc.perform(RestDocumentationRequestBuilders.get("/coupon/usages/books/{couponTypeId}", 1L))
                .andExpect(status().isOk())
                .andDo(document("read-book-usages",
                        preprocessRequest(prettyPrint()), preprocessResponse(prettyPrint()),
                        pathParameters(
                                parameterWithName("couponTypeId").description("ID of the coupon type")
                        ),
                        responseFields(
                                fieldWithPath("header.resultCode").description("The result code of the response"),
                                fieldWithPath("header.successful").description("Whether the request was successful"),
                                fieldWithPath("body.data[]").description("List of book IDs where the coupon can be used")
                        )));
    }
}
