package com.wxxdfxrest.bubbly_todo.entity;

import com.wxxdfxrest.bubbly_todo.dto.DiaryDTO;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Table(name = "diary_table")
public class DiaryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long diaryId;
    
    @Column
    private String diary;
    
    @Column
    private String diaryDate;

    @Column
    private String diaryEmoji;

    public static DiaryEntity toDiaryEntity(DiaryDTO diaryDTO) {
        DiaryEntity diaryEntity = new DiaryEntity();
        diaryEntity.setDiary(diaryDTO.getDiary());
        diaryEntity.setDiaryDate(diaryDTO.getDiaryDate());
        diaryEntity.setDiaryEmoji(diaryDTO.getDiaryEmoji());
        return diaryEntity;
    }
}
