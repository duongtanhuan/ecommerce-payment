package com.practice.e_commerce.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {


  INVALID_REQUEST(HttpStatus.BAD_REQUEST, "invalid.request"),
  INTERNAL_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "internal.error"),
//  PRODUCT
  PRODUCT_NOT_FOUND(HttpStatus.NOT_FOUND, "product.not.found"),
  PRODUCT_CODE_EXISTS(HttpStatus.BAD_REQUEST, "product.code.exists"),
  PRODUCT_PRICE_INVALID_RANGE(HttpStatus.BAD_REQUEST, "product.price.invalid.range"),

//  ORDER
  OUT_OF_STOCK(HttpStatus.BAD_REQUEST, "out.of.stock"),
  ORDER_NOT_FOUND(HttpStatus.NOT_FOUND, "order.not.found"),
  INVALID_STATUS_TRANSITION(HttpStatus.BAD_REQUEST, "invalid.status.transition"),
  CANNOT_CANCEL(HttpStatus.BAD_REQUEST, "cannot.cancel"),

  // PAYMENT
  INVALID_ORDER_STATE(HttpStatus.BAD_REQUEST, "invalid.order.state"),
  ALREADY_PAID(HttpStatus.BAD_REQUEST, "already.paid");

  private final HttpStatus status;
  private final String messageKey;

  ErrorCode(HttpStatus status, String messageKey) {
    this.status = status;
    this.messageKey = messageKey;
  }
}