package com.example.webshop.services;

import com.example.webshop.api.mapper.ProductMapper;
import com.example.webshop.api.model.ProductDTO;
import com.example.webshop.domain.Product;
import com.example.webshop.exceptions.CodeNotUniqueException;
import com.example.webshop.exceptions.ResourceNotFoundException;
import com.example.webshop.repositories.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductMapper productMapper;
    private final ProductRepository productRepository;

    public ProductServiceImpl(ProductMapper productMapper, ProductRepository productRepository) {
        this.productMapper = productMapper;
        this.productRepository = productRepository;
    }

    @Override
    public List<ProductDTO> getAllProducts() {
        return productRepository
                .findAll()
                .stream()
                .map(product -> {
                    ProductDTO productDTO = productMapper.toDto(product);
                    return productDTO;
                })
                .collect(Collectors.toList());
    }

    @Override
    public ProductDTO getProductById(Long id) {
        return productRepository.findById(id)
                .map(productMapper::toDto)
                .orElseThrow(ResourceNotFoundException::new);
    }

    @Override
    public ProductDTO createNewProduct(ProductDTO productDTO) {
        if(productRepository.existsByCode(productDTO.getCode())) throw new CodeNotUniqueException();

        return saveAndReturnDTO(productMapper.toEntity(productDTO));
    }

    private ProductDTO saveAndReturnDTO(Product product) {
        Product savedProduct = productRepository.save(product);

        ProductDTO returnDTO = productMapper.toDto(savedProduct);

        return returnDTO;
    }


    @Override
    public ProductDTO updateProduct(Long id, ProductDTO productDTO) {
        if(productRepository.existsByCode(productDTO.getCode())) throw new CodeNotUniqueException();

        Product product = productMapper.toEntity(productDTO);
        product.setId(id);

        return saveAndReturnDTO(product) ;
    }

    @Override
    public void deleteProductById(Long id) {
        productRepository.deleteById(id);
    }
}
