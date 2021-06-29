package com.relias.example.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class APIResponseDataList<T> {
    private boolean success;
    private List<T> data;
    private List<APIError> errors;

}
