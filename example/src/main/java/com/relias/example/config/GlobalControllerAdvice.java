package com.relias.example.config;

import com.relias.example.enums.Language;
import com.relias.example.exception.EntityNotFoundException;
import com.relias.example.rest.dto.APIError;
import com.relias.example.rest.dto.APIResponseData;
import com.relias.example.rest.dto.APIResponseDataList;
import com.relias.example.rest.dto.ErrorCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static java.util.Objects.isNull;

@Slf4j
@ControllerAdvice
public class GlobalControllerAdvice {

    private MessageSource messageSource;

    public GlobalControllerAdvice(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<APIResponseData> businessLogicException(EntityNotFoundException entityNotFoundException) {
        log.error("Entity not found exception {}", entityNotFoundException);
        List<APIError> errors = new ArrayList<>();
        errors.add(APIError.builder()
                .errorCode(entityNotFoundException.getMessageCode().name())
                .errorLanguage(entityNotFoundException.getLanguage())
                .errorDetails(messageSource.getMessage(entityNotFoundException.getMessageCode().name(),
                        isNull(entityNotFoundException.getEntityId()) ? null : new String[]{entityNotFoundException.getEntityId()},
                        new Locale(isNull(entityNotFoundException.getLanguage())? Language.en.name() : entityNotFoundException.getLanguage().name())))
                .build());

        APIResponseData apiResponse = APIResponseData.builder()
                .success(false)
                .errors(errors)
                .build();

        return new ResponseEntity<>(apiResponse, new HttpHeaders(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<APIResponseDataList> anyException(Exception exception) {
        log.error("Server exception found {}", exception);
        List<APIError> errors = new ArrayList<>();
        errors.add(APIError.builder()
                .errorCode("500")
                .errorDetails(messageSource.getMessage(ErrorCode.SERVER_ERROR.name(),
                        null,
                        Locale.ENGLISH))
                .build());

        APIResponseDataList apiResponse = APIResponseDataList.builder()
                .success(false)
                .errors(errors)
                .build();

        return new ResponseEntity<>(apiResponse, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
