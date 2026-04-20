package com.practice.e_commerce.controller;

import com.practice.e_commerce.common.ApiStatus;
import com.practice.e_commerce.common.BaseResponse;
import com.practice.e_commerce.request.CreateProductRequest;
import com.practice.e_commerce.request.UpdateProductRequest;
import com.practice.e_commerce.response.ProductResponse;
import com.practice.e_commerce.service.ProductService;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
public class ProductController {

  private final ProductService productService;

  @PostMapping
  public BaseResponse<ProductResponse> create(@Valid @RequestBody CreateProductRequest request) {

    ProductResponse response = productService.create(request);

    return BaseResponse.<ProductResponse>builder()
        .status(ApiStatus.SUCCESS.getStatus().value())
        .message("success")
        .data(response)
        .errorCode(null)
        .build();
  }

  @GetMapping
  public BaseResponse<List<ProductResponse>> getAll() {

    List<ProductResponse> response = productService.getAll();

    return BaseResponse.<List<ProductResponse>>builder()
        .status(ApiStatus.SUCCESS.getStatus().value())
        .message("success")
        .data(response)
        .errorCode(null)
        .build();
  }

  @GetMapping("/{id}")
  public BaseResponse<ProductResponse> getById(@PathVariable Long id) {

    ProductResponse response = productService.getById(id);

    return BaseResponse.<ProductResponse>builder()
        .status(ApiStatus.SUCCESS.getStatus().value())
        .message("success")
        .data(response)
        .errorCode(null)
        .build();
  }

  @PatchMapping("/{id}")
  public BaseResponse<ProductResponse> update(
      @PathVariable Long id,
      @RequestBody UpdateProductRequest request
  ) {

    ProductResponse response = productService.update(id, request);

    return BaseResponse.<ProductResponse>builder()
        .status(ApiStatus.SUCCESS.getStatus().value())
        .message("success")
        .data(response)
        .errorCode(null)
        .build();
  }

  @DeleteMapping("/{id}")
  public BaseResponse<Void> delete(@PathVariable Long id) {

    productService.delete(id);

    return BaseResponse.<Void>builder()
        .status(200)
        .message("success")
        .data(null)
        .errorCode(null)
        .build();
  }
}