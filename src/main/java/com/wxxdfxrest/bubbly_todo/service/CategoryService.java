package com.wxxdfxrest.bubbly_todo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.wxxdfxrest.bubbly_todo.dto.CategoryDTO;
import com.wxxdfxrest.bubbly_todo.entity.CategoryEntity;
import com.wxxdfxrest.bubbly_todo.repository.CategoryRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;

    // Email이 동일한 Category Data 가져오기
    public List<CategoryEntity> findCategoriesByUserEmail(String categoryUser) {
        return categoryRepository.findByCategoryUser(categoryUser); // 이메일로 카테고리 찾기
    }

    // Add Category
    public boolean addCategory(CategoryDTO categoryDTO) {
        // 카테고리 이름 중복 확인
        if (categoryRepository.findByCategory(categoryDTO.getCategory()).isPresent()) {
            return false; // 이미 존재하는 카테고리 이름
        }
        
        // 새로운 카테고리 저장
        CategoryEntity categoryEntity = CategoryEntity.toCategoryEntity(categoryDTO);
        categoryRepository.save(categoryEntity);
        return true; // 카테고리 저장 성공
    }

    // Category Delete
    public boolean deleteCategoryByName(String category) {
        // 카테고리 이름으로 카테고리 조회
        Optional<CategoryEntity> categoryEntityOptional = categoryRepository.findByCategory(category);
        
        if (categoryEntityOptional.isPresent()) {
            categoryRepository.delete(categoryEntityOptional.get()); // 존재하는 경우 삭제
            return true; // 삭제 성공
        }
        return false; // 카테고리가 존재하지 않음
    }

    // Category <-> ToDo
    public CategoryDTO findCategoryById(Long categoryId) {
        return categoryRepository.findById(categoryId)
                .map(CategoryDTO::toCategoryDTO)
                .orElse(null); // 카테고리가 없으면 null 반환
    }    
}
