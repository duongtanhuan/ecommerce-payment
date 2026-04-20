package com.practice.e_commerce.exception;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BaseResponse<T> {

  private int status;
  private String message;
  private T data;
  private String errorCode;
}
