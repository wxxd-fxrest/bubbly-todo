package com.wxxdfxrest.bubbly_todo.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.wxxdfxrest.bubbly_todo.entity.DiaryEntity;

public interface DiaryRepository extends JpaRepository<DiaryEntity, Long> {
    Optional<DiaryEntity> findById(Long diaryId);
    Optional<DiaryEntity> findByDiaryDate(String diaryDate);
    List<DiaryEntity> findByDiaryUser(String diaryUser); // UserEmail로 다이어리 찾기
    Optional<DiaryEntity> findByDiaryDateAndDiaryUser(String diaryDate, String diaryUser);
} 
