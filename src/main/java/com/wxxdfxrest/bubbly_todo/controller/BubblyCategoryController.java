package com.wxxdfxrest.bubbly_todo.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.wxxdfxrest.bubbly_todo.dto.CategoryDTO;
import com.wxxdfxrest.bubbly_todo.entity.CategoryEntity;
import com.wxxdfxrest.bubbly_todo.service.CategoryService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class BubblyCategoryController {
    private final CategoryService categoryService;

    // 개인 Category List 
    @GetMapping("/bubbly-todo/category/{categoryUser}") 
    public ResponseEntity<List<CategoryEntity>> getCategoriesByUserEmail(@PathVariable(name = "categoryUser") String categoryUser) {
        List<CategoryEntity> categories = categoryService.findCategoriesByUserEmail(categoryUser);
        if (categories.isEmpty()) {
            return ResponseEntity.notFound().build(); // 404 Not Found 반환
        }
        return ResponseEntity.ok(categories); // 카테고리 리스트 반환
    }

    // Add Category 
    @PostMapping("/bubbly-todo/category")
    public ResponseEntity<String> addCategory(@RequestBody CategoryDTO categoryDTO) {
        System.out.println("categoryDTO = " + categoryDTO);
        
        boolean saveSuccess = categoryService.addCategory(categoryDTO); // 카테고리 저장 성공 여부 확인
        
        if (saveSuccess) {
            return ResponseEntity.status(HttpStatus.CREATED).body("카테고리 저장 성공"); // 201 Created
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("카테고리 저장 실패: 이미 존재하는 카테고리"); // 400 Bad Request
        }
    }    

    // Category Delete
    @GetMapping("/bubbly-todo/category/delete/{category}")
    public String deleteCategory(@PathVariable(name = "category") String category, RedirectAttributes redirectAttributes) {
        boolean isDeleted = categoryService.deleteCategoryByName(category);
        
        if (isDeleted) {
            redirectAttributes.addFlashAttribute("message", "카테고리 항목이 삭제되었습니다.");
        } else {
            redirectAttributes.addFlashAttribute("error", "삭제할 수 없는 카테고리 항목입니다.");
        }

        return "redirect:/category"; // 삭제 후 투두 목록으로 리다이렉트
    }
}
