package com.example.lesson7.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserDTO {


    private String username;

    private String email;

    private String bio;

    private String website;

    private String gender;

    private String fullName;

    private String password;

}
