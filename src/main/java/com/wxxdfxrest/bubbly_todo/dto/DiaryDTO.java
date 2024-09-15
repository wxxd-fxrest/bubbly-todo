package com.wxxdfxrest.bubbly_todo.dto;

import com.wxxdfxrest.bubbly_todo.entity.DiaryEntity;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class DiaryDTO {
    private Long diaryId;
    private String diary;
    private String diaryDate;
    private String diaryEmoji;

    public static DiaryDTO toDiaryDTO(DiaryEntity diaryEntity) {
        DiaryDTO diaryDTO = new DiaryDTO();
        diaryDTO.setDiaryId(diaryEntity.getDiaryId());
        diaryDTO.setDiary(diaryEntity.getDiary());
        diaryDTO.setDiaryDate(diaryEntity.getDiaryDate());
        diaryDTO.setDiaryEmoji(diaryEntity.getDiaryEmoji());
        return diaryDTO;
    }    
}
