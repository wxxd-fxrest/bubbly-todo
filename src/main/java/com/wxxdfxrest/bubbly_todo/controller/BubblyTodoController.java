package com.wxxdfxrest.bubbly_todo.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import com.wxxdfxrest.bubbly_todo.dto.UserDTO;

import lombok.RequiredArgsConstructor;

import com.wxxdfxrest.bubbly_todo.service.UserService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequiredArgsConstructor
public class BubblyTodoController {
    private final UserService userService;

    @GetMapping("/")
    public String goIndex() {
        return "index";
    }

    // 이동 // signup
    @GetMapping("/bubbly-todo/signup") 
    public String goSignupForm() {
        return "signup";
    }

    // Web 저장 // signup
    // @PostMapping("/bubbly-todo/signup") 
    // public String goSignup(@ModelAttribute UserDTO userDTO) {
    //     System.out.println("userDTO = " + userDTO);
    //     userService.userSignup(userDTO);
    //     return "index";
    // }

    // Swift 저장 // signup
    @PostMapping("/bubbly-todo/signup")
    public ResponseEntity<String> goSignup(@RequestBody UserDTO userDTO) {
        System.out.println("userDTO = " + userDTO);
        userService.userSignup(userDTO);
        return ResponseEntity.ok("회원가입 성공");
    }

    // 이동 // login
    @GetMapping("/bubbly-todo/login")
    public String goLogin() {
        return "login";
    }

    // Web 저장 // login
    // @PostMapping("/bubbly-todo/login")
    // public String goLogin(@ModelAttribute UserDTO userDTO, HttpSession session) {
    //     System.out.println("login");
    //     UserDTO loginResult = userService.userLogin(userDTO);
    //     if (loginResult != null) {
    //         // login 성공 
    //         session.setAttribute("loginEmail", loginResult.getUseremail());
    //         return "main";
    //     } else {
    //         // login 실패
    //         return "login";
    //     }
    // }

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

    // 로그인 상태 확인 -- id 조회하기 다시 확인 
    // @GetMapping("/bubbly-todo/state/{id}")
    // public ResponseEntity<String> checkLoginState(@PathVariable Long id, Model model, HttpSession session) {
    //     Long userId = (Long) session.getAttribute("userId");

    //     if (userId != null) {
    //         boolean isLoggedIn = userService.isUserLoggedIn(userId);
    //         System.out.println("사용자 ID: " + userId);
    //         if (isLoggedIn) {
    //             return ResponseEntity.ok("로그인 상태: 로그인 중입니다. 사용자 ID: " + userId);
    //         }
    //     }

    //     return ResponseEntity.ok("로그인 상태: 로그인하지 않았습니다.");
    // }
}


