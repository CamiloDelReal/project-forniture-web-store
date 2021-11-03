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
        if (categoryRepository.count() == 0 && productRepository.count() == 0) {
            String descriptions = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Pellentesque blandit felis sit amet erat sagittis ornare. Donec hendrerit bibendum imperdiet. Class aptent taciti sociosqu ad litora torquent per conubia nostra.";
            Category bedsCategory = new Category("Beds", descriptions, "bed-portrait-1.png", "bed-landscape-1.png");
            bedsCategory = categoryRepository.save(bedsCategory);
            Category chairsCategory = new Category("Chairs", descriptions, "chair-portrait-1.png", "chair-landscape-1.png");
            chairsCategory = categoryRepository.save(chairsCategory);
            Category couchsCategory = new Category("Couchs", descriptions, "couch-portrait-1.png", "couch-landscape-1.png");
            couchsCategory = categoryRepository.save(couchsCategory);
            Category tablesCategory = new Category("Tables", descriptions, "table-portrait-1.png", "table-landscape-1.png");
            tablesCategory = categoryRepository.save(tablesCategory);
            Category wardrobesCategory = new Category("Wardrobes", descriptions, "wardrobe-portrait-1.png", "wardrobe-landscape-1.png");
            wardrobesCategory = categoryRepository.save(wardrobesCategory);

            Product bed1Product = new Product("Bed 1", descriptions, 845.50f, 22, "bed-portrait-1.png", "bed-landscape-1.png", List.of(bedsCategory));
            bed1Product = productRepository.save(bed1Product);
            Product bed2Product = new Product("Bed 2", descriptions, 480.00f, 7, "bed-portrait-2.png", "bed-landscape-2.png", List.of(bedsCategory));
            bed2Product = productRepository.save(bed2Product);
            Product bed3Product = new Product("Bed 3", descriptions, 920.84f, 50, "bed-portrait-3.png", "bed-landscape-3.png", List.of(bedsCategory));
            bed3Product = productRepository.save(bed3Product);
            Product bed4Product = new Product("Bed 4", descriptions, 690.99f, 34, "bed-portrait-4.png", "bed-landscape-4.png", List.of(bedsCategory));
            bed4Product = productRepository.save(bed4Product);
            Product bed5Product = new Product("Bed 5", descriptions, 700.00f, 13, "bed-portrait-5.png", "bed-landscape-5.png", List.of(bedsCategory));
            bed5Product = productRepository.save(bed5Product);

            Product chair1Product = new Product("Chair 1", descriptions, 120.45f, 12, "chair-portrait-1.png", "chair-landscape-1.png", List.of(chairsCategory));
            chair1Product = productRepository.save(chair1Product);
            Product chair2Product = new Product("Chair 2", descriptions, 84.99f, 21, "chair-portrait-2.png", "chair-landscape-2.png", List.of(chairsCategory));
            chair2Product = productRepository.save(chair2Product);
            Product chair3Product = new Product("Chair 3", descriptions, 92.30f, 46, "chair-portrait-3.png", "chair-landscape-3.png", List.of(chairsCategory));
            chair3Product = productRepository.save(chair3Product);
            Product chair4Product = new Product("Chair 4", descriptions, 86.99f, 2, "chair-portrait-4.png", "chair-landscape-4.png", List.of(chairsCategory));
            chair4Product = productRepository.save(chair4Product);
            Product chair5Product = new Product("Chair 5", descriptions, 23.00f, 6, "chair-portrait-5.png", "chair-landscape-5.png", List.of(chairsCategory));
            chair5Product = productRepository.save(chair5Product);

            Product couch1Product = new Product("Couch 1", descriptions, 255.00f, 4, "couch-portrait-1.png", "couch-landscape-1.png", List.of(couchsCategory));
            couch1Product = productRepository.save(couch1Product);
            Product couch2Product = new Product("Couch 2", descriptions, 158.00f, 8, "couch-portrait-2.png", "couch-landscape-2.png", List.of(couchsCategory));
            couch2Product = productRepository.save(couch2Product);
            Product couch3Product = new Product("Couch 3", descriptions, 699.99f, 2, "couch-portrait-3.png", "couch-landscape-3.png", List.of(couchsCategory));
            couch3Product = productRepository.save(couch3Product);
            Product couch4Product = new Product("Couch 4", descriptions, 200.00f, 5, "couch-portrait-4.png", "couch-landscape-4.png", List.of(couchsCategory));
            couch4Product = productRepository.save(couch4Product);

            Product table1Product = new Product("Table 1", descriptions, 400.00f, 1, "table-portrait-1.png", "table-landscape-1.png", List.of(tablesCategory));
            table1Product = productRepository.save(table1Product);
            Product table2Product = new Product("Table 2", descriptions, 250.85f, 3, "table-portrait-2.png", "table-landscape-2.png", List.of(tablesCategory));
            table2Product = productRepository.save(table2Product);
            Product table3Product = new Product("Table 3", descriptions, 325.99f, 6, "table-portrait-3.png", "table-landscape-3.png", List.of(tablesCategory));
            table3Product = productRepository.save(table3Product);

            Product wardrobe1Product = new Product("Wardrobe 1", descriptions, 999.99f, 2, "wardrobe-portrait-1.png", "wardrobe-landscape-1.png", List.of(wardrobesCategory));
            wardrobe1Product = productRepository.save(wardrobe1Product);
            Product wardrobe2Product = new Product("Wardrobe 2", descriptions, 750.55f, 4, "wardrobe-portrait-2.png", "wardrobe-landscape-2.png", List.of(wardrobesCategory));
            wardrobe2Product = productRepository.save(wardrobe2Product);
            Product wardrobe3Product = new Product("Wardrobe 3", descriptions, 540.00f, 1, "wardrobe-portrait-3.png", "wardrobe-landscape-3.png", List.of(wardrobesCategory));
            wardrobe3Product = productRepository.save(wardrobe3Product);
            Product wardrobe4Product = new Product("Wardrobe 4", descriptions, 800.60f, 6, "wardrobe-portrait-4.png", "wardrobe-landscape-4.png", List.of(wardrobesCategory));
            wardrobe4Product = productRepository.save(wardrobe4Product);
            Product wardrobe5Product = new Product("Wardrobe 5", descriptions, 745.50f, 4, "wardrobe-portrait-5.png", "wardrobe-landscape-5.png", List.of(wardrobesCategory));
            wardrobe5Product = productRepository.save(wardrobe5Product);
        }
    }
}
