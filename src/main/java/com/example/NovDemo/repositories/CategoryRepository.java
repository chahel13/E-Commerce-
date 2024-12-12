package com.example.NovDemo.repositories;

import com.example.NovDemo.model.Category;
import com.example.NovDemo.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    Category save(Category category);
    Category findByTitle(String title);
    List<Category> findAll();



    @Query(value = "SELECT * FROM product p WHERE p.categoryTitle = :categoryTitle",nativeQuery = true )
    List<Product> findByCategoryTitle(@Param("categoryTitle") String catgeoryTitle);



}
