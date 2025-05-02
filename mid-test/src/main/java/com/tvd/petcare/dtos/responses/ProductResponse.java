package com.tvd.petcare.dtos.responses;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductResponse {
    LocalDateTime createdAt;
    LocalDateTime updatedAt;
    Long id;
    String name;
    double price;
    String description;
    String imagePath;
}
