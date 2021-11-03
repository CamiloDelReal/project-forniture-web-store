package com.example.productservice.repositories;

import com.example.productservice.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    Optional<Product> findByName(String name);

    @Transactional
    @Query(value = "SELECT products.* FROM products, products_categories WHERE products.id = products_categories.product_id AND products_categories.category_id = :categoryId", nativeQuery = true)
    List<Product> findAllByCategoryId(Long categoryId);
}
