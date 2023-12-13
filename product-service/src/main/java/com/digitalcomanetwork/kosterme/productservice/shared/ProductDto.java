package com.digitalcomanetwork.kosterme.productservice.shared;

import jakarta.persistence.Column;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDate;

@Getter @Setter
public class ProductDto implements Serializable {
    private String productId;
    private String name;
    private String artist;
    private String description;
    private LocalDate releaseDate;
    private String imageId;
    private String category;
}
