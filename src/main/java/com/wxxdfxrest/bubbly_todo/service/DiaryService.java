package com.wxxdfxrest.bubbly_todo.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.wxxdfxrest.bubbly_todo.dto.DiaryDTO;
import com.wxxdfxrest.bubbly_todo.entity.DiaryEntity;
import com.wxxdfxrest.bubbly_todo.repository.DiaryRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DiaryService {
    private final DiaryRepository diaryRepository;

    public boolean addDiary(DiaryDTO diaryDTO) {
        if (diaryRepository.findByDiaryDate(diaryDTO.getDiaryDate()).isPresent()) {
            return false; 
        }
    
        // 새로운 사용자 저장
        DiaryEntity diaryEntity = DiaryEntity.toDiaryEntity(diaryDTO);
        diaryRepository.save(diaryEntity);
        return true; // Diary 저장 성공
    }       

    public List<DiaryDTO> findDiaryAll() {
        List<DiaryEntity> diaryEntityList = diaryRepository.findAll();
        List<DiaryDTO> diaryDTOList = new ArrayList<>();
        for (DiaryEntity diaryEntity: diaryEntityList) {
            diaryDTOList.add(DiaryDTO.toDiaryDTO(diaryEntity));
        }
        return diaryDTOList;
    }

    public DiaryDTO findByDiaryId(String diaryDate) {
        System.out.println("Received ID: " + diaryDate); // ID 출력
    
        Optional<DiaryEntity> optionalDiaryEntity = diaryRepository.findByDiaryDate(diaryDate);
        
        if (optionalDiaryEntity.isPresent()) {
            DiaryDTO diaryDTO = DiaryDTO.toDiaryDTO(optionalDiaryEntity.get());
            System.out.println("User found: " + diaryDTO); // 조회된 사용자 정보 출력
            return diaryDTO;
        } else {
            System.out.println("No user found with ID: " + diaryDate); // 사용자 없음 출력
            return null;
        }
    }

    public void updateTodo(DiaryDTO diaryDTO) {
        Optional<DiaryEntity> optionalDiaryEntity = diaryRepository.findByDiaryDate(diaryDTO.getDiaryDate());
        
        if (optionalDiaryEntity.isPresent()) {
            DiaryEntity diaryEntity = optionalDiaryEntity.get(); // 값 가져오기
            diaryEntity.setDiary(diaryDTO.getDiary());
            diaryEntity.setDiaryDate(diaryDTO.getDiaryDate());
            diaryEntity.setDiaryEmoji(diaryDTO.getDiaryEmoji());
            diaryRepository.save(diaryEntity);
        } else {
            System.out.println("Todo not found with ID: " + diaryDTO.getDiaryDate()); // 디버깅
        }
    }

    public boolean deleteDiaryById(Long id) {
        if (diaryRepository.existsById(id)) {
            diaryRepository.deleteById(id);
            return true; // 삭제 성공
        }
        return false; // ID가 존재하지 않음
    }
}
