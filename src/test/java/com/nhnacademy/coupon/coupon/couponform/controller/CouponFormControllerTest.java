package com.nhnacademy.coupon.coupon.couponform.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;

import com.nhnacademy.coupon.coupon.couponform.dto.ReadCouponFormResponse;
import com.nhnacademy.coupon.coupon.couponform.service.CouponFormService;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

class CouponFormControllerTest extends CouponFormBaseDocumentTest {
    @MockBean
    private RabbitTemplate rabbitTemplate;

    @MockBean
    private CouponFormService couponFormService;

    @Test
    void testCreateCouponForm() throws Exception {
        String requestBody = "{"
                + "\"startDate\": \"2024-01-01T00:00:00Z\","
                + "\"endDate\": \"2024-12-31T23:59:59Z\","
                + "\"name\": \"Test Coupon\","
                + "\"maxPrice\": 1000,"
                + "\"minPrice\": 500,"
                + "\"couponTypeId\": 1,"
                + "\"couponUsageId\": 1"
                + "}";

        this.mockMvc.perform(RestDocumentationRequestBuilders.post("/coupon/forms")
                        .contentType("application/json")
                        .content(requestBody))
                .andExpect(status().isOk())
                .andDo(document("create-coupon-form",
                        preprocessRequest(prettyPrint()), preprocessResponse(prettyPrint()),
                        requestFields(
                                fieldWithPath("startDate").description("The start date of the coupon"),
                                fieldWithPath("endDate").description("The end date of the coupon"),
                                fieldWithPath("name").description("The name of the coupon"),
                                fieldWithPath("maxPrice").description("The maximum price of the coupon"),
                                fieldWithPath("minPrice").description("The minimum price of the coupon"),
                                fieldWithPath("couponTypeId").description("The ID of the coupon type"),
                                fieldWithPath("couponUsageId").description("The ID of the coupon usage")
                        ),
                        responseFields(
                                fieldWithPath("header.resultCode").description("The result code of the response"),
                                fieldWithPath("header.successful").description("Whether the request was successful"),
                                fieldWithPath("body.data").description("ID of the created coupon form")
                        )));
    }

    @Test
    void testReadAllCouponForms() throws Exception {
        List<ReadCouponFormResponse> responses = Arrays.asList(
                new ReadCouponFormResponse(
                        1L, ZonedDateTime.parse("2024-01-01T00:00:00Z"), ZonedDateTime.parse("2024-12-31T23:59:59Z"),
                        ZonedDateTime.now(), "Test Coupon 1", UUID.randomUUID(), 1000, 500,
                        1L, 1L, "Fixed", "Books", Arrays.asList(1L, 2L), Arrays.asList(1L, 2L),
                        1000, 0.0, 0),
                new ReadCouponFormResponse(
                        2L, ZonedDateTime.parse("2024-01-01T00:00:00Z"), ZonedDateTime.parse("2024-12-31T23:59:59Z"),
                        ZonedDateTime.now(), "Test Coupon 2", UUID.randomUUID(), 2000, 1000,
                        2L, 2L, "Ratio", "Categories", Arrays.asList(1L, 2L), Arrays.asList(1L, 2L),
                        0, 0.2, 500)
        );

        when(couponFormService.readAllForms()).thenReturn(responses);

        this.mockMvc.perform(RestDocumentationRequestBuilders.get("/coupon/forms"))
                .andExpect(status().isOk())
                .andDo(document("read-all-coupon-forms",
                        preprocessRequest(prettyPrint()), preprocessResponse(prettyPrint()),
                        responseFields(
                                fieldWithPath("header.resultCode").description("The result code of the response"),
                                fieldWithPath("header.successful").description("Whether the request was successful"),
                                fieldWithPath("body.data[].couponFormId").description("ID of the coupon form"),
                                fieldWithPath("body.data[].startDate").description("The start date of the coupon form"),
                                fieldWithPath("body.data[].endDate").description("The end date of the coupon form"),
                                fieldWithPath("body.data[].createdAt").description("The creation date of the coupon form"),
                                fieldWithPath("body.data[].name").description("The name of the coupon form"),
                                fieldWithPath("body.data[].code").description("The unique code of the coupon form"),
                                fieldWithPath("body.data[].maxPrice").description("The maximum price of the coupon form"),
                                fieldWithPath("body.data[].minPrice").description("The minimum price of the coupon form"),
                                fieldWithPath("body.data[].couponTypeId").description("The ID of the coupon type"),
                                fieldWithPath("body.data[].couponUsageId").description("The ID of the coupon usage"),
                                fieldWithPath("body.data[].type").description("The type of the coupon form"),
                                fieldWithPath("body.data[].usage").description("The usage description of the coupon form"),
                                fieldWithPath("body.data[].books").description("List of book IDs associated with the coupon form"),
                                fieldWithPath("body.data[].categorys").description("List of category IDs associated with the coupon form"),
                                fieldWithPath("body.data[].discountPrice").description("The discount price of the coupon form (if applicable)"),
                                fieldWithPath("body.data[].discountRate").description("The discount rate of the coupon form (if applicable)"),
                                fieldWithPath("body.data[].discountMax").description("The maximum discount of the coupon form (if applicable)")
                        )));
    }

    @Test
    void testReadCouponForm() throws Exception {
        String requestBody = "{\"couponFormIds\": [1, 2, 3]}";

        List<ReadCouponFormResponse> responses = Arrays.asList(
                new ReadCouponFormResponse(
                        1L, ZonedDateTime.parse("2024-01-01T00:00:00Z"), ZonedDateTime.parse("2024-12-31T23:59:59Z"),
                        ZonedDateTime.now(), "Test Coupon 1", UUID.randomUUID(), 1000, 500,
                        1L, 1L, "Fixed", "Books", Arrays.asList(1L, 2L), Arrays.asList(1L, 2L),
                        1000, 0.0, 0),
                new ReadCouponFormResponse(
                        2L, ZonedDateTime.parse("2024-01-01T00:00:00Z"), ZonedDateTime.parse("2024-12-31T23:59:59Z"),
                        ZonedDateTime.now(), "Test Coupon 2", UUID.randomUUID(), 2000, 1000,
                        2L, 2L, "Ratio", "Categories", Arrays.asList(1L, 2L), Arrays.asList(1L, 2L),
                        0, 0.2, 500)
        );

        when(couponFormService.readAll(any())).thenReturn(responses);

        this.mockMvc.perform(RestDocumentationRequestBuilders.post("/coupon/members/forms")
                        .contentType("application/json")
                        .content(requestBody))
                .andExpect(status().isOk())
                .andDo(document("read-coupon-form",
                        preprocessRequest(prettyPrint()), preprocessResponse(prettyPrint()),
                        requestFields(
                                fieldWithPath("couponFormIds").description("List of coupon form IDs to read")
                        ),
                        responseFields(
                                fieldWithPath("header.resultCode").description("The result code of the response"),
                                fieldWithPath("header.successful").description("Whether the request was successful"),
                                fieldWithPath("body.data[].couponFormId").description("ID of the coupon form"),
                                fieldWithPath("body.data[].startDate").description("The start date of the coupon form"),
                                fieldWithPath("body.data[].endDate").description("The end date of the coupon form"),
                                fieldWithPath("body.data[].createdAt").description("The creation date of the coupon form"),
                                fieldWithPath("body.data[].name").description("The name of the coupon form"),
                                fieldWithPath("body.data[].code").description("The unique code of the coupon form"),
                                fieldWithPath("body.data[].maxPrice").description("The maximum price of the coupon form"),
                                fieldWithPath("body.data[].minPrice").description("The minimum price of the coupon form"),
                                fieldWithPath("body.data[].couponTypeId").description("The ID of the coupon type"),
                                fieldWithPath("body.data[].couponUsageId").description("The ID of the coupon usage"),
                                fieldWithPath("body.data[].type").description("The type of the coupon form"),
                                fieldWithPath("body.data[].usage").description("The usage description of the coupon form"),
                                fieldWithPath("body.data[].books").description("List of book IDs associated with the coupon form"),
                                fieldWithPath("body.data[].categorys").description("List of category IDs associated with the coupon form"),
                                fieldWithPath("body.data[].discountPrice").description("The discount price of the coupon form (if applicable)"),
                                fieldWithPath("body.data[].discountRate").description("The discount rate of the coupon form (if applicable)"),
                                fieldWithPath("body.data[].discountMax").description("The maximum discount of the coupon form (if applicable)")
                        )));
    }
}
