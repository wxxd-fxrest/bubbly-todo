package com.wxxdfxrest.bubbly_todo.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import com.wxxdfxrest.bubbly_todo.dto.CategoryDTO;
import com.wxxdfxrest.bubbly_todo.dto.TodoDTO;
import com.wxxdfxrest.bubbly_todo.service.CategoryService;
import com.wxxdfxrest.bubbly_todo.service.TodoService;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor
public class BubblyTodoController {
    private final TodoService todoService;
    private final CategoryService categoryService;

    // MARK: - ToDo
    @GetMapping("/bubbly-todo") 
    public String Home() {
        return "home";
    }

    @GetMapping("/bubbly-todo/addTodo") // 투두 추가 페이지로 이동
    public String showAddTodoPage(Model model) {
        List<CategoryDTO> categoryDTOList = categoryService.findByCategoryAll();
        model.addAttribute("categoryList", categoryDTOList); // 카테고리 목록 추가
        return "addTodo"; // addTodo.html로 이동
    }

    @PostMapping("/bubbly-todo/addTodo") // 투두 항목 추가
    public String addTodo(@ModelAttribute TodoDTO todoDTO) {
        System.out.println("todoDTO = " + todoDTO); // 디버깅용 출력
        todoService.addTodo(todoDTO); // 투두 항목 저장
        return "redirect:/bubbly-todo/list"; // 추가 후 목록 페이지로 리다이렉트
    }

    @GetMapping("/bubbly-todo/list")
    public String findAllTodo(Model model) {
        List<TodoDTO> todoDTOList = todoService.findTodoAll();
        model.addAttribute("todos", todoDTOList);
        return "todoList";
    }
    
    @GetMapping("/bubbly-todo/list/{id}")
    public String findById(@PathVariable(name = "id") Long id, Model model) {
        System.out.println("Fetching ID: " + id); // 요청된 ID 출력

        TodoDTO todoDTO = todoService.findByTodo(id);

        if (todoDTO == null) {
            System.out.println("User not found"); // 사용자 없음 출력
            return "error"; // 에러 페이지로 리다이렉트
        }

        model.addAttribute("todo", todoDTO);
        return "todoDetail"; // 사용자 정보가 있는 detail 페이지로 이동
    }

    @GetMapping("/bubbly-todo/update/{id}")
    public String updateTodoForm(@PathVariable(name = "id") Long id, Model model) {
        TodoDTO todoDTO = todoService.findByTodo(id); // ID로 투두 항목 찾기
        if (todoDTO == null) {
            return "error"; // 투두 항목이 없을 경우 에러 페이지로 이동
        }
        
        model.addAttribute("todo", todoDTO); // 모델에 투두 항목 추가
        return "todoUpdate"; // 수정 폼 템플릿으로 이동
    }

    @PostMapping("/bubbly-todo/update/{id}")
    public String updateTodo(@PathVariable(name = "id") Long id, @ModelAttribute TodoDTO todoDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "error"; // 오류 처리
        }
        
        todoDTO.setTodoId(id); // ID 설정
        todoService.updateTodo(todoDTO); // 서비스에서 업데이트 메소드 호출
    
        return "redirect:/bubbly-todo/list"; // 목록 페이지로 리다이렉트
    }

    @GetMapping("/bubbly-todo/delete/{id}")
    public String deleteTodo(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
        boolean isDeleted = todoService.deleteTodoById(id);
        
        if (isDeleted) {
            redirectAttributes.addFlashAttribute("message", "투두 항목이 삭제되었습니다.");
        } else {
            redirectAttributes.addFlashAttribute("error", "삭제할 수 없는 투두 항목입니다.");
        }

        return "redirect:/todoList"; // 삭제 후 투두 목록으로 리다이렉트
    }



    // MARK: - Category
    @GetMapping("/bubbly-todo/category") 
    public String goCategory(Model model) {
        List<CategoryDTO> categoryDTOList = categoryService.findByCategoryAll();
        model.addAttribute("categoryList", categoryDTOList);
        return "category";
    }

    @PostMapping("/bubbly-todo/category")
    public String addCategory(@ModelAttribute CategoryDTO categoryDTO) {
        System.out.println("categoryDTO = " + categoryDTO);
        categoryService.addCategory(categoryDTO);
        return "category";
    }

    @GetMapping("/bubbly-todo/category/delete/{id}")
    public String deleteCategofy(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
        boolean isDeleted = categoryService.deleteCategoryById(id);
        
        if (isDeleted) {
            redirectAttributes.addFlashAttribute("message", "카테고리 항목이 삭제되었습니다.");
        } else {
            redirectAttributes.addFlashAttribute("error", "삭제할 수 없는 카테고리 항목입니다.");
        }

        return "redirect:/category"; // 삭제 후 투두 목록으로 리다이렉트
    }
}
