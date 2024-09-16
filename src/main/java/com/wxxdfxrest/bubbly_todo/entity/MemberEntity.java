package com.wxxdfxrest.bubbly_todo.entity;

import com.wxxdfxrest.bubbly_todo.dto.MemberDTO;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Table(name = "member_table")
public class MemberEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true) 
    private String userEmail;

    @Column 
    private String userPassword;

    @Column 
    private String userName;

    public static MemberEntity toMemberEntity(MemberDTO memberDTO) {
        MemberEntity memberEntity = new MemberEntity();
        memberEntity.setUserEmail(memberDTO.getUserEmail());
        memberEntity.setUserPassword(memberDTO.getUserPassword());
        memberEntity.setUserName(memberDTO.getUserName());
        return memberEntity;
    }
}
