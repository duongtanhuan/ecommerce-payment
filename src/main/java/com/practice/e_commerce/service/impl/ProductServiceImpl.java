package com.practice.e_commerce.service.impl;

import static com.practice.e_commerce.common.constant.ProductConstant.MAX_PRICE;
import static com.practice.e_commerce.common.constant.ProductConstant.MIN_PRICE;

import com.practice.e_commerce.dto.ProductDto;
import com.practice.e_commerce.entity.Product;
import com.practice.e_commerce.exception.BusinessException;
import com.practice.e_commerce.exception.ErrorCode;
import com.practice.e_commerce.mapper.ProductMapper;
import com.practice.e_commerce.repository.ProductRepository;
import com.practice.e_commerce.request.CreateProductRequest;
import com.practice.e_commerce.request.UpdateProductRequest;
import com.practice.e_commerce.response.ProductResponse;
import com.practice.e_commerce.service.ProductService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

  private final ProductRepository productRepository;
  private final ProductMapper productMapper;

  @Override
  public ProductResponse update(Long id, UpdateProductRequest request) {

    Product product = productRepository.findByIdAndDeletedFalse(id)
        .orElseThrow(() -> new BusinessException(ErrorCode.PRODUCT_NOT_FOUND));

    if (request.getPrice() != null && request.getPrice() <= 0) {
      throw new BusinessException(ErrorCode.INVALID_REQUEST);
    }

    if (request.getStock() != null && request.getStock() < 0) {
      throw new BusinessException(ErrorCode.INVALID_REQUEST);
    }

    productMapper.updateProduct(product, request);

    Product updated = productRepository.save(product);

    return productMapper.toResponse(productMapper.toDTO(updated));
  }
  @Override
  public ProductResponse create(CreateProductRequest request) {
    if (request.getPrice() < MIN_PRICE || request.getPrice() > MAX_PRICE) {
      throw new BusinessException(ErrorCode.PRODUCT_PRICE_INVALID_RANGE);
    }

    if (productRepository.existsByCode(request.getCode())) {
      throw new BusinessException(ErrorCode.PRODUCT_CODE_EXISTS);
    }

    Product product = productMapper.toEntity(request);
    product = productRepository.save(product);

    return productMapper.toResponse(productMapper.toDTO(product));
  }

  @Override
  public List<ProductResponse> getAll() {

    return productRepository.findAll()
        .stream()
        .map(productMapper::toDTO)
        .map(productMapper::toResponse)
        .toList();
  }

  @Override
  public ProductResponse getById(Long id) {

    Product product = productRepository.findById(id)
        .orElseThrow(() -> new BusinessException(ErrorCode.PRODUCT_NOT_FOUND));

    ProductDto dto = productMapper.toDTO(product);

    return productMapper.toResponse(dto);
  }

  @Override
  public void delete(Long id) {

    Product product = productRepository.findByIdAndDeletedFalse(id)
        .orElseThrow(() -> new BusinessException(ErrorCode.PRODUCT_NOT_FOUND));

    product.setDeleted(true);

    productRepository.save(product);
  }
}