package com.example.NovDemo.dto;

import com.example.NovDemo.model.Category;
import com.example.NovDemo.model.Product;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class FakeStoreProductDto {
    private Long id;
    private String title;
    private String description;
    private double price;
    private String image;
    private String category;

    public Product toProduct()
    {
        Product p = new Product();
        p.setId(id);
        p.setTitle(title);
        p.setDescription(description);
        p.setPrice(price);
        p.setImageurl(image);

        Category cat  = new Category();
        cat.setTitle(category);
        p.setCategory(cat);
        return p;
    }


    public Product toCategory() {
        Product p = new Product();
        Category category = new Category();
        category.setId(id);
        category.setTitle(title);
        return p;
    }


}