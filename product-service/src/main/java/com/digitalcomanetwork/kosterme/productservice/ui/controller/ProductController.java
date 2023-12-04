package com.digitalcomanetwork.kosterme.productservice.ui.controller;

import com.digitalcomanetwork.kosterme.productservice.mapper.ProductMapper;
import com.digitalcomanetwork.kosterme.productservice.service.ProductService;
import com.digitalcomanetwork.kosterme.productservice.shared.ProductDto;
import com.digitalcomanetwork.kosterme.productservice.ui.model.CreateProductRequestModel;
import com.digitalcomanetwork.kosterme.productservice.ui.model.CreateProductResponseModel;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/product")
@RequiredArgsConstructor
public class ProductController {
    @Autowired
    private Environment env;
    private final ProductMapper productMapper;
    private final ProductService productService;

    @GetMapping("/status/check")
    public String getStatus() {
        return "Working on port " + env.getProperty("local.server.port");
    }

    @PostMapping("/create")
    public ResponseEntity<CreateProductResponseModel> createProduct(
            @Valid @RequestBody CreateProductRequestModel productDetails) {
        ProductDto productDto = productMapper.productRequestToProductDto(productDetails);
        productDto = productService.createProduct(productDto);
        CreateProductResponseModel response = productMapper.productDtoToProductResponse(productDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
