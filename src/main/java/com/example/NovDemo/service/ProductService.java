package com.example.NovDemo.service;

import com.example.NovDemo.Exceptions.ProductNotFoundException;
import com.example.NovDemo.model.Category;
import com.example.NovDemo.model.Product;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ProductService {
          Product getSingleProduct(Long productId) throws ProductNotFoundException;
          Page<Product> getAllProducts(int pageNumber, int pageSize);
          Product createProduct(Product product);
          List<Category> getAllCategory();
          List<Product> getProductsByCategory(Long categoryId);
          Product updateProduct(Product product)throws ProductNotFoundException;
          Product deleteProduct(Long productId) throws ProductNotFoundException;
}
