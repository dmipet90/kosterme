package com.digitalcomanetwork.kosterme.productservice.ui.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.Date;

@Getter @Setter
public class CreateProductRequestModel {
    @NotNull(message = "Product name cannot be null")
    @Size(min = 2, message = "Product name must not be less than two characters")
    private String name;
    @NotNull(message = "Product artist cannot be null")
    @Size(min = 2, message = "Product artist must not be less than two characters")
    private String artist;
    @NotNull(message = "Product description cannot be null")
    @Size(min = 2, message = "Product description must not be less than two characters")
    private String description;
    @NotNull(message = "Product release date cannot be null")
    @DateTimeFormat(pattern = "yyyy-mm-dd")
    private LocalDate releaseDate;
    private String imageId;
    @NotNull(message = "Product category cannot be null")
    @Size(min = 2, message = "Product category must not be less than two characters")
    private String category;
}
