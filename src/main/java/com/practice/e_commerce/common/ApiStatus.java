package com.practice.e_commerce.common;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ApiStatus {

  SUCCESS(HttpStatus.OK),
  CREATED(HttpStatus.CREATED),
  BAD_REQUEST(HttpStatus.BAD_REQUEST),
  NOT_FOUND(HttpStatus.NOT_FOUND);

  private final HttpStatus status;

  ApiStatus(HttpStatus status) {
    this.status = status;
  }
}
