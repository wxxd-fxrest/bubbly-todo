package com.wxxdfxrest.bubbly_todo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.wxxdfxrest.bubbly_todo.dto.CategoryDTO;
import com.wxxdfxrest.bubbly_todo.dto.TodoDTO;
import com.wxxdfxrest.bubbly_todo.entity.TodoEntity;
import com.wxxdfxrest.bubbly_todo.repository.TodoRepository;

import lombok.RequiredArgsConstructor;
import java.util.stream.Collectors; // Collectors 임포트

@Service
@RequiredArgsConstructor
public class TodoService {
    private final TodoRepository todoRepository;
    private final CategoryService categoryService;

    // Email이 동일한 ToDo Data 가져오기
    public List<TodoDTO> findTodoByUserEmailAndDate(String todoUser, String todoDate) {
        List<TodoEntity> todoEntities = todoRepository.findByTodoUserAndTodoDate(todoUser, todoDate); // 이메일과 날짜로 검색
        return todoEntities.stream()
            .map(TodoDTO::toTodoDTO) // DTO로 변환
            .collect(Collectors.toList()); // 리스트로 수집
    }
    
    // Add ToDo
    public boolean addTodo(TodoDTO todoDTO) {
        if (todoDTO.getTodoCategoryId() == null) {
            throw new IllegalArgumentException("todoCategoryId must not be null");
        }
    
        // 카테고리 정보 조회
        CategoryDTO categoryDTO = categoryService.findCategoryById(todoDTO.getTodoCategoryId());
        
        if (categoryDTO != null) {
            todoDTO.setTodoCategory(categoryDTO.getCategory());
            todoDTO.setTodoCategoryColor(categoryDTO.getCategoryColor());
        }
    
        // 새로운 투두 항목 저장
        TodoEntity todoEntity = TodoEntity.toTodoEntity(todoDTO);
        todoRepository.save(todoEntity); // 투두 항목 저장
        return true; // 성공적으로 저장됨
    }  

    // 각각의 ToDo 찾기
    public TodoDTO findByTodo(Long id) {
        System.out.println("Received ID: " + id); // ID 출력
    
        Optional<TodoEntity> optionalTodoEntity = todoRepository.findByTodoId(id);
        
        if (optionalTodoEntity.isPresent()) {
            TodoDTO todoDTO = TodoDTO.toTodoDTO(optionalTodoEntity.get());
            System.out.println("User found: " + todoDTO); // 조회된 사용자 정보 출력
            return todoDTO;
        } else {
            System.out.println("No user found with ID: " + id); // 사용자 없음 출력
            return null;
        }
    }

    // ToDo Update
    public void updateTodo(TodoDTO todoDTO) {
        Optional<TodoEntity> optionalTodoEntity = todoRepository.findByTodoId(todoDTO.getTodoId());
        
        if (optionalTodoEntity.isPresent()) {
            TodoEntity todoEntity = optionalTodoEntity.get(); // 값 가져오기
            todoEntity.setTodo(todoDTO.getTodo());
            todoEntity.setTodoDate(todoDTO.getTodoDate());
            todoEntity.setTodoState(todoDTO.isTodoState());
            todoEntity.setTodoCategory(todoDTO.getTodoCategory());
            todoRepository.save(todoEntity);
        } else {
            System.out.println("Todo not found with ID: " + todoDTO.getTodoId()); // 디버깅
        }
    }

    // ToDo Delete
    public boolean deleteTodoById(Long id) {
        if (todoRepository.existsById(id)) {
            todoRepository.deleteById(id);
            return true; // 삭제 성공
        }
        return false; // ID가 존재하지 않음
    }
}
