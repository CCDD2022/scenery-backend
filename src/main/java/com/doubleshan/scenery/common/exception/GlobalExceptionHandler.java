package com.doubleshan.scenery.common.exception;

import com.doubleshan.scenery.common.api.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BizException.class)
    public ApiResponse<Void> handleBiz(BizException e) {
        return ApiResponse.fail(e.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ApiResponse<Void> handleValid(MethodArgumentNotValidException e) {
        String msg = e.getBindingResult().getFieldErrors().stream()
                .map(f -> f.getField() + ":" + f.getDefaultMessage())
                .findFirst().orElse("参数错误");
        return ApiResponse.fail(msg);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ApiResponse<Void> handleDenied(AccessDeniedException e) {
        return new ApiResponse<>(false, HttpStatus.FORBIDDEN.getReasonPhrase(), null);
    }

    @ExceptionHandler(Exception.class)
    public ApiResponse<Void> handleOther(Exception e) {
        e.printStackTrace();
        return ApiResponse.fail("服务器内部错误");
    }
}
