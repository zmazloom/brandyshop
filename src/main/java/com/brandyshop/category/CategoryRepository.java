package com.brandyshop.category;

import com.brandyshop.domain.data.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    @Query("from Category b where b.name = :name and b.removed = false")
    Category getCategoryByName(String name);

    @Query("from Category b where b.name = :name and b.id <> :categoryId and b.removed = false")
    Category getCategoryByName(Long categoryId, String name);

    @Query("from Category b where b.id = :categoryId and b.removed = false")
    Category getCategoryById(long categoryId);

    @Query("from Category b where b.removed = false order by b.id desc")
    List<Category> getAllCategories();

}