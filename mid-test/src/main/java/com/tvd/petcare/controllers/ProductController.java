package com.tvd.petcare.controllers;

import com.tvd.petcare.dtos.requests.CreateProductRequest;
import com.tvd.petcare.dtos.requests.PurchaseProductRequest;
import com.tvd.petcare.dtos.requests.UpdateProductRequest;
import com.tvd.petcare.dtos.responses.ApiResponse;
import com.tvd.petcare.dtos.responses.ProductResponse;
import com.tvd.petcare.services.mails.EmailService;
import com.tvd.petcare.services.products.IProductService;
import com.tvd.petcare.utils.BindingUtils;
import com.tvd.petcare.utils.FileUtils;
import com.tvd.petcare.utils.PageableUtils;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
@Tag(name = "Product-Controller")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProductController {


    final EmailService emailService;


    final Path imageLocation = Paths.get("uploads/images/");

    final IProductService productService;

    @GetMapping("")
    public ApiResponse<Page<ProductResponse>> getAllProducts(@RequestParam(defaultValue = "0") int page,
                                                             @RequestParam(defaultValue = "10") int size,
                                                             @RequestParam(defaultValue = "createdAt,desc") String sort)
            throws Exception {

        Pageable pageable = PageableUtils.convertPageable(page, size, sort);

        return ApiResponse.<Page<ProductResponse>>builder()
                .data(productService.getAllProducts(pageable))
                .message("Get all products successfully")
                .status(HttpStatus.OK)
                .build();
    }

    @GetMapping("/image")
    public ResponseEntity<Resource> getImage(@RequestParam("filename") String filename) throws Exception {
        try {
            Path imagePath = imageLocation.resolve(filename).normalize();
            Resource resource = new UrlResource(imagePath.toUri());

            if (resource.exists() && resource.isReadable()) {
                String contentType = determineContentType(filename);

                return ResponseEntity.ok()
                        .contentType(MediaType.parseMediaType(contentType))
                        .body(resource);
            } else {
                return ResponseEntity.notFound().build();
            }

        } catch (MalformedURLException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    private String determineContentType(String filename) {
        if (filename.endsWith(".png")) {
            return "image/png";
        } else if (filename.endsWith(".jpg") || filename.endsWith(".jpeg")) {
            return "image/jpeg";
        } else if (filename.endsWith(".gif")) {
            return "image/gif";
        }
        return "application/octet-stream";
    }

    @GetMapping("/{productId}")
    public ApiResponse<ProductResponse> getProductDetailsById(@PathVariable Long productId) throws Exception {
        return ApiResponse.<ProductResponse>builder()
                .data(productService.getProductById(productId))
                .message("Get product details by id successfully")
                .status(HttpStatus.OK)
                .build();
    }

    @PostMapping("")
    public ApiResponse<?> createProduct(@RequestParam("name") String productName,
                                        @RequestParam("price") double productPrice,
                                        @RequestParam("description") String productDescription,
                                        @RequestParam("image")MultipartFile file
                                        ) throws Exception {


        CreateProductRequest request = CreateProductRequest.builder()
                .name(productName)
                .price(productPrice)
                .description(productDescription)
                .build();

        String imagePath = FileUtils.onUpLoadFolder(file);

        request.setImagePath(imagePath);

        ProductResponse response = productService.createProduct(request);

        return ApiResponse.<ProductResponse>builder()
                .data(response)
                .message("Created new product successfully")
                .status(HttpStatus.CREATED)
                .build();
    }

    @PatchMapping("/{productId}")
    public ApiResponse<?> updateProductById(@PathVariable Long productId,
                                            @RequestParam(value = "name", required = true) String productName,
                                            @RequestParam(value = "price", defaultValue = "0") double productPrice,
                                            @RequestParam(value = "description", defaultValue = "") String productDescription,
                                            @RequestParam(value = "image", required = false) MultipartFile file) throws Exception {

        UpdateProductRequest request = UpdateProductRequest.builder()
                .name(productName)
                .price(productPrice)
                .description(productDescription)
                .build();

        System.out.println("Image file: " + (file != null ? file.getOriginalFilename() : "null"));


        if (file != null) {
            String newImagePath = FileUtils.onUpLoadFolder(file);
            request.setImagePath(newImagePath);
        }

        ProductResponse response = productService.updateProductById(productId, request);

        return ApiResponse.<ProductResponse>builder()
                .data(response)
                .message("Updated product successfully")
                .status(HttpStatus.OK)
                .build();
    }

    @PostMapping("/purchase/{productId}")
    public ApiResponse<?> purchaseProduct(@PathVariable Long productId,
                                          @RequestParam String customerEmail,
                                          @RequestParam String customerName,
                                          @RequestParam String customerPhoneNumber,
                                          @RequestParam(defaultValue = "1") int quantity) throws Exception {

        productService.purchaseProduct(productId, customerEmail, customerName, customerPhoneNumber, quantity);

        return ApiResponse.builder()
                .status(HttpStatus.OK)
                .message("Purchase product successfully")
                .data(null)
                .build();
    }

}
