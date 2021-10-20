package com.example.webshop.api.model;

import com.example.webshop.domain.Customer;
import com.example.webshop.domain.OrderItem;
import com.example.webshop.domain.OrderStatus;


import java.math.BigDecimal;
import java.util.Set;

public class OrderDTO {

    private Long Id;
    private OrderStatus status;
    private BigDecimal totalPriceHrk;
    private BigDecimal totalPriceEur;

    private Long customerId;

    private Set<OrderItemDTO> orderItems;

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public BigDecimal getTotalPriceHrk() {
        return totalPriceHrk;
    }

    public void setTotalPriceHrk(BigDecimal totalPriceHrk) {
        this.totalPriceHrk = totalPriceHrk;
    }

    public BigDecimal getTotalPriceEur() {
        return totalPriceEur;
    }

    public void setTotalPriceEur(BigDecimal totalPriceEur) {
        this.totalPriceEur = totalPriceEur;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public Set<OrderItemDTO> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(Set<OrderItemDTO> orderItems) {
        this.orderItems = orderItems;
    }

}
