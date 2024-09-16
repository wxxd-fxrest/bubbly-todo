package com.wxxdfxrest.bubbly_todo.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.wxxdfxrest.bubbly_todo.dto.TodoDTO;
import com.wxxdfxrest.bubbly_todo.service.TodoService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class BubblyTodoController {
    private final TodoService todoService;

    // Add ToDo
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

    // 개인 ToDo List
    @GetMapping("/bubbly-todo/list/{todoUser}") // 사용자 이메일을 경로 변수로 추가
    public ResponseEntity<List<TodoDTO>> findAllTodo(@PathVariable(name = "todoUser") String todoUser) {
        List<TodoDTO> todoDTOList = todoService.findTodoByUserEmail(todoUser); // 사용자 이메일로 투두 목록 가져오기
        return ResponseEntity.ok(todoDTOList); // 200 OK와 함께 투두 리스트 반환
    }

    // ToDo Update
    @PostMapping("/bubbly-todo/update/{todoId}")
    public ResponseEntity<String> updateTodo(@PathVariable(name = "todoId") Long todoId, 
                                              @RequestBody TodoDTO todoDTO, 
                                              BindingResult bindingResult) {
        System.out.println("Received TodoDTO: " + todoDTO); // 디버깅 로그 추가
    
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body("입력 값에 오류가 있습니다."); // 오류 처리
        }
    
        todoDTO.setTodoId(todoId); // ID 설정
        todoService.updateTodo(todoDTO); // 서비스에서 업데이트 메소드 호출
    
        return ResponseEntity.ok("투두 항목이 성공적으로 업데이트되었습니다."); // 200 OK와 메시지 반환
    }
    

    // ToDo Delete
    @DeleteMapping("/bubbly-todo/delete/{id}")
    public ResponseEntity<String> deleteTodo(@PathVariable("id") Long id) {
        boolean isDeleted = todoService.deleteTodoById(id);
        
        if (isDeleted) {
            return ResponseEntity.ok("투두 항목이 삭제되었습니다."); // 200 OK와 메시지 반환
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("삭제할 수 없는 투두 항목입니다."); // 404 NOT FOUND와 메시지 반환
        }
    }
}
