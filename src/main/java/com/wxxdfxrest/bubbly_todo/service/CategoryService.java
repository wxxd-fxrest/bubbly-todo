package com.wxxdfxrest.bubbly_todo.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.wxxdfxrest.bubbly_todo.dto.CategoryDTO;
import com.wxxdfxrest.bubbly_todo.entity.CategoryEntity;
import com.wxxdfxrest.bubbly_todo.repository.CategoryRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;

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

    public List<CategoryDTO> findByCategoryAll() {
        List<CategoryEntity> categoryEntityList = categoryRepository.findAll();
        List<CategoryDTO> categoryDTOList = new ArrayList<>();
        
        for (CategoryEntity categoryEntity : categoryEntityList) {
            categoryDTOList.add(CategoryDTO.toCategoryDTO(categoryEntity));
        }
        
        return categoryDTOList; // 올바른 반환
    }

    public boolean deleteCategoryById(Long id) {
        if (categoryRepository.existsById(id)) {
            categoryRepository.deleteById(id);
            return true; // 삭제 성공
        }
        return false; // ID가 존재하지 않음
    }

    public CategoryDTO findCategoryById(Long categoryId) {
        return categoryRepository.findById(categoryId)
                .map(CategoryDTO::toCategoryDTO)
                .orElse(null); // 카테고리가 없으면 null 반환
    }    
}
