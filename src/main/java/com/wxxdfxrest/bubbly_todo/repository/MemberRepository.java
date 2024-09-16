package com.wxxdfxrest.bubbly_todo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.wxxdfxrest.bubbly_todo.entity.MemberEntity;

public interface MemberRepository extends JpaRepository<MemberEntity, Long> {
    Optional<MemberEntity> findByUserEmail(String userEmail); // Optional을 반환
}
