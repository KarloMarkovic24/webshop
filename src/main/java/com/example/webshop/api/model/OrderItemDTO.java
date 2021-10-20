package com.example.webshop.api.model;

import com.example.webshop.domain.Order;
import com.example.webshop.domain.Product;

import javax.persistence.*;

public class OrderItemDTO {

    private Long Id;
    private Integer quantity;
    private Long productId;
    private Long orderId;

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }
}
