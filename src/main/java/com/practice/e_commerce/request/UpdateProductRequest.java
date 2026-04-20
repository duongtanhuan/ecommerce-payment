package com.practice.e_commerce.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateProductRequest {

  private String code;

  @Size(min = 3, max = 255, message = "product.name.invalid.size")
  private String name;

  @Positive(message = "product.price.invalid.positive")
  private Double price;

  @Min(value = 0, message = "product.stock.invalid.min")
  private Integer stock;

  @Size(max = 1000, message = "product.description.invalid.size")
  private String description;

}