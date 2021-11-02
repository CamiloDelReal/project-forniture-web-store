package com.example.productservice.entities;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "products_categories")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ProductCategory {
    @EmbeddedId
    private ProductCategoryId id;

    @Embeddable
    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @ToString
    public static final class ProductCategoryId implements Serializable {
        @Column(name = "product_id")
        private Long productId;

        @Column(name = "category_id")
        private Long categoryId;
    }
}
