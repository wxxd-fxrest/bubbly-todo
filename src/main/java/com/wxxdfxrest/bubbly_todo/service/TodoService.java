package com.wxxdfxrest.bubbly_todo.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.wxxdfxrest.bubbly_todo.dto.CategoryDTO;
import com.wxxdfxrest.bubbly_todo.dto.TodoDTO;
import com.wxxdfxrest.bubbly_todo.entity.TodoEntity;
import com.wxxdfxrest.bubbly_todo.repository.TodoRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TodoService {
    private final TodoRepository todoRepository;
    private final CategoryService categoryService;
    
    public boolean addTodo(TodoDTO todoDTO) {
        // 카테고리 정보 조회
        CategoryDTO categoryDTO = categoryService.findCategoryById(todoDTO.getTodoCategoryId());
        
        if (categoryDTO != null) {
            todoDTO.setTodoCategory(categoryDTO.getCategory()); // 카테고리 이름 설정
            todoDTO.setTodoCategoryColor(categoryDTO.getCategoryColor()); // 카테고리 색상 설정
        }
    
        // 새로운 투두 항목 저장
        TodoEntity todoEntity = TodoEntity.toTodoEntity(todoDTO);
        todoRepository.save(todoEntity); // 투두 항목 저장
        return true; // 성공적으로 저장됨
    }
    

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

    public boolean deleteTodoById(Long id) {
        if (todoRepository.existsById(id)) {
            todoRepository.deleteById(id);
            return true; // 삭제 성공
        }
        return false; // ID가 존재하지 않음
    }


    public void updateTodo(TodoDTO todoDTO) {
        Optional<TodoEntity> optionalTodoEntity = todoRepository.findByTodoId(todoDTO.getTodoId());
        
        if (optionalTodoEntity.isPresent()) {
            TodoEntity todoEntity = optionalTodoEntity.get(); // 값 가져오기
            todoEntity.setTodo(todoDTO.getTodo());
            todoEntity.setTodoDate(todoDTO.getTodoDate());
            todoEntity.setTodoState(todoDTO.isTodoState());
            todoRepository.save(todoEntity);
        }
    }

    public List<TodoDTO> findTodoAll() {
        List<TodoEntity> todoEntityList = todoRepository.findAll();
        List<TodoDTO> todoDTOList = new ArrayList<>();
        for (TodoEntity todoEntity: todoEntityList) {
            todoDTOList.add(TodoDTO.toTodoDTO(todoEntity));
        }
        return todoDTOList;
    }
}
