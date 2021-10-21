package com.example.webshop.services;

import com.example.webshop.api.model.ProductDTO;


import java.util.List;
import java.util.Optional;

public interface ProductService {

    List<ProductDTO> getAllProducts();

    ProductDTO getProductById(Long id);

    ProductDTO createNewProduct(ProductDTO productDTO);

    ProductDTO updateProduct(Long id, ProductDTO productDTO);

    void deleteProductById(Long id);

}
