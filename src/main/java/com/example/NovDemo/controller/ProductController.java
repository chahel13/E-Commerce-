package com.example.NovDemo.controller;

import com.example.NovDemo.Exceptions.ProductNotFoundException;
import com.example.NovDemo.dto.ErrorDto;
import com.example.NovDemo.model.Category;
import com.example.NovDemo.model.Product;
import com.example.NovDemo.service.ProductService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.ResponseExtractor;

import java.util.List;

@RestController
public class ProductController {

    private ProductService productService;

    public ProductController(@Qualifier("selfProductService") ProductService productService) {
        this.productService = productService;
    }

    @PostMapping("/products")
    public Product createProduct(@RequestBody Product product)
    {
        Product postRequestResponse = productService.createProduct(product);
        return postRequestResponse;
    }

    @GetMapping("/products/{id}")
    public ResponseEntity <Product> getSingleProduct(@PathVariable("id") long productId) throws ProductNotFoundException
    {
       Product currentProduct = productService.getSingleProduct(productId);
       ResponseEntity<Product> res = new ResponseEntity<>(currentProduct, HttpStatus.OK);
       return res;

    }

    @GetMapping("/products")
    public List getAllProducts()
    {
        List<Product> res = productService.getAllProducts();
        return res;
    }

     @PutMapping("/products/{id}")
     public Product updateProduct(@PathVariable("id") long productId,@RequestBody Product product) throws ProductNotFoundException
      {
          Product updatedProduct = productService.updateProduct(productId, product);
          return updatedProduct;
      }

    @DeleteMapping("/products/{id}")
    public Product deleteProduct(@PathVariable("id") long productId) throws ProductNotFoundException
    {
       return productService.deleteProduct(productId);
    }


    @GetMapping("/category")
    public List<Category> getAllCategory()
    {
        List<Category> catres = productService.getAllCategory();
        return catres;
    }

    @GetMapping("/category/{id}")
    public List<Product> getProductsByCategory(@PathVariable("id") Long categoryId)
    {
        List<Product> categoryproduct = productService.getProductsByCategory(categoryId);
        return categoryproduct;
    }


    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<ErrorDto> handleProductNotFoundException(Exception e)
    {
        ErrorDto errorDto = new ErrorDto();
        errorDto.setMessage(e.getMessage());

        return new ResponseEntity<>(errorDto, HttpStatus.NOT_FOUND);
    }

}
