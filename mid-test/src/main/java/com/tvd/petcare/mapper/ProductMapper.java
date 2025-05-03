package com.tvd.petcare.mapper;

import com.tvd.petcare.dtos.requests.CreateProductRequest;
import com.tvd.petcare.dtos.requests.UpdateProductRequest;
import com.tvd.petcare.dtos.responses.ProductResponse;
import com.tvd.petcare.entities.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    Product toProduct(CreateProductRequest request);

    Product toProduct(UpdateProductRequest request);

    ProductResponse toProductResponse(Product product);
}
