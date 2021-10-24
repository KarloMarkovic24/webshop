package com.example.webshop.services;

import com.example.webshop.api.mapper.*;
import com.example.webshop.api.model.OrderDTO;
import com.example.webshop.api.model.OrderItemDTO;
import com.example.webshop.domain.Order;
import com.example.webshop.domain.OrderItem;
import com.example.webshop.domain.OrderStatus;
import com.example.webshop.domain.Product;
import com.example.webshop.repositories.OrderRepository;
import com.example.webshop.repositories.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;



@SpringBootTest(classes = {
        OrderMapperImpl.class,
        OrderItemMapperImpl.class,
        ProductMapperImpl.class,
        RestTemplate.class
})
class OrderServiceImplTest {

    @Autowired
    OrderItemMapper orderItemMapper;

    @Autowired
    OrderMapper orderMapper;

    @Autowired
    ProductMapper productMapper;

    @Autowired
    RestTemplate restTemplate;
    OrderServiceImpl orderService;
    ProductServiceImpl productService;


    @Mock
    ProductRepository productRepository;

    @Mock
    OrderRepository orderRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        productService = new ProductServiceImpl(productMapper,productRepository);

        orderService = new OrderServiceImpl(orderRepository,orderMapper,productService,orderItemMapper,restTemplate);

    }

    @Test
    void readOrder() {
        Long id = 1L;

        //given
        Order order = new Order();
        order.setId(id);
        order.setStatus(OrderStatus.DRAFT);
        order.setTotalPriceHrk(BigDecimal.valueOf(100));
        order.setTotalPriceEur(BigDecimal.valueOf(13.33));
        OrderItem orderItem = new OrderItem();
        orderItem.setId(id);
        orderItem.setQuantity(12);
        orderItem.setOrder(order);
        orderItem.setProduct(new Product());
        Set<OrderItem> orderItems = new HashSet<>();
        orderItems.add(orderItem);
        order.setOrderItems(orderItems);

        when(orderRepository.findById(id)).thenReturn(java.util.Optional.of(order));

        //when
        OrderDTO orderDTO = orderService.readOrder(id);

        //then
        assertEquals(id, orderDTO.getId());
    }

    @Test
    void createNewOrder() {
        Long id = 1L;

        //given
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setId(id);
        orderDTO.setStatus(OrderStatus.DRAFT);
        orderDTO.setTotalPriceHrk(BigDecimal.valueOf(100));
        orderDTO.setTotalPriceEur(BigDecimal.valueOf(13.33));
        OrderItemDTO orderItemDTO = new OrderItemDTO();
        orderItemDTO.setId(id);
        orderItemDTO.setQuantity(12);
        orderItemDTO.setOrderId(id);
        Set<OrderItemDTO> orderItemDTOS = new HashSet<>();
        orderItemDTOS.add(orderItemDTO);
        orderDTO.setOrderItems(orderItemDTOS);

        Order savedOrder = new Order();
        savedOrder.setId(orderDTO.getId());
        savedOrder.setStatus(orderDTO.getStatus());
        savedOrder.setTotalPriceHrk(orderDTO.getTotalPriceHrk());
        savedOrder.setTotalPriceEur(orderDTO.getTotalPriceEur());
        savedOrder.setOrderItems(orderItemMapper.toEntity(orderDTO.getOrderItems()));

        when(orderRepository.save(any(Order.class))).thenReturn(savedOrder);

        //when
        OrderDTO savedDTO = orderService.createNewOrder(orderDTO);

        //then
        assertEquals(orderDTO.getStatus(),savedDTO.getStatus());

    }

    @Test
    void updateOrder() {
        Long id = 1L;

        //given
        OrderItemDTO orderItemDTO = new OrderItemDTO();
        orderItemDTO.setId(id);
        orderItemDTO.setQuantity(12);
        orderItemDTO.setOrderId(id);
        orderItemDTO.setProductId(id);

        Set<OrderItemDTO> orderItemDTOS = new HashSet<>();
        orderItemDTOS.add(orderItemDTO);

        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setId(id);
        orderDTO.setStatus(OrderStatus.DRAFT);
        orderDTO.setTotalPriceHrk(BigDecimal.valueOf(100));
        orderDTO.setTotalPriceEur(BigDecimal.valueOf(13.33));
        orderDTO.setOrderItems(orderItemDTOS);

        Product product = new Product();
        product.setId(id);
        product.setAvailable(true);
        product.setCode("1234567890");
        product.setDescription("sdsadsad");
        product.setName("product");
        product.setPriceHrk(BigDecimal.valueOf(1000));

        Order savedOrder = new Order();
        savedOrder.setId(orderDTO.getId());
        savedOrder.setStatus(orderDTO.getStatus());
        savedOrder.setTotalPriceHrk(orderDTO.getTotalPriceHrk());
        savedOrder.setTotalPriceEur(orderDTO.getTotalPriceEur());

        OrderItem orderItem = new OrderItem();
        orderItem.setId(orderItemDTO.getId());
        orderItem.setQuantity(orderItemDTO.getQuantity());
        orderItem.setOrder(savedOrder);
        orderItem.setProduct(product);

        Set<OrderItem> orderItems = new HashSet<>();
        orderItems.add(orderItem);
        savedOrder.setOrderItems(orderItems);

        when(orderRepository.save(any(Order.class))).thenReturn(savedOrder);
        when(orderRepository.findById(id)).thenReturn(java.util.Optional.of(savedOrder));
        when(productRepository.findById(id)).thenReturn(java.util.Optional.of(product));

        //when
        OrderDTO savedDto = orderService.updateOrder(orderItemDTO);

        //then
        assertEquals(orderItemDTO.getQuantity(), savedDto.getOrderItems().stream().findFirst().get().getQuantity());
    }

    @Test
    void deleteOrderById() {
        Long id = 1L;

        orderRepository.deleteById(id);

        verify(orderRepository, times(1)).deleteById(id);
    }

    @Test
    void finalizeOrder() {
        Long id = 1L;

        //given
        OrderItemDTO orderItemDTO = new OrderItemDTO();
        orderItemDTO.setId(id);
        orderItemDTO.setQuantity(12);
        orderItemDTO.setOrderId(id);
        orderItemDTO.setProductId(id);

        Set<OrderItemDTO> orderItemDTOS = new HashSet<>();
        orderItemDTOS.add(orderItemDTO);

        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setId(id);
        orderDTO.setStatus(OrderStatus.DRAFT);
        orderDTO.setTotalPriceHrk(BigDecimal.valueOf(100));
        orderDTO.setTotalPriceEur(BigDecimal.valueOf(13.33));
        orderDTO.setOrderItems(orderItemDTOS);

        Product product = new Product();
        product.setId(id);
        product.setAvailable(true);
        product.setCode("1234567890");
        product.setDescription("sdsadsad");
        product.setName("product");
        product.setPriceHrk(BigDecimal.valueOf(1000));

        Order savedOrder = new Order();
        savedOrder.setId(orderDTO.getId());
        savedOrder.setStatus(orderDTO.getStatus());
        savedOrder.setTotalPriceHrk(orderDTO.getTotalPriceHrk());
        savedOrder.setTotalPriceEur(orderDTO.getTotalPriceEur());

        OrderItem orderItem = new OrderItem();
        orderItem.setId(orderItemDTO.getId());
        orderItem.setQuantity(orderItemDTO.getQuantity());
        orderItem.setOrder(savedOrder);
        orderItem.setProduct(product);

        Set<OrderItem> orderItems = new HashSet<>();
        orderItems.add(orderItem);
        savedOrder.setOrderItems(orderItems);

        when(orderRepository.findById(id)).thenReturn(java.util.Optional.of(savedOrder));
        when(orderRepository.save(any(Order.class))).thenReturn(savedOrder);

        //when
        OrderDTO savedDto = orderService.finalizeOrder(id);

        //then
        assertEquals(OrderStatus.SUBMITTED,savedDto.getStatus());
    }
}