package com.project.ecommerce_backend.repositry;

import com.project.ecommerce_backend.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category,Long> {


    public Category findByName(String name);

    @Query("SELECT c FROM Category c where c.name=:name AND c.parentCategory.name=:parentCategoryName")
    public Category findByNameAndParent(@Param("name")String name,@Param("parentCategoryName")String parentCategoryName);

}
