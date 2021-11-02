package com.example.productservice.controllers;

import com.example.productservice.dtos.ProductRequest;
import com.example.productservice.dtos.ProductResponse;
import com.example.productservice.services.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(path = "/products")
@Slf4j
public class ProductController {
    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ProductResponse>> getAllProducts() {
        log.info("getAllProducts");
        List<ProductResponse> products = productService.getAll();
        ResponseEntity<List<ProductResponse>> response = new ResponseEntity<>(products, HttpStatus.OK);
        return response;
    }

    @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ProductResponse> getProductById(@PathVariable("id") Long id) {
        ResponseEntity<ProductResponse> response = null;
        ProductResponse product = productService.getById(id);
        if(product != null) {
            response = new ResponseEntity<>(product, HttpStatus.OK);
        } else {
            response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return response;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ProductResponse> createProduct(@Valid @RequestBody ProductRequest productRequest) {
        ResponseEntity<ProductResponse> response = null;
        ProductResponse product = productService.create(productRequest);
        if(product != null) {
            response = new ResponseEntity<>(product, HttpStatus.CREATED);
        }
         else {
             response = new ResponseEntity<>(HttpStatus.CONFLICT);
        }
         return response;
    }

    @PutMapping(path = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ProductResponse> editProduct(@PathVariable("id") Long id, @Valid @RequestBody ProductRequest productRequest) {
        ResponseEntity<ProductResponse> response = null;
        ProductResponse product = productService.edit(id, productRequest);
        if(product != null) {
            response = new ResponseEntity<>(product, HttpStatus.OK);
        } else {
            response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return response;
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable("id") Long id) {
        ResponseEntity<Void> response = null;
        if(productService.delete(id)) {
            response = new ResponseEntity<>(HttpStatus.OK);
        } else {
            response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return response;
    }
}
