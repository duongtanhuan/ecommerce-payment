package com.practice.e_commerce.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateProductRequest {

  @NotBlank(message = "product.code.invalid.blank")
  private String code;

  @NotBlank(message = "product.name.invalid.blank")
  @Size(min = 3, max = 255, message = "product.name.invalid.size")
  private String name;

  @NotNull(message = "product.price.invalid.null")
  @Positive(message = "product.price.invalid.positive")
  private Double price;

  @NotNull(message = "product.stock.invalid.null")
  @Min(value = 0, message = "product.stock.invalid.min")
  private Integer stock;

  @Size(max = 1000, message = "product.description.invalid.size")
  private String description;

}
