package com.example.sprintdev.model.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class ApproveContentException extends RuntimeException {
    public ApproveContentException(String message) {
        super(message);
    }
}
