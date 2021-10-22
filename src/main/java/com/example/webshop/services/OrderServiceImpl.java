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
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;
    private final ProductService productService;
    private final OrderItemMapper orderItemMapper;

    private final String URI_HNB = "https://api.hnb.hr/tecajn/v1?valuta=EUR";

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
    public OrderDTO updateOrder(OrderItemDTO orderItemDTO) {
        // Checking if product is available
        if(!productService.getProductById(orderItemDTO.getProductId()).isAvailable()){throw new ProductNotAvailableException();}

        //fetching order
        Order savedOrder = orderMapper.toEntity(readOrder(orderItemDTO.getId()));
        Set<OrderItem> orderItems =savedOrder.getOrderItems();
        //checking if product exist in orderItemList
        //false: add it to set
        //true: update quantity
        OrderItem orderItem = orderItems.stream().filter(oi -> oi.getProduct().getId().equals(orderItemDTO.getProductId()))
                .findFirst().orElse(orderItemMapper.toEntity(orderItemDTO));
        orderItem.setQuantity(orderItemDTO.getQuantity());

        savedOrder.addOrderItem(orderItem);

        return saveAndReturnDTO(savedOrder);
    }

    @Override
    public void deleteOrderById(Long id) {
        orderRepository.deleteById(id);
    }

    @Override
    public OrderDTO finalizeOrder(OrderDTO orderDTO) {
        Order order = orderMapper.toEntity(orderDTO);
        order.setTotalPriceHrk(totalPriceHrk(order));

        return null;
    }

    private BigDecimal totalPriceHrk(Order order){
        Set<OrderItem> orderItems = new HashSet<>(order.getOrderItems());
        BigDecimal sum = BigDecimal.valueOf(0.00);
        for(OrderItem orderItem : orderItems) {
            //Price * ((BigDecimal) quantity)
            sum.add(orderItem.getProduct().getPriceHrk().multiply(BigDecimal.valueOf(orderItem.getQuantity())));
        }
        return sum;
    }

    private BigDecimal totalPriceEur(BigDecimal totalPriceHrk){
        RestTemplate restTemplate = new RestTemplate();

        return null;
    }
}



