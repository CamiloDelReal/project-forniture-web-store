package com.example.productservice.repositories;

import com.example.productservice.entities.ProductCategory;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface ProductCategoryRepository extends CrudRepository<ProductCategory, ProductCategory.ProductCategoryId> {

    @Transactional
    @Modifying
    @Query(value = "DELETE FROM products_categories WHERE category_id = :categoryId", nativeQuery = true)
    int deleteProductsInCategory(Long categoryId);

    @Transactional
    @Modifying
    @Query(value = "DELETE FROM products_categories WHERE product_id = :productId", nativeQuery = true)
    int deleteCategoriesByProduct(Long productId);

}
