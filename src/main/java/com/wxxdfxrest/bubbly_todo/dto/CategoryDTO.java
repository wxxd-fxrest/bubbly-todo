package com.wxxdfxrest.bubbly_todo.dto;


import com.wxxdfxrest.bubbly_todo.entity.CategoryEntity;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class CategoryDTO {
    private Long categoryId;
    private String category;
    private String categoryColor;
    private String categoryUser;

    public static CategoryDTO toCategoryDTO(CategoryEntity categoryEntity) {
        CategoryDTO categoryDTO = new CategoryDTO();
        categoryDTO.setCategoryId(categoryEntity.getCategoryId());
        categoryDTO.setCategory(categoryEntity.getCategory());
        categoryDTO.setCategoryColor(categoryEntity.getCategoryColor());
        categoryDTO.setCategoryUser(categoryEntity.getCategoryUser()); // 수정된 부분
        return categoryDTO;
    }
}
