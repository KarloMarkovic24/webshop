package com.example.webshop.controllers;

import com.example.webshop.api.model.ProductDTO;
import com.example.webshop.services.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/product")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<ProductDTO> getListOfProducts(){
        return productService.getAllProducts();
    }

    @GetMapping({"/{id}"})
    @ResponseStatus(HttpStatus.OK)
    public ProductDTO getProductById(@PathVariable Long id){
        return productService.getProductById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProductDTO createNewProduct(@Valid @RequestBody ProductDTO productDTO){
        return productService.createNewProduct( productDTO);
    }

    @PutMapping({"/{id}"})
    @ResponseStatus(HttpStatus.OK)
    public ProductDTO updateProduct(@Valid @PathVariable Long id, @RequestBody ProductDTO productDTO){
        return productService.updateProduct(id,productDTO);
    }

    @DeleteMapping({"/{id}"})
    @ResponseStatus(HttpStatus.OK)
    public void deleteProduct(@PathVariable Long id){ productService.deleteProductById(id); }

}
