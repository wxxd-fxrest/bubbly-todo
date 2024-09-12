package com.wxxdfxrest.bubbly_todo.service;

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
    public void userSignup(UserDTO userDTO) {
        UserEntity userEntity = UserEntity.toUserEntity(userDTO);
        userRepository.save(userEntity);
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
                userEntity.setLoginstate(true); // 로그인 성공 시 loginstate를 true로 변경
                userRepository.save(userEntity); // 데이터베이스에 변경 사항 저장

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

    // 앱 로딩 시 state 확인 
    // public boolean isUserLoggedIn(Long userId) {
    //     Optional<UserEntity> userEntityOptional = userRepository.findById(userId);
    //     return userEntityOptional.map(UserEntity::isLoginstate).orElse(false); // 로그인 상태 반환
    // }
}
