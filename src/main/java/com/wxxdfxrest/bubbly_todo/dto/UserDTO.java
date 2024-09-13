package com.wxxdfxrest.bubbly_todo.dto;

import com.wxxdfxrest.bubbly_todo.entity.UserEntity;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class UserDTO {
    public static UserDTO to;
    private Long id;
    private String useremail;
    private String userpassword;
    private String username;

    public static UserDTO toUserDTO(UserEntity userEntity) {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(userEntity.getId());       
        userDTO.setUseremail(userEntity.getUseremail());
        userDTO.setUserpassword(userEntity.getUserpassword()); 
        userDTO.setUsername(userEntity.getUsername()); 
        return userDTO;
    }
}
