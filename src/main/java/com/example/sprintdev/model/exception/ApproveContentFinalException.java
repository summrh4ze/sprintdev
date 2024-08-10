package com.example.sprintdev.model.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class ApproveContentFinalException extends RuntimeException {
    public ApproveContentFinalException(String message) {
        super(message);
    }
}
