package com.agyat.project.uber.uberApp.advices;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class APIResponse<T>{
    //@JsonFormat(pattern = "hh:mm:ss dd-MM-yyyy")
    private LocalDateTime timeStep;
    private T data;
    private APIError error;

    public APIResponse() {
        this.timeStep = LocalDateTime.now();
    }
    public APIResponse(T data) {
        this();
        this.data = data;
    }

    public APIResponse(APIError error) {
        this();
        this.error = error;
    }


}
