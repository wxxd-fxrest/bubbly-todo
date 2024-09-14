package com.wxxdfxrest.bubbly_todo.entity;

import jakarta.persistence.*;
import lombok.*;

import com.wxxdfxrest.bubbly_todo.dto.TodoDTO;

@Entity
@Getter
@Setter
@Table(name = "todo_table")
public class TodoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long todoId;

    @Column
    private String todo;

    @Column
    private String todoDate;

    @Column
    private boolean todoState;

    @Column
    private String todoUser;

    @Column
    private Long todoCategoryId;

    @Column
    private String todoCategory; 

    @Column
    private String todoCategoryColor; 
 
    public static TodoEntity toTodoEntity(TodoDTO todoDTO) {
        TodoEntity todoEntity = new TodoEntity();
        todoEntity.setTodo(todoDTO.getTodo());
        todoEntity.setTodoDate(todoDTO.getTodoDate());
        todoEntity.setTodoState(todoDTO.isTodoState());
        todoEntity.setTodoUser(todoDTO.getTodoUser());
        todoEntity.setTodoCategoryId(todoDTO.getTodoCategoryId());
        todoEntity.setTodoCategory(todoDTO.getTodoCategory()); 
        todoEntity.setTodoCategoryColor(todoDTO.getTodoCategoryColor()); 
        return todoEntity;
    }
}
