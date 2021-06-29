package com.relias.example.exception;

import com.relias.example.enums.Language;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class MultiLanguageException extends RuntimeException {
    protected Language language;
}
