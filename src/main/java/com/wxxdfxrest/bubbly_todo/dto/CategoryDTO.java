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

    public static CategoryDTO toCategoryDTO(CategoryEntity categoryEntity) {
        CategoryDTO categoryDTO = new CategoryDTO();
        categoryDTO.setCategoryId(categoryEntity.getCategoryId());
        categoryDTO.setCategory(categoryEntity.getCategory());
        categoryDTO.setCategoryColor(categoryEntity.getCategoryColor());
        return categoryDTO;
    }
}
