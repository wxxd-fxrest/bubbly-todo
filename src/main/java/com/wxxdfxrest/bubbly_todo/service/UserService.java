package com.wxxdfxrest.bubbly_todo.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.wxxdfxrest.bubbly_todo.dto.UserDTO;
import com.wxxdfxrest.bubbly_todo.entity.UserEntity;
import com.wxxdfxrest.bubbly_todo.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    // 회원가입 
    public boolean userSignup(UserDTO userDTO) {
        // 이메일 중복 확인
        if (userRepository.findByUseremail(userDTO.getUseremail()).isPresent()) {
            return false; // 이미 존재하는 이메일
        }
    
        // 새로운 사용자 저장
        UserEntity userEntity = UserEntity.toUserEntity(userDTO);
        userRepository.save(userEntity);
        return true; // 회원가입 성공
    }    

    // 로그인 
    public UserDTO userLogin(UserDTO userDTO) {
        // 1. 회원이 입력한 이메일로 DB에서 조회 
        // 2. DB에서 조회한 비밀번호와 사용자 입력 비밀번호가 일치하는지 판단 
        Optional<UserEntity> byUserEmail = userRepository.findByUseremail(userDTO.getUseremail());  
        if(byUserEmail.isPresent()) {
            // 해당 이메일을 가진 회원 정보가 있음(회원가입 된 이메일)
            UserEntity userEntity = byUserEmail.get();
            if(userEntity.getUserpassword().equals(userDTO.getUserpassword())) {
                // 비밀번호 일치 여부 확인 -> 일치 
                // entity 객체를 dto 로 변환 후 return

                // setLoginstate 설정 
                // userEntity.setLoginstate(true); // 로그인 성공 시 loginstate를 true로 변경
                // userRepository.save(userEntity); // 데이터베이스에 변경 사항 저장

                UserDTO dto = UserDTO.toUserDTO(userEntity);
                return dto;
            } else {
                // 비밀번호 일치 여부 확인 -> 불일치 
                return null; 
            }
        } else {
            // 해당 이메일을 가진 회원 정보가 없음(회원가입되지 않은 이메일)
            return null;
        }
    }

    public List<UserDTO> findAll() {
        List<UserEntity> memberEntityList = userRepository.findAll();
        List<UserDTO> memberDTOList = new ArrayList<>();
        for (UserEntity memberEntity: memberEntityList) {
            memberDTOList.add(UserDTO.toUserDTO(memberEntity));
        }
        return memberDTOList;
    }

    public UserDTO findByUseremail(String useremail) {
        System.out.println("Received ID: " + useremail); // ID 출력
    
        Optional<UserEntity> optionalUserEntity = userRepository.findByUseremail(useremail);
        
        if (optionalUserEntity.isPresent()) {
            UserDTO userDTO = UserDTO.toUserDTO(optionalUserEntity.get());
            System.out.println("User found: " + userDTO); // 조회된 사용자 정보 출력
            return userDTO;
        } else {
            System.out.println("No user found with ID: " + useremail); // 사용자 없음 출력
            return null;
        }
    }
}
