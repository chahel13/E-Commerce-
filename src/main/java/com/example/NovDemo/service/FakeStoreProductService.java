package com.example.NovDemo.service;

import com.example.NovDemo.Exceptions.ProductNotFoundException;
import com.example.NovDemo.dto.FakeStoreProductDto;
import com.example.NovDemo.model.Category;
import com.example.NovDemo.model.Product;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
@Service("fakeProductService")
public class FakeStoreProductService implements ProductService{

    private RestTemplate restTemplate;

    public FakeStoreProductService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public Product getSingleProduct(Long productId) throws ProductNotFoundException {
        FakeStoreProductDto fakestoreProductDto =  restTemplate.getForObject("https://fakestoreapi.com/products/"
                + productId,FakeStoreProductDto.class);


        if(fakestoreProductDto == null)
        {
            throw new ProductNotFoundException("Product not found with ID" + productId);
        }

        return fakestoreProductDto.toProduct();

    }

    @Override
    public List<Product> getAllProducts() {
        List<Product> products = new ArrayList<>();
        FakeStoreProductDto[] res = restTemplate.getForObject("https://fakestoreapi.com/products/", FakeStoreProductDto[].class);

        for(FakeStoreProductDto fs : res)
        {
           products.add(fs.toProduct());
        }
        return products;
    }

    @Override
    public Product createProduct(Product product) {
        FakeStoreProductDto fs = new FakeStoreProductDto();
        fs.setId(product.getId());
        fs.setTitle(product.getTitle());
        fs.setDescription(product.getDescription());
        fs.setPrice(product.getPrice());
        fs.setImage(product.getImageurl());
        fs.setCategory(product.getCategory().getTitle());

        FakeStoreProductDto response = restTemplate.postForObject("https://fakestoreapi.com/products",
                fs,FakeStoreProductDto.class);

        return response.toProduct();
    }

    @Override
    public List<Category> getAllCategory() {
         /*  List<Category> categories = new ArrayList<>();
        FakeStoreProductDto[] cares = restTemplate.getForObject("https://fakestoreapi.com/categories", FakeStoreProductDto[].class);

        for(FakeStoreProductDto fs : cares)
        {
            categories.add(fs.toCategory());
        }
        return categories;*/
        return null;
    }

    @Override
    public List<Product> getProductsByCategory(Long categoryId) {
        return List.of();
    }

    @Override
    public Product updateProduct(long productId, Product product) throws ProductNotFoundException {
        return null;
    }

    @Override
    public Product deleteProduct(Long productId) throws ProductNotFoundException {
        if (restTemplate.getForObject("https://fakestoreapi.com/products/" + productId, FakeStoreProductDto.class) != null)
        {
            restTemplate.delete("https://fakestoreapi.com/products/" + productId,FakeStoreProductDto.class);
            return null;
        }
        else throw new ProductNotFoundException("Product not found with ID" + productId);
    }
/*
   @Override
   public Product updateProduct(Product product) {
       FakeStoreProductDto upres = restTemplate.put("https://fakestoreapi.com/products/");
    }

    @Override
    public Product deleteProduct(Long productId) {
        FakeStoreProductDto del = restTemplate.delete("https://fakestoreapi.com/products/" + productId,FakeStoreProductDto.class);
        return del.toProduct();
    }

*/
}

