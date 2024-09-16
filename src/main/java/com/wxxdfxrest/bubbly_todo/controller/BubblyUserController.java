package com.wxxdfxrest.bubbly_todo.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.wxxdfxrest.bubbly_todo.dto.MemberDTO;
import com.wxxdfxrest.bubbly_todo.service.MemberService;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class BubblyUserController {
    private final MemberService memberService;

    // Swift -> Join 
    @PostMapping("/bubbly-todo/signup") 
    public ResponseEntity<String> goJoin(@RequestBody MemberDTO memberDTO) {
        System.out.println("memberDTO = " + memberDTO);

        boolean joinSussess = memberService.memberJoin(memberDTO); // 회원가입 성공 여부 확인

        if(joinSussess) {
            return ResponseEntity.status(HttpStatus.CREATED).body("Join 성공");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Join 실패");
        }
    }

    // Swift -> Login  
    @PostMapping("/bubbly-todo/login")
    public ResponseEntity<String> goLogin(@RequestBody MemberDTO memberDTO, HttpSession session) {
        System.out.println("login");
        MemberDTO loginResult = memberService.memberLogin(memberDTO);
        if (loginResult != null) {
            session.setAttribute("loginEmail", loginResult.getUserEmail());
            return ResponseEntity.ok("로그인 성공");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인 실패");
        }
    }

    // User Data 
    @GetMapping("/bubbly-todo/{userEmail}")
    public ResponseEntity<MemberDTO> findByUseremail(@PathVariable(name = "userEmail") String userEmail) {
        System.out.println("Fetching user with email: " + userEmail); // 요청된 이메일 출력

        MemberDTO memberDTO = memberService.findByUseremail(userEmail);

        if (memberDTO == null) {
            System.out.println("User not found."); // 사용자 없음 출력
            return ResponseEntity.notFound().build(); // 404 Not Found 반환
        }

        return ResponseEntity.ok(memberDTO); // 조회된 UserDTO 반환
    }
}
