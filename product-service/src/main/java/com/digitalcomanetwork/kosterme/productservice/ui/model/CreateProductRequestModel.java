package com.digitalcomanetwork.kosterme.productservice.ui.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class CreateProductRequestModel {
    @NotNull(message = "Product name cannot be null")
    @Size(min = 2, message = "Product name must not be less than two characters")
    private String name;
    private String description;
}
