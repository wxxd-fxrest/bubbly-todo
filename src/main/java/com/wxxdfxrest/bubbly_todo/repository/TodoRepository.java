package com.wxxdfxrest.bubbly_todo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.wxxdfxrest.bubbly_todo.entity.TodoEntity;

public interface TodoRepository extends JpaRepository<TodoEntity, Long> {
    Optional<TodoEntity> findByTodoId(Long todoId);
}
