package com.digitalcomanetwork.kosterme.productservice.shared;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter @Setter
public class ProductDto implements Serializable {
    private String productId;
    private String name;
    private String description;
}
