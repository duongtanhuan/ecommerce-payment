package com.practice.e_commerce.common;

import java.util.Map;
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
  private Map<String, String> errors;
}