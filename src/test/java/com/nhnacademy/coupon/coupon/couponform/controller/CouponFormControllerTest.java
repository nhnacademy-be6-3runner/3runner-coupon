package com.nhnacademy.coupon.coupon.couponform.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.*;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;

import com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper;
import com.nhnacademy.coupon.coupon.BaseDocumentTest;
import com.nhnacademy.coupon.coupon.couponform.dto.ReadCouponFormResponse;
import com.nhnacademy.coupon.coupon.couponform.dto.request.CreateCouponFormRequest;
import com.nhnacademy.coupon.coupon.couponform.service.CouponFormService;
import com.nhnacademy.coupon.entity.coupontype.CouponType;
import com.nhnacademy.coupon.global.util.ApiResponse;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.restdocs.payload.JsonFieldType;

import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

class CouponFormControllerTest extends BaseDocumentTest {
	@MockBean
	private RabbitTemplate rabbitTemplate;

	@MockBean
	private CouponFormService couponFormService;

	@DisplayName("쿠폰 만들기")
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

		given(couponFormService.create(any(CreateCouponFormRequest.class))).willReturn(1L);

		this.mockMvc.perform(RestDocumentationRequestBuilders.post("/coupon/forms")
				.contentType(MediaType.APPLICATION_JSON)
				.content(requestBody)
				.accept(MediaType.APPLICATION_JSON)
			)
			.andExpect(status().isOk())
			.andDo(MockMvcRestDocumentationWrapper.document(snippetPath,
				"쿠폰 폼을 생성하는 API",
				requestFields(
					fieldWithPath("startDate").description("쿠폰 시작일"),
					fieldWithPath("endDate").description("쿠폰 종료일"),
					fieldWithPath("name").description("쿠폰 이름"),
					fieldWithPath("maxPrice").description("최대 금액"),
					fieldWithPath("minPrice").description("최소 금액"),
					fieldWithPath("couponTypeId").description("쿠폰 타입 번호"),
					fieldWithPath("couponUsageId").description("쿠폰 사용 번호")
				),
				responseFields(
					fieldWithPath("header.resultCode").type(JsonFieldType.NUMBER).description("결과 코드"),
					fieldWithPath("header.successful").type(JsonFieldType.BOOLEAN).description("성공 여부"),
					fieldWithPath("body.data").type(JsonFieldType.NUMBER).description("쿠폰 아이디")
				)
			));
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
					fieldWithPath("body.data[].categorys").description(
						"List of category IDs associated with the coupon form"),
					fieldWithPath("body.data[].discountPrice").description(
						"The discount price of the coupon form (if applicable)"),
					fieldWithPath("body.data[].discountRate").description(
						"The discount rate of the coupon form (if applicable)"),
					fieldWithPath("body.data[].discountMax").description(
						"The maximum discount of the coupon form (if applicable)")
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
					fieldWithPath("body.data[].categorys").description(
						"List of category IDs associated with the coupon form"),
					fieldWithPath("body.data[].discountPrice").description(
						"The discount price of the coupon form (if applicable)"),
					fieldWithPath("body.data[].discountRate").description(
						"The discount rate of the coupon form (if applicable)"),
					fieldWithPath("body.data[].discountMax").description(
						"The maximum discount of the coupon form (if applicable)")
				)));
	}
}
