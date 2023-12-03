package com.digitalcomanetwork.kosterme.productservice.mapper;

import com.digitalcomanetwork.kosterme.productservice.data.ProductEntity;
import com.digitalcomanetwork.kosterme.productservice.shared.ProductDto;
import com.digitalcomanetwork.kosterme.productservice.ui.model.CreateProductRequestModel;
import com.digitalcomanetwork.kosterme.productservice.ui.model.CreateProductResponseModel;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ProductMapper {
    ProductEntity productDtoToProductEntity(ProductDto productDetails);
    ProductDto productRequestToProductDto(CreateProductRequestModel productDetails);
    CreateProductResponseModel productDtoToProductResponse(ProductDto productDetails);
    ProductDto productEntityToProductDto(ProductEntity productEntity);
}
