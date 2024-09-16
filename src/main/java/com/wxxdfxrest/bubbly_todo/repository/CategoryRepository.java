package com.wxxdfxrest.bubbly_todo.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.wxxdfxrest.bubbly_todo.entity.CategoryEntity;

public interface CategoryRepository extends JpaRepository<CategoryEntity, Long> {
    Optional<CategoryEntity> findByCategory(String category);
    List<CategoryEntity> findByCategoryUser(String categoryUser); // UserEmail로 카테고리 찾기
}
