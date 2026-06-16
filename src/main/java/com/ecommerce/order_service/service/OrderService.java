package com.ecommerce.order_service.service;

import com.ecommerce.order_service.client.ProductClient;
import com.ecommerce.order_service.dto.OrderEvent;
import com.ecommerce.order_service.dto.OrderRequest;
import com.ecommerce.order_service.dto.ProductResponse;
import com.ecommerce.order_service.entity.Order;
import com.ecommerce.order_service.repository.OrderRepository;
import org.springframework.stereotype.Service;

@Service
public class OrderService {

    private final OrderRepository repository;
    private final OrderPublisher publisher;
    private final ProductClient productClient;

    public OrderService(
            OrderRepository repository,
            OrderPublisher publisher,
            ProductClient productClient
    ) {
        this.repository = repository;
        this.publisher = publisher;
        this.productClient = productClient;
    }

    public Order createOrder(OrderRequest request) {

        Order order = new Order();

        order.setCustomerId(request.getCustomerId());
        order.setProductId(request.getProductId());

        ProductResponse product =
                productClient.getProduct(
                        request.getProductId()
                );

        order.setProductName(
                product.getName()
        );

        order.setQuantity(
                request.getQuantity()
        );

        order.setTotalPrice(
                product.getUnitPrice()
                        * request.getQuantity()
        );

        Order savedOrder = repository.save(order);

        OrderEvent event = new OrderEvent(
                savedOrder.getOrderId(),
                savedOrder.getCustomerId()
        );

        publisher.publish(event);

        return savedOrder;
    }
}