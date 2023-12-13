package com.digitalcomanetwork.kosterme.productservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ProductNameException extends RuntimeException {
    public ProductNameException(String message) {
        super(message);
    }

}