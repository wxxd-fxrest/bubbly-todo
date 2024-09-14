package com.wxxdfxrest.bubbly_todo.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<String> addTodo(@RequestBody TodoDTO todoDTO) {
        System.out.println("todoDTO = " + todoDTO); // 디버깅용 출력
    
        boolean todoSaveSuccess = todoService.addTodo(todoDTO); // 서비스 호출
        
        if (todoSaveSuccess) {
            return ResponseEntity.status(HttpStatus.CREATED).body("할 일 추가 성공");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("할 일 추가 실패");
        }
    }
    
    @GetMapping("/bubbly-todo/list")
    public ResponseEntity<List<TodoDTO>> findAllTodo(Model model) {
        List<TodoDTO> todoDTOList = todoService.findTodoAll();
        return ResponseEntity.ok(todoDTOList); // 200 OK와 함께 카테고리 리스트 반환
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
    public ResponseEntity<String> updateTodo(@PathVariable(name = "id") Long id, @RequestBody TodoDTO todoDTO, BindingResult bindingResult) {
        System.out.println("Received TodoDTO: " + todoDTO); // 디버깅 로그 추가

        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body("입력 값에 오류가 있습니다."); // 오류 처리
        }

        todoDTO.setTodoId(id); // ID 설정
        todoService.updateTodo(todoDTO); // 서비스에서 업데이트 메소드 호출

        return ResponseEntity.ok("투두 항목이 성공적으로 업데이트되었습니다."); // 200 OK와 메시지 반환
    }

    @GetMapping("/bubbly-todo/delete/{id}")
    public ResponseEntity<String> deleteTodo(@PathVariable("id") Long id) {
        boolean isDeleted = todoService.deleteTodoById(id);
        
        if (isDeleted) {
            return ResponseEntity.ok("투두 항목이 삭제되었습니다."); // 200 OK와 메시지 반환
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("삭제할 수 없는 투두 항목입니다."); // 404 NOT FOUND와 메시지 반환
        }
    }



    // MARK: - Category
    @GetMapping("/bubbly-todo/category") 
    public ResponseEntity<List<CategoryDTO>> goCategory() {
        List<CategoryDTO> categoryDTOList = categoryService.findByCategoryAll();
        return ResponseEntity.ok(categoryDTOList); // 200 OK와 함께 카테고리 리스트 반환
    }

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
