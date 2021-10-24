package com.example.webshop.services;

import com.example.webshop.api.mapper.OrderItemMapperImpl;
import com.example.webshop.api.mapper.OrderMapperImpl;
import com.example.webshop.api.mapper.ProductMapper;
import com.example.webshop.api.mapper.ProductMapperImpl;
import com.example.webshop.api.model.ProductDTO;
import com.example.webshop.domain.Product;
import com.example.webshop.repositories.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest(classes = {ProductMapperImpl.class})
class ProductServiceImplTest {

    ProductServiceImpl productService;

    @Autowired
    ProductMapper productMapper;

    @Mock
    ProductRepository productRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        productService = new ProductServiceImpl(productMapper,productRepository);
    }

    @Test
    void getAllProducts() {

        //given
        Product product1 = new Product();
        product1.setId(1L);
        product1.setAvailable(true);
        product1.setCode("1234567890");
        product1.setDescription("sdsadsad");
        product1.setName("product1");
        product1.setPriceHrk(BigDecimal.valueOf(1000));

        Product product2 = new Product();
        product2.setId(2L);
        product2.setAvailable(true);
        product2.setCode("1234567891");
        product2.setDescription("jsajdjasd");
        product2.setName("product2");
        product2.setPriceHrk(BigDecimal.valueOf(1234));

        when(productRepository.findAll()).thenReturn(Arrays.asList(product1,product2));

        //when
        List<ProductDTO> productDTOS = productService.getAllProducts();

        //then
        assertEquals(2, productDTOS.size());

    }

    @Test
    void getProductById() {
        Long id = 1L;
        String name = "Product1";

        //given
        Product product1 = new Product();
        product1.setId(id);
        product1.setAvailable(true);
        product1.setCode("1234567890");
        product1.setDescription("sdsadsad");
        product1.setName(name);
        product1.setPriceHrk(BigDecimal.valueOf(1000));

        when(productRepository.findById(id)).thenReturn(java.util.Optional.ofNullable(product1));

       //when
        ProductDTO productDTO = productService.getProductById(id);

        //then
        assertEquals(name,productDTO.getName());
    }

    @Test
    void createNewProduct() {

        //given
        ProductDTO productDTO = new ProductDTO();
        productDTO.setId(1L);
        productDTO.setAvailable(true);
        productDTO.setCode("1234567890");
        productDTO.setDescription("sdsadsad");
        productDTO.setName("Product1");
        productDTO.setPriceHrk(BigDecimal.valueOf(1000));

        Product savedProduct = new  Product();
        savedProduct.setId(productDTO.getId());
        savedProduct.setAvailable(productDTO.isAvailable());
        savedProduct.setCode(productDTO.getCode());
        savedProduct.setDescription(productDTO.getDescription());
        savedProduct.setName(productDTO.getName());
        savedProduct.setPriceHrk(productDTO.getPriceHrk());

        when(productRepository.save(any(Product.class))).thenReturn(savedProduct);

        //when
        ProductDTO savedDTO = productService.createNewProduct(productDTO);

        //then
        assertEquals(productDTO.getName(),savedDTO.getName());
    }

    @Test
    void updateProduct() {
        Long id = 1L;

        //given
        ProductDTO productDTO = new ProductDTO();
        productDTO.setId(id);
        productDTO.setAvailable(true);
        productDTO.setCode("1234567890");
        productDTO.setDescription("sdsadsad");
        productDTO.setName("Product1");
        productDTO.setPriceHrk(BigDecimal.valueOf(1000));

        Product savedProduct = new  Product();
        savedProduct.setId(productDTO.getId());
        savedProduct.setAvailable(productDTO.isAvailable());
        savedProduct.setCode(productDTO.getCode());
        savedProduct.setDescription(productDTO.getDescription());
        savedProduct.setName(productDTO.getName());
        savedProduct.setPriceHrk(productDTO.getPriceHrk());

        when(productRepository.save(any(Product.class))).thenReturn(savedProduct);

        //when
        ProductDTO savedDTO = productService.updateProduct(id,productDTO);

        //then
        assertEquals(productDTO.getName(),savedDTO.getName());

    }

    @Test
    void deleteProductById() {
        Long id = 1L;

        productRepository.deleteById(id);

    verify(productRepository, times(1)).deleteById(id);
    }
}