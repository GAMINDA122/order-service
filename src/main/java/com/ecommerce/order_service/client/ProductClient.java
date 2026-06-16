package com.ecommerce.order_service.client;

import com.ecommerce.order_service.dto.ProductResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class ProductClient {

    private final RestTemplate restTemplate;

    public ProductClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public ProductResponse getProduct(Long productId) {

        String url =
                "http://localhost:8081/products/" + productId;

        return restTemplate.getForObject(
                url,
                ProductResponse.class
        );
    }
}