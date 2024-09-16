package com.wxxdfxrest.bubbly_todo.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.wxxdfxrest.bubbly_todo.dto.DiaryDTO;
import com.wxxdfxrest.bubbly_todo.service.DiaryService;

import ch.qos.logback.core.model.Model;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class BubblyDiaryController {
    private final DiaryService diaryService;

    // 개인 Diary List 
    @GetMapping("/bubbly-todo/diary/{diaryUser}") // 사용자 이메일을 경로 변수로 추가
    public ResponseEntity<List<DiaryDTO>> findAllTodo(@PathVariable(name = "diaryUser") String diaryUser) {
        List<DiaryDTO> diaryDTOList = diaryService.findTodoByUserEmail(diaryUser); // 사용자 이메일로 투두 목록 가져오기
        return ResponseEntity.ok(diaryDTOList); // 200 OK와 함께 투두 리스트 반환
    }

    // Add Diary 
    @PostMapping("/bubbly-todo/diary/addDiary")
    public ResponseEntity<String> addCategory(@RequestBody DiaryDTO diaryDTO) {
        System.out.println("diaryDTO = " + diaryDTO);
        
        boolean saveSuccess = diaryService.addDiary(diaryDTO); // 카테고리 저장 성공 여부 확인
        
        if (saveSuccess) {
            return ResponseEntity.status(HttpStatus.CREATED).body("다이어리 저장 성공"); // 201 Created
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("다이어리 저장 실패: 이미 존재하는 카테고리"); // 400 Bad Request
        }
    }    

    // Diary Detail
    @GetMapping("/bubbly-todo/diary/detail/{diaryId}")
    public ResponseEntity<DiaryDTO> findById(@PathVariable(name = "diaryId") Long diaryId, Model model) {
        System.out.println("Fetching ID: " + diaryId); // 요청된 ID 출력
    
        DiaryDTO diaryDTO = diaryService.findByDiaryId(diaryId);
    
        if (diaryDTO == null) {
            System.out.println("User not found"); // 사용자 없음 출력
            return ResponseEntity.notFound().build(); // 404 Not Found 반환
        }
    
        return ResponseEntity.ok(diaryDTO); // 사용자 정보가 있는 detail 페이지로 이동
    }

    // Diary Update
    @PostMapping("/bubbly-todo/diary/update/{diaryId}")
    public ResponseEntity<String> updateTodo(@PathVariable(name = "diaryId") Long diaryId, @RequestBody DiaryDTO diaryDTO, BindingResult bindingResult) {
        System.out.println("Received DiaryDTO: " + diaryDTO); // 디버깅 로그 추가
    
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body("입력 값에 오류가 있습니다."); // 오류 처리
        }
    
        diaryDTO.setDiaryId(diaryId); // ID 설정
        diaryService.updateDiary(diaryDTO); // 서비스에서 업데이트 메소드 호출
    
        return ResponseEntity.ok("일기가 성공적으로 업데이트되었습니다."); // 200 OK와 메시지 반환
    }

    // Diary Delete
    @GetMapping("/bubbly-todo/diary/delete/{diaryDate}")
    public String deleteDiary(@PathVariable(name = "diaryDate") String diaryDate, RedirectAttributes redirectAttributes) {
        boolean isDeleted = diaryService.deleteCategoryByDate(diaryDate);
        
        if (isDeleted) {
            redirectAttributes.addFlashAttribute("message", "카테고리 항목이 삭제되었습니다.");
        } else {
            redirectAttributes.addFlashAttribute("error", "삭제할 수 없는 카테고리 항목입니다.");
        }

        return "redirect:/category"; // 삭제 후 투두 목록으로 리다이렉트
    }
}
