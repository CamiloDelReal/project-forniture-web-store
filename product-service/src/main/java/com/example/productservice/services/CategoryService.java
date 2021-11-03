package com.example.productservice.services;

import com.example.productservice.dtos.CategoryRequest;
import com.example.productservice.dtos.CategoryResponse;
import com.example.productservice.entities.Category;
import com.example.productservice.repositories.CategoryRepository;
import com.example.productservice.repositories.ProductCategoryRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;
    private final ProductCategoryRepository productCategoryRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public CategoryService(CategoryRepository categoryRepository, ProductCategoryRepository productCategoryRepository,
                           ModelMapper modelMapper) {
        this.categoryRepository = categoryRepository;
        this.productCategoryRepository = productCategoryRepository;
        this.modelMapper = modelMapper;
    }

    public boolean exists(Long id) {
        return categoryRepository.existsById(id);
    }

    public List<CategoryResponse> getAll() {
        List<CategoryResponse> response = null;
        List<Category> categories = categoryRepository.findAll();
        if (categories != null && !categories.isEmpty()) {
            response = categories.stream().map(c -> modelMapper.map(c, CategoryResponse.class)).collect(Collectors.toList());
        } else {
            response = new ArrayList<>();
        }
        return response;
    }

    public CategoryResponse getById(Long id) {
        CategoryResponse response = null;
        Category category = categoryRepository.findById(id).orElse(null);
        if (category != null) {
            response = modelMapper.map(category, CategoryResponse.class);
        }
        return response;
    }

    public CategoryResponse create(CategoryRequest categoryRequest) {
        CategoryResponse response = null;
        Category duplicity = categoryRepository.findByName(categoryRequest.getName()).orElse(null);
        if (duplicity == null) {
            Category category = modelMapper.map(categoryRequest, Category.class);
            category = categoryRepository.save(category);
            response = modelMapper.map(category, CategoryResponse.class);
        }
        return response;
    }

    public CategoryResponse edit(Long id, CategoryRequest categoryRequest) {
        CategoryResponse response = null;
        Category category = categoryRepository.findById(id).orElse(null);
        // Skipping name duplicity
        if (category != null) {
            category.setName(categoryRequest.getName());
            category = categoryRepository.save(category);
            response = modelMapper.map(category, CategoryResponse.class);
        }
        return response;
    }

    public boolean delete(Long id) {
        boolean success = false;
        Category category = categoryRepository.findById(id).orElse(null);
        if (category != null) {
            productCategoryRepository.deleteProductsInCategory(category.getId());
            categoryRepository.delete(category);
            success = true;
        }
        return success;
    }
}
