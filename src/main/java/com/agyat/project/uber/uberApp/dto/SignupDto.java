package com.agyat.project.uber.uberApp.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SignupDto {

    private String name;
    private String email;
    private String password;
}
