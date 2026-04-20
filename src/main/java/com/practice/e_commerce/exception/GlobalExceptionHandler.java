package com.practice.e_commerce.exception;

import com.practice.e_commerce.common.BaseResponse;
import com.practice.e_commerce.common.MessageService;
import java.util.HashMap;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {

  private final MessageService messageService;

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<BaseResponse<?>> handleValidation(MethodArgumentNotValidException ex) {

    Map<String, String> errors = new HashMap<>();

    ex.getBindingResult().getFieldErrors().forEach(error -> {
      String field = error.getField();
      String message = messageService.getMessage(error.getDefaultMessage());
      errors.put(field, message);
    });

    BaseResponse<?> response = BaseResponse.builder()
        .status(400)
        .message(messageService.getMessage("invalid.request"))
        .errorCode("INVALID_REQUEST")
        .errors(errors)
        .data(null)
        .build();

    return ResponseEntity.badRequest().body(response);
  }

  @ExceptionHandler(BusinessException.class)
  public ResponseEntity<BaseResponse<?>> handleBusiness(BusinessException ex) {

    ErrorCode errorCode = ex.getErrorCode();

    BaseResponse<?> response = BaseResponse.builder()
        .status(errorCode.getStatus().value())
        .message(messageService.getMessage(errorCode.getMessageKey()))
        .errorCode(errorCode.name())
        .data(null)
        .build();

    return ResponseEntity
        .status(errorCode.getStatus())
        .body(response);
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<BaseResponse<?>> handleOther(Exception ex) {

    BaseResponse<?> response = BaseResponse.builder()
        .status(500)
        .message(messageService.getMessage("internal.error"))
        .errorCode("INTERNAL_ERROR")
        .data(null)
        .build();

    return ResponseEntity.status(500).body(response);
  }
}