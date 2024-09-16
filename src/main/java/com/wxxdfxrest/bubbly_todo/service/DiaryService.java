package com.wxxdfxrest.bubbly_todo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.wxxdfxrest.bubbly_todo.dto.DiaryDTO;
import com.wxxdfxrest.bubbly_todo.entity.DiaryEntity;
import com.wxxdfxrest.bubbly_todo.repository.DiaryRepository;

import lombok.RequiredArgsConstructor;
import java.util.stream.Collectors; // Collectors 임포트

@Service
@RequiredArgsConstructor
public class DiaryService {
    private final DiaryRepository diaryRepository;

   // Email이 동일한 Diary Data 가져오기
   public List<DiaryDTO> findTodoByUserEmail(String diaryUser) {
        List<DiaryEntity> diaryEntities = diaryRepository.findByDiaryUser(diaryUser); // Repository에서 이메일로 검색
        return diaryEntities.stream() // 스트림으로 변환
            .map(DiaryDTO::toDiaryDTO) // 각 엔티티를 DTO로 변환
            .collect(Collectors.toList()); // 리스트로 수집
    }

    // Add Diary
    public boolean addDiary(DiaryDTO diaryDTO) {
        // Diary 날짜와 사용자 중복 확인
        Optional<DiaryEntity> existingDiary = diaryRepository.findByDiaryDateAndDiaryUser(diaryDTO.getDiaryDate(), diaryDTO.getDiaryUser());
        
        if (existingDiary.isPresent()) {
            return false; // 이미 존재하는 Diary 날짜와 사용자
        }
        
        // 새로운 Diary 저장
        DiaryEntity diaryEntity = DiaryEntity.toDiaryEntity(diaryDTO);
        diaryRepository.save(diaryEntity);
        return true; // Diary 저장 성공
    }    

    // Diary Detail
    public DiaryDTO findByDiaryId(Long diaryId) {
        Optional<DiaryEntity> diaryEntityOptional = diaryRepository.findById(diaryId);

        if (diaryEntityOptional.isPresent()) {
            DiaryEntity diaryEntity = diaryEntityOptional.get();
            return DiaryDTO.toDiaryDTO(diaryEntity); // DTO 변환
        } else {
            return null; // 다이어리가 존재하지 않으면 null 반환
        }
    }

    // Diary Update
    public void updateDiary(DiaryDTO diaryDTO) {
        // diaryId를 사용하여 다이어리 엔티티를 찾기
        Optional<DiaryEntity> optionalDiaryEntity = diaryRepository.findById(diaryDTO.getDiaryId());
        
        if (optionalDiaryEntity.isPresent()) {
            DiaryEntity diaryEntity = optionalDiaryEntity.get(); // 값 가져오기
            diaryEntity.setDiary(diaryDTO.getDiary());
            diaryEntity.setDiaryDate(diaryDTO.getDiaryDate());
            diaryEntity.setDiaryEmoji(diaryDTO.getDiaryEmoji());
            diaryEntity.setDiaryUser(diaryDTO.getDiaryUser());
            diaryRepository.save(diaryEntity);
        } else {
            System.out.println("Todo not found with ID: " + diaryDTO.getDiaryId()); // 디버깅
        }
    }

    // Diary Delete
    public boolean deleteCategoryByDate(String diaryDate) {
        Optional<DiaryEntity> diaryEntityOptional = diaryRepository.findByDiaryDate(diaryDate);
        
        if (diaryEntityOptional.isPresent()) {
            diaryRepository.delete(diaryEntityOptional.get()); // 존재하는 경우 삭제
            return true; // 삭제 성공
        }
        return false; // ID가 존재하지 않음
    }
}
