package com.practice.e_commerce.service;

import com.practice.e_commerce.request.CreateProductRequest;
import com.practice.e_commerce.request.UpdateProductRequest;
import com.practice.e_commerce.response.ProductResponse;
import java.util.List;

public interface ProductService {

  ProductResponse create(CreateProductRequest request);

  List<ProductResponse> getAll();

  ProductResponse getById(Long id);

  ProductResponse update(Long id, UpdateProductRequest request);

  void delete(Long id);
}
