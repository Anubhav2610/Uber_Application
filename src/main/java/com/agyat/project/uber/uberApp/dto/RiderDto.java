package com.agyat.project.uber.uberApp.dto;

import com.agyat.project.uber.uberApp.entities.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class RiderDto {
    private UserDto user;
    private  Double rating;
}
