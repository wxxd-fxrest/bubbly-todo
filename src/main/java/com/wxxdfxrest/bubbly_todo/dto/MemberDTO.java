package com.wxxdfxrest.bubbly_todo.dto;

import com.wxxdfxrest.bubbly_todo.entity.MemberEntity;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class MemberDTO {
    public static MemberDTO to;
    private Long id;
    private String userEmail;
    private String userPassword;
    private String userName;

    public static MemberDTO toMemberDTO(MemberEntity memberEntity) {
        MemberDTO memberDTO = new MemberDTO();
        memberDTO.setId(memberEntity.getId());       
        memberDTO.setUserEmail(memberEntity.getUserEmail());
        memberDTO.setUserPassword(memberEntity.getUserPassword()); 
        memberDTO.setUserName(memberEntity.getUserName()); 
        return memberDTO;
    }
}
