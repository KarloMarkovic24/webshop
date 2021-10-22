package com.example.webshop.services;

import com.example.webshop.api.mapper.OrderItemMapper;
import com.example.webshop.api.mapper.OrderMapper;
import com.example.webshop.api.model.OrderDTO;
import com.example.webshop.api.model.OrderItemDTO;
import com.example.webshop.domain.Order;
import com.example.webshop.domain.OrderItem;
import com.example.webshop.exceptions.ProductNotAvailableException;
import com.example.webshop.exceptions.ResourceNotFoundException;
import com.example.webshop.repositories.OrderRepository;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;
    private final ProductService productService;
    private final OrderItemMapper orderItemMapper;

    public OrderServiceImpl(OrderRepository orderRepository, OrderMapper orderMapper, ProductService productService, OrderItemMapper orderItemMapper) {
        this.orderRepository = orderRepository;
        this.orderMapper = orderMapper;
        this.productService = productService;
        this.orderItemMapper = orderItemMapper;
    }

    @Override
    public OrderDTO readOrder(Long id) {
        return orderRepository.findById(id)
                .map(orderMapper::toDto)
                .orElseThrow(ResourceNotFoundException::new);
    }

    @Override
    public OrderDTO createNewOrder(OrderDTO orderDTO) {
        return saveAndReturnDTO(orderMapper.toEntity(orderDTO));
    }

    private OrderDTO saveAndReturnDTO(Order order){
        return orderMapper.toDto(orderRepository.save(order));
    }

    @Override
    public OrderDTO updateOrder(Long orderId, OrderItemDTO orderItemDTO) {
        // Checking if product is available
        if(!productService.getProductById(orderItemDTO.getProductId()).isAvailable()){throw new ProductNotAvailableException();}

        //fetching order
        Order savedOrder = orderMapper.toEntity(readOrder(orderId));

        //fetching orderItems and adding orderItem
        Set<OrderItem> orderItems =  new HashSet<>(savedOrder.getOrderItems());
        orderItems.add(orderItemMapper.toEntity(orderItemDTO));

        //saving into order
        savedOrder.setOrderItems(orderItems);

        return saveAndReturnDTO(savedOrder);
    }

    @Override
    public void deleteOrderById(Long id) {
        orderRepository.deleteById(id);
    }
}
