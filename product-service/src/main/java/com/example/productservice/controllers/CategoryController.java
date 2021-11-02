package com.example.productservice.controllers;

import com.example.productservice.dtos.CategoryRequest;
import com.example.productservice.dtos.CategoryResponse;
import com.example.productservice.services.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(path = "/categories")
@Slf4j
public class CategoryController {
    private final CategoryService categoryService;

    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<CategoryResponse>> getAllCategories() {
        log.info("getAllCategories");
        List<CategoryResponse> categories = categoryService.getAll();
        ResponseEntity<List<CategoryResponse>> response = new ResponseEntity<>(categories, HttpStatus.OK);
        return response;
    }

    @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CategoryResponse> getCategoryById(@PathVariable("id") Long id) {
        ResponseEntity<CategoryResponse> response = null;
        CategoryResponse category = categoryService.getById(id);
        if(category != null) {
            response = new ResponseEntity<>(category, HttpStatus.OK);
        } else {
            response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return response;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("isAuthenticated() and hasRole('ADMIN')")
    public ResponseEntity<CategoryResponse> createCategory(@Valid @RequestBody CategoryRequest categoryRequest) {
        ResponseEntity<CategoryResponse> response = null;
        CategoryResponse category = categoryService.create(categoryRequest);
        if(category != null) {
            response = new ResponseEntity<>(category, HttpStatus.CREATED);
        } else {
            response = new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        return response;
    }

    @PutMapping(path = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("isAuthenticated() and hasRole('ADMIN')")
    public ResponseEntity<CategoryResponse> editCategory(@PathVariable("id") Long id, @Valid @RequestBody CategoryRequest categoryRequest) {
        ResponseEntity<CategoryResponse> response = null;
        CategoryResponse category = categoryService.edit(id, categoryRequest);
        if(category != null) {
            response = new ResponseEntity<>(category, HttpStatus.OK);
        } else {
            response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return response;
    }

    @DeleteMapping(path = "/{id}")
    @PreAuthorize("isAuthenticated() and hasRole('ADMIN')")
    public ResponseEntity<Void> deleteCategory(@PathVariable("id") Long id) {
        ResponseEntity<Void> response = null;
        if(categoryService.delete(id)) {
            response = new ResponseEntity<>(HttpStatus.OK);
        } else {
            response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return response;
    }
}
