package com.example.NovDemo.dto;

import com.example.NovDemo.model.Category;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class FakeStoreCategoryDto {
    private String categoryName;


    public Category toCategory() {
        Category category = new Category();
        category.setTitle(categoryName);
        return category;
    }
}
