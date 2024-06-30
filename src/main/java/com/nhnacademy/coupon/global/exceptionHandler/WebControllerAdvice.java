package com.nhnacademy.coupon.global.exceptionHandler;

import java.nio.channels.AlreadyBoundException;
import java.time.ZonedDateTime;

import com.nhnacademy.coupon.coupon.couponform.exception.CouponFormNotExistException;
import com.nhnacademy.coupon.global.util.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Error Handler Controller
 * @author 김병우
 */
@RestControllerAdvice
public class WebControllerAdvice {

    // 응답, 성공,실패 폼 맞추기
    /**
     * INTERNAL_SERVER_ERROR 처리 메소드
     * @param ex
     * @param model
     * @return ApiResponse<ErrorResponseForm>
     */
    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ApiResponse<ErrorResponseForm> runtimeExceptionHandler(Exception ex, Model model) {

		return ApiResponse.fail(500,
			new ApiResponse.Body<>(
				ErrorResponseForm.builder()
					.title(ex.getMessage())
					.status(HttpStatus.INTERNAL_SERVER_ERROR.value())
					.timestamp(ZonedDateTime.now().toString())
					.build())
		);
	}

    /**
     * BAD_REQUEST 처리 메소드
     * @param ex
     * @param model
     * @return ApiResponse<ErrorResponseForm>
     */
    @ExceptionHandler({
			CouponFormNotExistException.class,
            AlreadyBoundException.class
    })
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiResponse<ErrorResponseForm> badRequestHandler(Exception ex, Model model) {
        return ApiResponse.badRequestFail(
                new ApiResponse.Body<>(ErrorResponseForm.builder()
                        .title(ex.getMessage())
                        .status(HttpStatus.BAD_REQUEST.value())
                        .timestamp(ZonedDateTime.now().toString())
                        .build())
        );

    }
//
//	/**
//	 * NOT_FOUND 처리 메소드
//	 * @param ex
//	 * @param model
//	 * @return ApiResponse<ErrorResponseForm>
//	 *
//	 * @author 정주혁
//	 */
//	@ExceptionHandler({
//	})
//	@ResponseStatus(HttpStatus.NOT_FOUND)
//	public ApiResponse<ErrorResponseForm> notFoundHandler(Exception ex, Model model) {
//		return ApiResponse.notFoundFail(
//			new ApiResponse.Body<>(ErrorResponseForm.builder()
//				.title(ex.getMessage())
//				.status(HttpStatus.NOT_FOUND.value())
//				.timestamp(ZonedDateTime.now().toString())
//				.build())
//		);
//	}
//
//	/**
//	 * METHOD_NOT_ALLOWED 처리 메소드
//	 * @param ex
//	 * @param model
//	 * @return ApiResponse<ErrorResponseForm>
//	 *
//	 * @author 정주혁
//	 */
//
//	@ExceptionHandler({
//	})
//	@ResponseStatus(HttpStatus.UNAUTHORIZED)
//	public ApiResponse<ErrorResponseForm> unauthorizedHandler(Exception ex, Model model) {
//		return new ApiResponse<>(
//			new ApiResponse.Header(false, HttpStatus.METHOD_NOT_ALLOWED.value()),
//			new ApiResponse.Body<>(ErrorResponseForm.builder()
//				.title(ex.getMessage())
//				.status(HttpStatus.UNAUTHORIZED.value())
//				.timestamp(ZonedDateTime.now().toString())
//				.build())
//		);
//	}
//    @ExceptionHandler(MethodArgumentNotValidException.class)
//    @ResponseStatus(HttpStatus.BAD_REQUEST)
//    public ApiResponse<ErrorResponseForm> handleValidationExceptions(MethodArgumentNotValidException ex, Model model) {
//        Map<String, String> errors = new HashMap<>();
//        ex.getBindingResult().getAllErrors().forEach((error) -> {
//            String fieldName = ((FieldError) error).getField();
//            String errorMessage = error.getDefaultMessage();
//            errors.put(fieldName, errorMessage);
//        });
//        return new ApiResponse<>(
//                new ApiResponse.Header(false,400),
//                new ApiResponse.Body<>(ErrorResponseForm.builder()
//                        .title(errors.toString())
//                        .status(HttpStatus.BAD_REQUEST.value())
//                        .timestamp(ZonedDateTime.now().toString()).build())
//        );
//    }

//    @ExceptionHandler({})
//    @ResponseStatus(HttpStatus.NOT_FOUND)
//    public ApiResponse<ErrorResponseForm> notFoundHandler(Exception ex, Model model) {
//        return new ApiResponse<>(
//                new ApiResponse.Header(false, 404, "not found"),
//                new ApiResponse.Body<>(ErrorResponseForm.builder()
//                        .title(ex.getMessage())
//                        .status(HttpStatus.NOT_FOUND.value())
//                        .timestamp(ZonedDateTime.now().toString())
//                        .build())
//        );
//    }
//
//
//    @ExceptionHandler({})
//    @ResponseStatus(HttpStatus.UNAUTHORIZED)
//    public ApiResponse<ErrorResponseForm> unauthorizedHandler(Exception ex, Model model) {
//        return new ApiResponse<>(
//                new ApiResponse.Header(false, 405, "unauthorized"),
//                new ApiResponse.Body<>(ErrorResponseForm.builder()
//                        .title(ex.getMessage())
//                        .status(HttpStatus.UNAUTHORIZED.value())
//                        .timestamp(ZonedDateTime.now().toString())
//                        .build())
//        );
//    }
}
