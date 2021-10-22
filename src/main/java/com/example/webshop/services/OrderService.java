package com.example.webshop.services;


import com.example.webshop.api.model.OrderDTO;
import com.example.webshop.api.model.OrderItemDTO;
import com.example.webshop.api.model.ProductDTO;
import com.example.webshop.domain.Order;


public interface OrderService {

    OrderDTO createNewOrder(OrderDTO orderDTO);

    OrderDTO updateOrder(OrderItemDTO orderItemDTO);

    OrderDTO readOrder(Long id);

    void deleteOrderById(Long id);

    OrderDTO finalizeOrder(OrderDTO orderDTO);

}
