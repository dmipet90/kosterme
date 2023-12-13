package com.digitalcomanetwork.kosterme.productservice.ui.model;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter @Setter
public class CreateProductResponseModel {
    private String productId;
    private String name;
    private String artist;
    private String description;
    private LocalDate releaseDate;
    private String imageId;
    private String category;
}
