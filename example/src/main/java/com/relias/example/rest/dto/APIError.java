package com.relias.example.rest.dto;

import com.relias.example.enums.Language;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class APIError {
    private String errorCode;
    private Language errorLanguage;
    private String errorDetails;
}
