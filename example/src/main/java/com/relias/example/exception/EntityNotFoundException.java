package com.relias.example.exception;

import com.relias.example.rest.dto.ErrorCode;
import lombok.Getter;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus
@Getter
public class EntityNotFoundException extends MultiLanguageException {

    private String entityId;
    private ErrorCode messageCode;

    public EntityNotFoundException(ErrorCode messageCode) {
        this.messageCode = messageCode;
    }

    public EntityNotFoundException(ErrorCode messageCode, String entityId) {
        this.entityId = entityId;
        this.messageCode = messageCode;
    }
}