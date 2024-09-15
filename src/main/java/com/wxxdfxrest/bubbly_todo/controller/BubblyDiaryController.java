package com.wxxdfxrest.bubbly_todo.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import com.wxxdfxrest.bubbly_todo.dto.DiaryDTO;
import com.wxxdfxrest.bubbly_todo.service.DiaryService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class BubblyDiaryController {
    private final DiaryService diaryService;

    @GetMapping("/bubbly-todo/diary") 
    public ResponseEntity<List<DiaryDTO>> findDiaryAll(Model model) {
        List<DiaryDTO> diaryDTOList = diaryService.findDiaryAll();
        return ResponseEntity.ok(diaryDTOList);
    }    

    @PostMapping("/bubbly-todo/addDiary") // 일기 항목 추가
    public ResponseEntity<String> addDiary(@RequestBody DiaryDTO diaryDTO) {
        System.out.println("diaryDTO = " + diaryDTO); // 디버깅용 출력

        boolean diarySaveSuccess = diaryService.addDiary(diaryDTO); // 서비스 호출
        
        if (diarySaveSuccess) {
            return ResponseEntity.status(HttpStatus.CREATED).body("일기 추가 성공");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("일기 추가 실패");
        }
    }

    @GetMapping("/bubbly-todo/diary/{diaryDate}")
    public ResponseEntity<DiaryDTO> findById(@PathVariable(name = "diaryDate") String diaryDate, Model model) {
        System.out.println("Fetching ID: " + diaryDate); // 요청된 ID 출력
    
        DiaryDTO diaryDTO = diaryService.findByDiaryId(diaryDate);
    
        if (diaryDTO == null) {
            System.out.println("User not found"); // 사용자 없음 출력
            return ResponseEntity.notFound().build(); // 404 Not Found 반환
        }
    
        return ResponseEntity.ok(diaryDTO); // 사용자 정보가 있는 detail 페이지로 이동
    }

    @PostMapping("/bubbly-todo/diary/update/{diaryDate}")
    public ResponseEntity<String> updateTodo(@PathVariable(name = "diaryDate") String diaryDate, @RequestBody DiaryDTO diaryDTO, BindingResult bindingResult) {
        System.out.println("Received diaryDTO: " + diaryDTO); // 디버깅 로그 추가

        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body("입력 값에 오류가 있습니다."); // 오류 처리
        }

        diaryDTO.setDiaryDate(diaryDate); // ID 설정
        diaryService.updateTodo(diaryDTO); // 서비스에서 업데이트 메소드 호출

        return ResponseEntity.ok("투두 항목이 성공적으로 업데이트되었습니다."); // 200 OK와 메시지 반환
    }


    @GetMapping("/bubbly-todo/diary/delete/{id}")
    public ResponseEntity<String> deleteTodo(@PathVariable("id") Long id) {
        boolean isDeleted = diaryService.deleteDiaryById(id);
        
        if (isDeleted) {
            return ResponseEntity.ok("투두 항목이 삭제되었습니다."); // 200 OK와 메시지 반환
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("삭제할 수 없는 투두 항목입니다."); // 404 NOT FOUND와 메시지 반환
        }
    }
}
