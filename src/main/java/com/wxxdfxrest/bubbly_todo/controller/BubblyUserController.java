package com.wxxdfxrest.bubbly_todo.controller;

import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import com.wxxdfxrest.bubbly_todo.dto.UserDTO;

import lombok.RequiredArgsConstructor;

import com.wxxdfxrest.bubbly_todo.service.UserService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequiredArgsConstructor
public class BubblyUserController {
    private final UserService userService;

    // web
    @GetMapping("/")
    public String goIndex() {
        return "index";
    }

    // web 
    @GetMapping("/bubbly-todo/signup") 
    public String goSignupForm() {
        return "signup";
    }

    // web 
    @GetMapping("/bubbly-todo/login")
    public String goLogin() {
        return "login";
    }

    // Swift 저장 // signup
    @PostMapping("/bubbly-todo/signup")
    public ResponseEntity<String> goSignup(@RequestBody UserDTO userDTO) {
        System.out.println("userDTO = " + userDTO);
    
        boolean signupSuccess = userService.userSignup(userDTO); // 회원가입 성공 여부 확인
    
        if (signupSuccess) {
            return ResponseEntity.status(HttpStatus.CREATED).body("회원가입 성공"); // 201 Created
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("회원가입 실패: 이미 존재하는 이메일"); // 400 Bad Request
        }
    }

    // Swift 저장 // login
    @PostMapping("/bubbly-todo/login")
    public ResponseEntity<String> goLogin(@RequestBody UserDTO userDTO, HttpSession session) {
        System.out.println("login");
        UserDTO loginResult = userService.userLogin(userDTO);
        if (loginResult != null) {
            session.setAttribute("loginEmail", loginResult.getUseremail());
            return ResponseEntity.ok("로그인 성공");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인 실패");
        }
    }

    // swift login 한 아이디 데이터 확인 - useremail로 확인 
    @GetMapping("/bubbly-todo/{useremail}")
    public ResponseEntity<UserDTO> findByUseremail(@PathVariable(name = "useremail") String useremail) {
        System.out.println("Fetching user with email: " + useremail); // 요청된 이메일 출력

        UserDTO userDTO = userService.findByUseremail(useremail);

        if (userDTO == null) {
            System.out.println("User not found."); // 사용자 없음 출력
            return ResponseEntity.notFound().build(); // 404 Not Found 반환
        }

        return ResponseEntity.ok(userDTO); // 조회된 UserDTO 반환
    }
}