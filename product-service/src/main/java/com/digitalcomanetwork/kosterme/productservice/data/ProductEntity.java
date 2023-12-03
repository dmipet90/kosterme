package com.digitalcomanetwork.kosterme.productservice.data;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Max;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="product")
@Getter @Setter
public class ProductEntity {
    @Id
    @GeneratedValue
    private long id;
    @Column(nullable = false, unique = true)
    private String productId;
    @Column(nullable = false, length = 50)
    private String name;
    @Column(nullable = false)
    private String description;
}
