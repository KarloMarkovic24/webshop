package com.example.webshop.controllers;

import com.example.webshop.api.model.OrderDTO;
import com.example.webshop.api.model.OrderItemDTO;
import com.example.webshop.domain.OrderStatus;
import com.example.webshop.exceptions.ProductNotAvailableException;
import com.example.webshop.exceptions.ResourceNotFoundException;
import com.example.webshop.services.OrderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.mockito.Mockito.when;

class OrderControllerTest extends AbstractRestControllerTest {

    String BASE_URL = "/order";

    @Mock
    OrderService orderService;

    @InjectMocks
    OrderController orderController;

    MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        mockMvc = MockMvcBuilders.standaloneSetup(orderController)
                .setControllerAdvice(new RestResponseEntityExceptionHandler())
                .build();
    }

    @Test
    void readOrder() throws Exception {
        Long id = 1L;

        //given
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setId(id);
        orderDTO.setStatus(OrderStatus.DRAFT);
        orderDTO.setTotalPriceHrk(BigDecimal.valueOf(100));
        orderDTO.setTotalPriceEur(BigDecimal.valueOf(13.33));

        when(orderService.readOrder(id)).thenReturn(orderDTO);

        //when/then
        mockMvc.perform(get(BASE_URL+"/"+id)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalPriceHrk",equalTo(100)));
    }

    @Test
    void createOrder() throws Exception {

        //given
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setId(1L);
        orderDTO.setStatus(OrderStatus.DRAFT);
        orderDTO.setTotalPriceHrk(BigDecimal.valueOf(100));
        orderDTO.setTotalPriceEur(BigDecimal.valueOf(13.33));

        when(orderService.createNewOrder(orderDTO)).thenReturn(orderDTO);

        //when/then
        mockMvc.perform(post(BASE_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(orderDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.totalPriceHrk",equalTo(100)));
    }

    @Test
    void updateOrder() throws Exception {
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


        when(orderService.updateOrder(orderItemDTO)).thenReturn(orderDTO);

        //when/then
        mockMvc.perform(put(BASE_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(orderItemDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.orderItems[0].quantity",equalTo(12)));
    }

    @Test
    void deleteOrderById() throws Exception {
        Long id = 1L;

        mockMvc.perform(delete(BASE_URL+"/"+id)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(orderService).deleteOrderById(id);
    }

    @Test
    void  testProductNotAvailableException() throws Exception{
        when(orderService.updateOrder(any(OrderItemDTO.class))).thenThrow(ProductNotAvailableException.class);

        mockMvc.perform(put(BASE_URL)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }


    @Test
    void testResourceNotFoundException() throws Exception{
        when(orderService.readOrder(any(Long.class))).thenThrow(ResourceNotFoundException.class);

        mockMvc.perform(get(BASE_URL+"/11")
               .contentType(MediaType.APPLICATION_JSON))
               .andExpect(status().isNotFound());

    }


}