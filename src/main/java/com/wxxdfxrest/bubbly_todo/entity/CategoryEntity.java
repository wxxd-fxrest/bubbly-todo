package com.wxxdfxrest.bubbly_todo.entity;

import com.wxxdfxrest.bubbly_todo.dto.CategoryDTO;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Table(name = "category_table")
public class CategoryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long categoryId;

    @Column
    private String category;

    @Column
    private String categoryColor;
    
    public static CategoryEntity toCategoryEntity(CategoryDTO categoryDTO) {
        CategoryEntity categoryEntity = new CategoryEntity();
        categoryEntity.setCategoryId(categoryDTO.getCategoryId());
        categoryEntity.setCategory(categoryDTO.getCategory());
        categoryEntity.setCategoryColor(categoryDTO.getCategoryColor());
        return categoryEntity;
    }
}
