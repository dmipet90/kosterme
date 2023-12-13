package com.digitalcomanetwork.kosterme.productservice.data;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.Date;

@Entity
@Table(name="product")
@Getter @Setter
public class ProductEntity {
    @Id
    @GeneratedValue
    private long id;
    @Column(nullable = false, unique = true)
    private String productId;
    @Column(nullable = false, unique = true)
    private String name;
    @Column(nullable = false)
    private String artist;
    @Column(nullable = false)
    private String description;
    @Column(nullable = false)
    private LocalDate releaseDate;
    private String imageId;
    @Column(nullable = false)
    private String category;
}
