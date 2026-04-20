package com.practice.e_commerce.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {


  INVALID_REQUEST(HttpStatus.BAD_REQUEST, "invalid.request"),
  INTERNAL_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "internal.error"),
  PRODUCT_NOT_FOUND(HttpStatus.NOT_FOUND, "product.not.found"),
  PRODUCT_CODE_EXISTS(HttpStatus.BAD_REQUEST, "product.code.exists"),
  PRODUCT_PRICE_INVALID_RANGE(HttpStatus.BAD_REQUEST, "product.price.invalid.range");

  private final HttpStatus status;
  private final String messageKey;

  ErrorCode(HttpStatus status, String messageKey) {
    this.status = status;
    this.messageKey = messageKey;
  }
}