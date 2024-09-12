package com.wxxdfxrest.bubbly_todo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.wxxdfxrest.bubbly_todo.entity.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByUseremail(String useremail);
    // Optional<UserEntity> findById(Long id); // 사용자 ID로 조회
}
