package com.yumyum.sns.security.oauthjwt.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserDTO {

    private String role;
    private String name;
    private String username;
    private Long id;

    public UserDTO(String role, String name, String username,Long userId) {
        this.role = role;
        this.name = name;
        this.username = username;
        this.id = userId;
    }

    public UserDTO(String username, String role){
        this.role = role;
        this.username = username;
    }



}
