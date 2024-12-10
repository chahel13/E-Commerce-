package com.example.NovDemo.service;

import com.example.NovDemo.Exceptions.ProductNotFoundException;
import com.example.NovDemo.dto.FakeStoreCategoryDto;
import com.example.NovDemo.dto.FakeStoreProductDto;
import com.example.NovDemo.model.Category;
import com.example.NovDemo.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
@Service("fakeProductService")
public class FakeStoreProductService implements ProductService{

    private RestTemplate restTemplate;
    private RedisTemplate redisTemplate;

    public FakeStoreProductService(RestTemplate restTemplate, RedisTemplate redisTemplate) {
        this.restTemplate = restTemplate;
        this.redisTemplate = redisTemplate;
    }

    @Override
    public Product getSingleProduct(Long productId) throws ProductNotFoundException {
        Product productFromRedis = (Product) redisTemplate.opsForHash().get("PRODUCT","PRODUCT_"+productId);
        if (productFromRedis != null) {
            return productFromRedis;
        }

        FakeStoreProductDto fakestoreProductDto =  restTemplate.getForObject("https://fakestoreapi.com/products/"
                + productId,FakeStoreProductDto.class);


        if(fakestoreProductDto == null)
        {
            throw new ProductNotFoundException("Product not found with ID" + productId);
        }
        redisTemplate.opsForHash().put("PRODUCT","PRODUCT_"+productId,fakestoreProductDto.toProduct());
        return fakestoreProductDto.toProduct();

    }



    @Override
    public Page<Product> getAllProducts(int pageNumber,int pageSize) {

        List<Product> products = new ArrayList<>();
        FakeStoreProductDto[] res = restTemplate.getForObject("https://fakestoreapi.com/products/", FakeStoreProductDto[].class);

        for(FakeStoreProductDto fs : res)
        {
           products.add(fs.toProduct());
        }

        return null;

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
        List<Category> categoryList = new ArrayList<>();
        FakeStoreCategoryDto[] cres = restTemplate.getForObject("https://fakestoreapi.com/products/categories", FakeStoreCategoryDto[].class);
        for(FakeStoreCategoryDto fsc : cres)
        {
            categoryList.add(fsc.toCategory());
        }
        return categoryList;
    }

    @Override
    public List<Product> getProductsByCategory(Long categoryId) {
        return null;
    }

    @Override
    public Product updateProduct(Product product) throws ProductNotFoundException {
        //updating existing product
        Product exProduct = getSingleProduct(product.getId());
        exProduct.setTitle(product.getTitle());
        exProduct.setDescription(product.getDescription());
        exProduct.setPrice(product.getPrice());
        exProduct.setImageurl(product.getImageurl());
        exProduct.setCategory(product.getCategory());

     // to send in request body
       FakeStoreProductDto fakeStoreProductDto = new FakeStoreProductDto();
       fakeStoreProductDto.setId(product.getId());
       fakeStoreProductDto.setTitle(product.getTitle());
       fakeStoreProductDto.setDescription(product.getDescription());
       fakeStoreProductDto.setPrice(product.getPrice());
       fakeStoreProductDto.setImage(product.getImageurl());
       fakeStoreProductDto.setCategory(product.getCategory().getTitle());


        restTemplate.put("https://fakestoreapi.com/products/" + product.getId(),exProduct, FakeStoreProductDto.class);
        return product;
    }


    @Override
    public Product deleteProduct(Long productId) throws ProductNotFoundException {
        // need to check
        if (restTemplate.getForObject("https://fakestoreapi.com/products/" + productId, FakeStoreProductDto.class) != null)
        {
            restTemplate.delete("https://fakestoreapi.com/products/" + productId);
            System.out.println(productId + "Deleted");
            return null;
        }
        else throw new ProductNotFoundException("Product not found with ID" + productId);
    }

}

