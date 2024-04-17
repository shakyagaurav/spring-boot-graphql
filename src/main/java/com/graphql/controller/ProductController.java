package com.graphql.controller;

import com.graphql.entity.Product;
import com.graphql.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
@Slf4j
public class ProductController {

    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService){
        this.productService = productService;
    }

    @MutationMapping
    public String createProduct(@Argument Product product){
        log.info("Create product: {}", product.getName());
        return productService.createProduct(product);
    }
    @SchemaMapping(typeName = "Query", value="findAllProducts")
    public List<Product> findAllProducts(@Argument int page){
        log.info("Find all product for page: {}", page);
        return productService.findAllProducts(page);
    }

    @QueryMapping
    public Product findProductById(@Argument String id){
        log.info("Finding product by Id: {}", id);
        return productService.findProductById(id);
    }

    @MutationMapping
    public String updateProduct(@Argument String id, @Argument Product updatedProduct){
        log.info("Update product by Id: {}", id);
        return productService.updateProduct(id, updatedProduct);
    }

    @MutationMapping
    public String deleteProduct(@Argument String id){
        log.info("Delete product by Id: {}", id);
        return productService.deleteProduct(id);
    }
}
