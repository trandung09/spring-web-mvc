package com.tvd.petcare.services.products;

import com.tvd.petcare.dtos.requests.PurchaseProductRequest;
import com.tvd.petcare.mapper.ProductMapper;
import com.tvd.petcare.dtos.requests.CreateProductRequest;
import com.tvd.petcare.dtos.requests.UpdateProductRequest;
import com.tvd.petcare.dtos.responses.ProductResponse;
import com.tvd.petcare.entities.Product;
import com.tvd.petcare.exceptions.AppException;
import com.tvd.petcare.repositories.ProductRepository;
import com.tvd.petcare.services.mails.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductService implements IProductService {

    final ProductMapper productMapper;
    final ProductRepository productRepository;
    final EmailService emailService;

    @Override
    public Page<ProductResponse> getAllProducts(Pageable pageable) throws Exception {
        return productRepository.findAll(pageable)
                .map(
                        productMapper::toProductResponse
                );
    }

    @Override
    public ProductResponse getProductById(Long id) throws Exception {
        if (id == null) {
            throw new AppException("Get product details: product id is null");
        }

        Product product = productRepository.findById(id)
                .orElseThrow(() -> new AppException("Get product details: product by id not found"));

        return productMapper.toProductResponse(product);
    }

    @Override
    public ProductResponse createProduct(CreateProductRequest request) throws Exception {
        if (request == null) {
            throw new AppException("Create product: create request is null");
        }

        Product product = productMapper.toProduct(request);
        product = productRepository.save(product);

        return productMapper.toProductResponse(product);
    }

    @Override
    public ProductResponse updateProductById(Long id, UpdateProductRequest request) throws Exception {
        if (id == null) {
            throw new AppException("Update product: product id is null");
        }

        if (request == null) {
            throw new AppException("Update product: update request is null");
        }

        Product product = productRepository.findById(id)
                .orElseThrow(() -> new AppException("Get product details: product by id not found"));

        product.setName(request.getName());
        product.setDescription(request.getDescription());
        product.setPrice(request.getPrice());
        product.setImagePath(request.getImagePath());

        product = productRepository.save(product);

        return productMapper.toProductResponse(product);
    }

    @Override
    public void purchaseProduct(Long productId, String customerEmail, String customerName, String customerPhoneNumber, int quantity) throws  Exception {
        if (customerEmail == null || customerName == null || customerPhoneNumber == null) {
            throw new AppException("Purchase product: customer email and customer name is null");
        }

        if (productId == null) {
            throw new AppException("Purchase product: product id is null");
        }

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new AppException("Get product details: product by id not found"));

        PurchaseProductRequest request = PurchaseProductRequest.builder()
                .productName(product.getName())
                .quantity(quantity)
                .price(product.getPrice())
                .build();

        emailService.sendConfirmPurchaseProductEmail(customerEmail, customerName, customerPhoneNumber, request);


    }


}
