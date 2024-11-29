package com.example.NovDemo.service;

import com.example.NovDemo.Exceptions.ProductNotFoundException;
import com.example.NovDemo.model.Category;
import com.example.NovDemo.model.Product;
import com.example.NovDemo.repositories.CategoryRepository;
import com.example.NovDemo.repositories.ProductRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service("selfProductService")
public class SelfProductService implements ProductService {
     private ProductRepository productRepository;
     private CategoryRepository categoryRepository;

    public SelfProductService(ProductRepository productRepository, CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public Product getSingleProduct(Long productId) throws ProductNotFoundException {
        Optional<Product> p = productRepository.findById(productId);
        if(p.isPresent())
        {
            return p.get();
        }
        else throw new ProductNotFoundException("Product not found");
    }

    @Override
    public List<Product> getAllProducts() {
             return productRepository.findAll();
    }

    @Override
    public Product createProduct(Product product) {
        Category cat = categoryRepository.findByTitle(product.getCategory().getTitle());
        if (cat == null)
        {
            Category newCat = new Category();
            newCat.setTitle(product.getCategory().getTitle());
            Category newCar = categoryRepository.save(newCat);
            product.setCategory(newCar);
        }
        else
        {
             product.setCategory(cat);
        }

        Product savedProduct = productRepository.save(product);
        return savedProduct;

    }

    @Override
    public List<Category> getAllCategory()
    {
        return categoryRepository.findAll();
    }

    @Override
    public List<Product> getProductsByCategory(Long categoryId) {
             return productRepository.findByCategoryId(categoryId);
    }

    @Override
    public Product updateProduct(long productId, Product product) throws ProductNotFoundException{
      Optional <Product> p = productRepository.findById(productId);
      Product exProduct = p.get();
      if(p.isPresent()) {
          if (product.getCategory() != null) {
              Category updatedCat = product.getCategory();

              if (updatedCat.getId() == null) {
                  categoryRepository.save(updatedCat);
                  exProduct.setCategory(updatedCat);
              }
          }
          exProduct.setTitle(product.getTitle());
          exProduct.setDescription(product.getDescription());
          exProduct.setPrice(product.getPrice());
          return productRepository.save(exProduct);
      }
     else throw new ProductNotFoundException("Product not found");
    }

    @Override
    public Product deleteProduct(Long productId) throws ProductNotFoundException {
        if(productRepository.existsById(productId))
        {
            productRepository.deleteById(productId);
            return null;

        }
        else throw new ProductNotFoundException("Product not found");
    }

}
