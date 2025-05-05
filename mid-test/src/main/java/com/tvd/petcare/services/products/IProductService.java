package com.tvd.petcare.services.products;

import com.tvd.petcare.dtos.requests.CreateProductRequest;
import com.tvd.petcare.dtos.requests.PurchaseProductRequest;
import com.tvd.petcare.dtos.requests.UpdateProductRequest;
import com.tvd.petcare.dtos.responses.ProductResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IProductService {

    Page<ProductResponse> getAllProducts(Pageable pageable) throws Exception;

    ProductResponse getProductById(Long id) throws Exception;

    ProductResponse createProduct(CreateProductRequest request) throws Exception;

    ProductResponse updateProductById(Long id, UpdateProductRequest request) throws Exception;

    void purchaseProduct(Long productId, String customerEmail,
                                    String customerName,
                                    String customerPhoneNumber,
                                    int quantity) throws Exception;
}
