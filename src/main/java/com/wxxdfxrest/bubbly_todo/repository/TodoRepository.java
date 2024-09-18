package com.wxxdfxrest.bubbly_todo.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.wxxdfxrest.bubbly_todo.entity.TodoEntity;

public interface TodoRepository extends JpaRepository<TodoEntity, Long> {
    Optional<TodoEntity> findByTodoId(Long todoId);
    List<TodoEntity> findByTodoUserAndTodoDate(String todoUser, String todoDate); // todoDate로 검색
}

