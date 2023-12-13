package com.digitalcomanetwork.kosterme.productservice.exception;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ProductNameExceptionResponse {
    private String name;

    public ProductNameExceptionResponse(String name) {
        this.name = name;
    }


}
