package com.example.productservice.seeders;

import com.example.productservice.entities.Category;
import com.example.productservice.entities.Product;
import com.example.productservice.repositories.CategoryRepository;
import com.example.productservice.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DatabaseSeeder {

    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;

    @Autowired
    public DatabaseSeeder(CategoryRepository categoryRepository, ProductRepository productRepository) {
        this.categoryRepository = categoryRepository;
        this.productRepository = productRepository;
    }

    @EventListener
    public void seedDatabase(ContextRefreshedEvent event) {
        if(categoryRepository.count() == 0 && productRepository.count() == 0) {
            Category bedsCategory = new Category("Beds", "bed-1.jpg");
            bedsCategory = categoryRepository.save(bedsCategory);
            Category chairsCategory = new Category("Chairs", "chair-1.jpg");
            chairsCategory = categoryRepository.save(chairsCategory);
            Category couchsCategory = new Category("Couchs", "couch-1.jpg");
            couchsCategory = categoryRepository.save(couchsCategory);
            Category tablesCategory = new Category("Tables", "table-1.jpg");
            tablesCategory = categoryRepository.save(tablesCategory);
            Category wardrobesCategory = new Category("Wardrobes", "wardrobe-1.jpg");
            wardrobesCategory = categoryRepository.save(wardrobesCategory);

            Product bed1Product = new Product("Bed 1", "", 845.50f, 22, "bed-1.jpg", List.of(bedsCategory));
            bed1Product = productRepository.save(bed1Product);
            Product bed2Product = new Product("Bed 2", "", 480.00f, 7, "bed-2.jpg", List.of(bedsCategory));
            bed2Product = productRepository.save(bed2Product);
            Product bed3Product = new Product("Bed 3", "", 920.84f, 50, "bed-3.jpg", List.of(bedsCategory));
            bed3Product = productRepository.save(bed3Product);
            Product bed4Product = new Product("Bed 4", "", 690.99f, 34, "bed-4.jpg", List.of(bedsCategory));
            bed4Product = productRepository.save(bed4Product);
            Product bed5Product = new Product("Bed 5", "", 700.00f, 13, "bed-5.jpg", List.of(bedsCategory));
            bed5Product = productRepository.save(bed5Product);

            Product chair1Product = new Product("Chair 1", "", 120.45f, 12, "chair-1.jpg", List.of(chairsCategory));
            chair1Product = productRepository.save(chair1Product);
            Product chair2Product = new Product("Chair 2", "", 84.99f, 21, "chair-2.jpg", List.of(chairsCategory));
            chair2Product = productRepository.save(chair2Product);
            Product chair3Product = new Product("Chair 3", "", 92.30f, 46, "chair-3.jpg", List.of(chairsCategory));
            chair3Product = productRepository.save(chair3Product);
            Product chair4Product = new Product("Chair 4", "", 86.99f, 2, "chair-4.jpg", List.of(chairsCategory));
            chair4Product = productRepository.save(chair4Product);

            Product couch1Product = new Product("Couch 1", "", 255.00f, 4, "couch-1.jpg", List.of(couchsCategory));
            couch1Product = productRepository.save(couch1Product);
            Product couch2Product = new Product("Couch 2", "", 158.00f, 8, "couch-2.jpg", List.of(couchsCategory));
            couch2Product = productRepository.save(couch2Product);
            Product couch3Product = new Product("Couch 3", "", 699.99f, 2, "couch-3.jpg", List.of(couchsCategory));
            couch3Product = productRepository.save(couch3Product);
            Product couch4Product = new Product("Couch 4", "", 200.00f, 5, "couch-4.jpg", List.of(couchsCategory));
            couch4Product = productRepository.save(couch4Product);

            Product table1Product = new Product("Table 1", "", 400.00f, 1, "table-1.jpg", List.of(tablesCategory));
            table1Product = productRepository.save(table1Product);
            Product table2Product = new Product("Table 2", "", 250.85f, 3, "table-2.jpg", List.of(tablesCategory));
            table2Product = productRepository.save(table2Product);
            Product table3Product = new Product("Table 3", "", 325.99f, 6, "table-3.jpg", List.of(tablesCategory));
            table3Product = productRepository.save(table3Product);

            Product wardrobe1Product = new Product("Wardrobe 1", "", 999.99f, 2, "wardrobe-1.jpg", List.of(wardrobesCategory));
            wardrobe1Product = productRepository.save(wardrobe1Product);
            Product wardrobe2Product = new Product("Wardrobe 2", "", 750.55f, 4, "wardrobe-2.jpg", List.of(wardrobesCategory));
            wardrobe2Product = productRepository.save(wardrobe2Product);
            Product wardrobe3Product = new Product("Wardrobe 3", "", 540.00f, 1, "wardrobe-3.jpg", List.of(wardrobesCategory));
            wardrobe3Product = productRepository.save(wardrobe3Product);
            Product wardrobe4Product = new Product("Wardrobe 4", "", 800.60f, 6, "wardrobe-4.jpg", List.of(wardrobesCategory));
            wardrobe4Product = productRepository.save(wardrobe4Product);
            Product wardrobe5Product = new Product("Wardrobe 5", "", 745.50f, 4, "wardrobe-5.jpg", List.of(wardrobesCategory));
            wardrobe5Product = productRepository.save(wardrobe5Product);
            Product wardrobe6Product = new Product("Wardrobe 6", "", 845.99f, 3, "wardrobe-6.jpg", List.of(wardrobesCategory));
            wardrobe6Product = productRepository.save(wardrobe6Product);
        }
    }
}
