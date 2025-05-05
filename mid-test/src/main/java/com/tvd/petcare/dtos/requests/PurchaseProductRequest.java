package com.tvd.petcare.dtos.requests;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PurchaseProductRequest {

    String productName;
    double price = 0;
    int quantity = 1;
}
