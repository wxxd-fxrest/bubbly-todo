package com.wxxdfxrest.bubbly_todo.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.wxxdfxrest.bubbly_todo.dto.MemberDTO;
import com.wxxdfxrest.bubbly_todo.entity.MemberEntity;
import com.wxxdfxrest.bubbly_todo.repository.MemberRepository;

import lombok.*;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;

    // Join
    public boolean memberJoin(MemberDTO memberDTO) {
        // 중복 확인
        Optional<MemberEntity> existingMember = memberRepository.findByUserEmail(memberDTO.getUserEmail());
        
        if (existingMember.isPresent()) {
            return false; // 중복된 이메일이 존재함
        }
    
        // 사용자 저장 
        MemberEntity memberEntity = MemberEntity.toMemberEntity(memberDTO);
        memberRepository.save(memberEntity);
        return true; // Join 성공 
    }

    // Login
    public MemberDTO memberLogin(MemberDTO memberDTO) {
        // 1. 회원이 입력한 이메일로 DB에서 조회 
        // 2. DB에서 조회한 비밀번호와 사용자 입력 비밀번호가 일치하는지 판단 
        Optional<MemberEntity> existingMember = memberRepository.findByUserEmail(memberDTO.getUserEmail());  
        if(existingMember.isPresent()) {
            // 해당 이메일을 가진 회원 정보가 있음(회원가입 된 이메일)
            MemberEntity memberEntity = existingMember.get();
            if(memberEntity.getUserPassword().equals(memberDTO.getUserPassword())) {
                // 비밀번호 일치 여부 확인 -> 일치 
                // entity 객체를 dto 로 변환 후 return
                MemberDTO dto = MemberDTO.toMemberDTO(memberEntity);
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

    // User Data 
    public MemberDTO findByUseremail(String userEmail) {
        System.out.println("Received ID: " + userEmail); // ID 출력
    
        Optional<MemberEntity> optionalMemberEntity = memberRepository.findByUserEmail(userEmail);
        
        if (optionalMemberEntity.isPresent()) {
            MemberDTO memberDTO = MemberDTO.toMemberDTO(optionalMemberEntity.get());
            System.out.println("User found: " + memberDTO); // 조회된 사용자 정보 출력
            return memberDTO;
        } else {
            System.out.println("No user found with ID: " + userEmail); // 사용자 없음 출력
            return null;
        }
    }
}