package com.digitalcomanetwork.kosterme.productservice.service;

import com.digitalcomanetwork.kosterme.productservice.data.ProductEntity;
import com.digitalcomanetwork.kosterme.productservice.data.ProductRepository;
import com.digitalcomanetwork.kosterme.productservice.mapper.ProductMapper;
import com.digitalcomanetwork.kosterme.productservice.shared.ProductDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductMapper productMapper;
    private final ProductRepository productRepository;

    @Override
    public ProductDto createProduct(ProductDto productDetails) {
        productDetails.setProductId(UUID.randomUUID().toString());
        ProductEntity productEntity = productMapper.productDtoToProductEntity(productDetails);
        productRepository.save(productEntity);
        return productMapper.productEntityToProductDto(productEntity);
    }


}
