package com.practice.e_commerce.mapper;

import com.practice.e_commerce.dto.ProductDto;
import com.practice.e_commerce.entity.Product;
import com.practice.e_commerce.request.CreateProductRequest;
import com.practice.e_commerce.request.UpdateProductRequest;
import com.practice.e_commerce.response.ProductResponse;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(
    componentModel = MappingConstants.ComponentModel.SPRING,
    unmappedTargetPolicy = ReportingPolicy.IGNORE,
    nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface ProductMapper {
  Product toEntity(CreateProductRequest request);

  ProductDto toDTO(Product product);

  ProductResponse toResponse(ProductDto dto);

  void updateProduct(@MappingTarget Product product, UpdateProductRequest request);
}
