package com.graphql.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "product")
public class Product {
    @Id
    private String id;
    private String name;
    private String description;
    private Double price;
    private Integer quantity;
    private String category;
    private boolean inStock;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;
}

