package com.example.NovDemo.repositories;

import com.example.NovDemo.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product,Long> {
      Product save(Product product);

      Product findByTitle(String title);
      List<Product> findAll();



      @Query(value = "SELECT * FROM product p WHERE p.category_id = :categoryId",nativeQuery = true )
      List<Product> findByCategoryId(@Param("categoryId") Long categoryId);


     // @Query("SELECT p.title as title, p.description as description FROM Product p WHERE p.category.id = :categoryId")
     // List<Product> findByCategoryId(@Param("categoryId") Long categoryId);
}
