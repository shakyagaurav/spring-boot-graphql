package com.graphql.service;

import com.graphql.entity.Product;
import com.graphql.exception.CustomGraphQLException;
import com.graphql.repository.ProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class ProductService {

    private final ProductRepository productRepository;

    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public String createProduct(Product product) {
        LocalDateTime localDateTime=LocalDateTime.now();
        product.setCreatedDate(localDateTime);
        product.setUpdatedDate(localDateTime);
        productRepository.save(product);
        return "Product Created Successfully.";
    }

    public List<Product> findAllProducts(int page) {
        int pageSize = 2;
        Page<Product> result=productRepository.findAll(PageRequest.of(page, pageSize));
        if(result.getContent().isEmpty()){
            log.error("Product not found while calling find all products for page: {}", page);
            throw new CustomGraphQLException(400, String.format("No product found while calling find all products for page: %s", page));
        }
        return result.getContent();
    }

    public Product findProductById(String id) {
        Optional<Product> product=productRepository.findById(id);
        if(product.isEmpty()){
            log.error("Product not found while finding product by Id: {}", id);
            throw new CustomGraphQLException(400, String.format("Product not found while finding product by Id: %s", id));
        }
        log.info("Found product details by Id: {}", product.get().getName());
        return product.get();
    }

    public String updateProduct(String id, Product updatedProduct) {
        Optional<Product> product=productRepository.findById(id);
        if(product.isEmpty()){
            throw new CustomGraphQLException(400, String.format("An error occurred while calling updateProduct: %s", id));
        }
        product.get().setName(updatedProduct.getName());
        product.get().setDescription(updatedProduct.getDescription());
        product.get().setPrice(updatedProduct.getPrice());
        product.get().setCategory(updatedProduct.getCategory());
        product.get().setQuantity(updatedProduct.getQuantity());
        product.get().setInStock(updatedProduct.isInStock());
        product.get().setUpdatedDate(LocalDateTime.now());
        productRepository.save(product.get());
        return "Product updated successfully.";
    }

    public String deleteProduct(String id) {
       if(productRepository.findById(id).isEmpty()){
           throw new CustomGraphQLException(400, String.format("An error occurred while calling deleteProduct: %s", id));
       }
       productRepository.deleteById(id);
       return "Product deleted successfully.";
    }
}
