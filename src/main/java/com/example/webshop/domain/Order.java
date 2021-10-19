package com.example.webshop.domain;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name="webshop_order")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    private Double totalPriceHrk;
    private Double totalPriceEur;

    @ManyToOne
    private Customer customer;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "order")
    private Set<OrderItem> orderItems;

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

    public Double getTotalPriceHrk() {
        return totalPriceHrk;
    }

    public void setTotalPriceHrk(Double totalPriceHrk) {
        this.totalPriceHrk = totalPriceHrk;
    }

    public Double getTotalPriceEur() {
        return totalPriceEur;
    }

    public void setTotalPriceEur(Double totalPriceEur) {
        this.totalPriceEur = totalPriceEur;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Set<OrderItem> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(Set<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }
}
