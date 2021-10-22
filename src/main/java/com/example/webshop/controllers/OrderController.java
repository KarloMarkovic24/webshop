package com.example.webshop.controllers;

import com.example.webshop.api.model.OrderDTO;

import com.example.webshop.api.model.OrderItemDTO;
import com.example.webshop.api.model.ProductDTO;
import com.example.webshop.services.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/order")
public class OrderController {
    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping({"/{id}"})
    @ResponseStatus(HttpStatus.OK)
    public OrderDTO readOrder(@PathVariable Long id){
        return orderService.readOrder(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public OrderDTO createOrder(@RequestBody OrderDTO orderDTO){
        return orderService.createNewOrder(orderDTO);
    }

    @PutMapping({"/{id}"})
    @ResponseStatus(HttpStatus.OK)
    public OrderDTO updateOrder(@PathVariable Long id, @RequestBody OrderItemDTO orderItemDTO){
        return orderService.updateOrder(id,orderItemDTO);
    }

    @DeleteMapping({"{/id}"})
    @ResponseStatus(HttpStatus.OK)
    public void deleteOrderById(@PathVariable Long id){orderService.deleteOrderById(id);}

}
