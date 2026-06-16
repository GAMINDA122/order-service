package com.ecommerce.order_service.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductResponse {

    private Long productId;

    private String name;

    private Double unitPrice;

    private String description;

    private Integer stock;
}
