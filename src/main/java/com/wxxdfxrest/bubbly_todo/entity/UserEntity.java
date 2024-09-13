package com.wxxdfxrest.bubbly_todo.entity;

import com.wxxdfxrest.bubbly_todo.dto.UserDTO;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Table(name = "user_table")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true) 
    private String useremail;

    @Column 
    private String userpassword;

    @Column 
    private String username;

    public static UserEntity toUserEntity(UserDTO userDTO) {
        UserEntity userEntity = new UserEntity();
        userEntity.setUseremail(userDTO.getUseremail());
        userEntity.setUserpassword(userDTO.getUserpassword());
        userEntity.setUsername(userDTO.getUsername());
        return userEntity;
    }
}
