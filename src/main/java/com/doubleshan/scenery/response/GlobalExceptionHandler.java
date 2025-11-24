package com.doubleshan.scenery.response;

import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ApiResponse<Void> handleValid(MethodArgumentNotValidException e) {
        return ApiResponse.error(ErrorCodes.PARAM_ERROR, e.getBindingResult().getFieldError() == null ? "参数错误"
                : e.getBindingResult().getFieldError().getDefaultMessage());
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ApiResponse<Void> handleJson(HttpMessageNotReadableException e) {
        return ApiResponse.error(ErrorCodes.PARAM_ERROR, "请求体解析失败");
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ApiResponse<Void> handleIllegal(IllegalArgumentException e) {
        return ApiResponse.error(ErrorCodes.PARAM_ERROR, e.getMessage());
    }

    @ExceptionHandler(NotFoundException.class)
    public ApiResponse<Void> handleNotFound(NotFoundException e) {
        return ApiResponse.error(ErrorCodes.NOT_FOUND, e.getMessage());
    }

    @ExceptionHandler(ConflictException.class)
    public ApiResponse<Void> handleConflict(ConflictException e) {
        return ApiResponse.error(ErrorCodes.CONFLICT, e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ApiResponse<Void> handleOthers(Exception e) {
        e.printStackTrace();
        return ApiResponse.error(ErrorCodes.SERVER_ERROR, "服务器错误");
    }
}
// 移动到独立公共类中
