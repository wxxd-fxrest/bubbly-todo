package com.wxxdfxrest.bubbly_todo.dto;

import com.wxxdfxrest.bubbly_todo.entity.TodoEntity;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor 
@ToString
public class TodoDTO {
    private Long todoId;
    private String todo;
    private String todoDate;
    private boolean todoState;
    private String todoUser;
    // Category
    private Long todoCategoryId; 
    private String todoCategory; 
    private String todoCategoryColor; 
  
    public static TodoDTO toTodoDTO(TodoEntity todoEntity) {
        TodoDTO todoDTO = new TodoDTO();
        todoDTO.setTodoId(todoEntity.getTodoId()); // ID 매핑
        todoDTO.setTodo(todoEntity.getTodo()); // 투두 내용 매핑
        todoDTO.setTodoDate(todoEntity.getTodoDate()); // 날짜 매핑
        todoDTO.setTodoState(todoEntity.isTodoState()); // 상태 매핑
        todoDTO.setTodoUser(todoEntity.getTodoUser()); // 사용자 매핑
        todoDTO.setTodoCategoryId(todoEntity.getTodoCategoryId()); // 카테고리 ID 매핑
        todoDTO.setTodoCategory(todoEntity.getTodoCategory()); // 카테고리 매핑
        todoDTO.setTodoCategoryColor(todoEntity.getTodoCategoryColor());
        return todoDTO;
    }    
}
