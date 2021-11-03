package com.example.productservice.services;

import com.example.productservice.dtos.ProductRequest;
import com.example.productservice.dtos.ProductResponse;
import com.example.productservice.entities.Category;
import com.example.productservice.entities.Product;
import com.example.productservice.repositories.CategoryRepository;
import com.example.productservice.repositories.ProductCategoryRepository;
import com.example.productservice.repositories.ProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ProductService {
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final ProductCategoryRepository productCategoryRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public ProductService(ProductRepository productRepository, CategoryRepository categoryRepository,
                          ProductCategoryRepository productCategoryRepository, ModelMapper modelMapper) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
        this.productCategoryRepository = productCategoryRepository;
        this.modelMapper = modelMapper;
    }

    public List<ProductResponse> getAll() {
        List<ProductResponse> response = null;
        List<Product> products = productRepository.findAll();
        if (products != null && !products.isEmpty()) {
            response = products.stream().map(p -> modelMapper.map(p, ProductResponse.class)).collect(Collectors.toList());
        } else {
            response = new ArrayList<>();
        }
        return response;
    }

    public ProductResponse getById(Long id) {
        log.info("getById " + id);
        ProductResponse response = null;
        Product product = productRepository.findById(id).orElse(null);
        if (product != null) {
            response = modelMapper.map(product, ProductResponse.class);
        }
        log.info("getByid returning " + response);
        return response;
    }

    public List<ProductResponse> getByCategory(Long categoryId) {
        log.info("getByCategory " + categoryId);
        List<ProductResponse> response = null;
        List<Product> products = productRepository.findAllByCategoryId(categoryId);
        if(products != null && !products.isEmpty()) {
            response = products.stream().map(p -> modelMapper.map(p, ProductResponse.class)).collect(Collectors.toList());
        } else {
            response = new ArrayList<>();
        }
        return response;
    }

    public ProductResponse create(ProductRequest productRequest) {
        log.info("create " + productRequest);
        ProductResponse response = null;
        Product duplicity = productRepository.findByName(productRequest.getName()).orElse(null);
        if (duplicity == null) {
            Product product = modelMapper.map(productRequest, Product.class);
            List<Category> categories = productRequest.getCategories().stream().map(c -> categoryRepository.findByName(c).orElse(null)).filter(Objects::nonNull).collect(Collectors.toList());
            product.setCategories(categories);
            product = productRepository.save(product);
            response = modelMapper.map(product, ProductResponse.class);
        }
        log.info("create returning " + response);
        return response;
    }

    public ProductResponse edit(Long id, ProductRequest productRequest) {
        log.info("edit " + id + " " + productRequest);
        ProductResponse response = null;
        Product product = productRepository.findById(id).orElse(null);
        if (product != null) {
            if (productRequest.getName() != null)
                product.setName(productRequest.getName());
            if(productRequest.getDescription() != null)
                product.setDescription(productRequest.getDescription());
            if (productRequest.getPrice() != null)
                product.setPrice(productRequest.getPrice());
            if (productRequest.getAvailability() != null)
                product.setAvailability(productRequest.getAvailability());
            if (productRequest.getCategories() != null) {
                List<Category> categories = productRequest.getCategories().stream().map(c -> categoryRepository.findByName(c).orElse(null)).filter(Objects::nonNull).collect(Collectors.toList());
                productCategoryRepository.deleteCategoriesByProduct(product.getId());
                product.setCategories(categories);
            }
            product = productRepository.save(product);
            response = modelMapper.map(product, ProductResponse.class);
        }
        log.info("edit returning " + response);
        return response;
    }

    public boolean delete(Long id) {
        boolean success = false;
        Product product = productRepository.findById(id).orElse(null);
        if (product != null) {
            productCategoryRepository.deleteCategoriesByProduct(product.getId());
            productRepository.delete(product);
            success = true;
        }
        return success;
    }
}
