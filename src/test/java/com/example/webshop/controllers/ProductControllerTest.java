package com.example.webshop.controllers;

import com.example.webshop.api.model.ProductDTO;
import com.example.webshop.exceptions.CodeNotUniqueException;
import com.example.webshop.exceptions.ResourceNotFoundException;
import com.example.webshop.services.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigDecimal;
import java.util.Arrays;


import static org.mockito.ArgumentMatchers.any;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class ProductControllerTest extends AbstractRestControllerTest{

    String BASE_URL = "/product";
    @Mock
    ProductService productService;

    @InjectMocks
    ProductController productController;

    MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        mockMvc = MockMvcBuilders.standaloneSetup(productController)
                .setControllerAdvice(new RestResponseEntityExceptionHandler())
                .build();
    }

    @Test
    void getListOfProducts() throws Exception {

        //given
        ProductDTO productDTO = new ProductDTO();
        productDTO.setId(1L);
        productDTO.setAvailable(true);
        productDTO.setCode("1234567890");
        productDTO.setDescription("sdsadsad");
        productDTO.setName("Product1");
        productDTO.setPriceHrk(BigDecimal.valueOf(1000));

        ProductDTO productDTO2 = new ProductDTO();
        productDTO2.setId(2L);
        productDTO2.setAvailable(true);
        productDTO2.setCode("1221127134");
        productDTO2.setDescription("description2");
        productDTO2.setName("Product2");
        productDTO2.setPriceHrk(BigDecimal.valueOf(100));

        when(productService.getAllProducts()).thenReturn(Arrays.asList(productDTO,productDTO2));
        
        ///when/then
        mockMvc.perform(get(BASE_URL)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$" , hasSize(2)))
                .andExpect(jsonPath("$[0].name",is("Product1")));
    }

    @Test
    void getProductById() throws Exception {
        Long id =1L;
        String name = "Product1";

        //given
        ProductDTO productDTO = new ProductDTO();
        productDTO.setId(id);
        productDTO.setAvailable(true);
        productDTO.setCode("1234567890");
        productDTO.setDescription("sdsadsad");
        productDTO.setName(name);
        productDTO.setPriceHrk(BigDecimal.valueOf(1000));

        when(productService.getProductById(id)).thenReturn(productDTO);
        
        //when/then
        mockMvc.perform(get(BASE_URL+"/"+id)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name",equalTo(name)));

    }

    @Test
    void createNewProduct() throws Exception {
        String name = "Product1";

        //given
        ProductDTO productDTO = new ProductDTO();
        productDTO.setId(1L);
        productDTO.setAvailable(true);
        productDTO.setCode("1234567890");
        productDTO.setDescription("sdsadsad");
        productDTO.setName(name);
        productDTO.setPriceHrk(BigDecimal.valueOf(1000));

        when(productService.createNewProduct(productDTO)).thenReturn(productDTO);

        //when/then
        mockMvc.perform(post(BASE_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(productDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name",equalTo(name)));

    }

    @Test
    void updateProduct() throws Exception {
        Long id = 1L;
        String name = "Product1";

        //given
        ProductDTO productDTO = new ProductDTO();
        productDTO.setId(id);
        productDTO.setAvailable(true);
        productDTO.setCode("1234567890");
        productDTO.setDescription("sdsadsad");
        productDTO.setName(name);
        productDTO.setPriceHrk(BigDecimal.valueOf(1000));

        when(productService.updateProduct(id,productDTO)).thenReturn(productDTO);

        //when/then
        mockMvc.perform(put(BASE_URL + "/"+id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(productDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name",equalTo(name)));

    }

    @Test
    void deleteProduct() throws Exception {
        Long id = 1L;

        mockMvc.perform(delete(BASE_URL+"/"+id)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(productService).deleteProductById(id);
    }

    @Test
    void testCodeNotUniqueException() throws Exception{
        when(productService.createNewProduct(any(ProductDTO.class))).thenThrow(CodeNotUniqueException.class);

        mockMvc.perform(post(BASE_URL)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());

    }

    @Test
    void testResourceNotFoundException() throws Exception{
        when(productService.getProductById(any(Long.class))).thenThrow(ResourceNotFoundException.class);

        mockMvc.perform(get(BASE_URL+"/11")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

    }
}