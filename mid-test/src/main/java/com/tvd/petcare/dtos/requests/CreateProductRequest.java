package com.tvd.petcare.dtos.requests;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateProductRequest {

    @NotNull(message = "Product name cannot be blank")
    @NotBlank
    String name;

    @Min(message = "Product price must be positive", value = 0)
    double price;

    String description;
}
