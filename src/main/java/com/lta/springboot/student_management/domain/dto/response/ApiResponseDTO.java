package com.lta.springboot.student_management.domain.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({"success", "message", "errorCode", "data", "timestamp", "path"})
public record ApiResponseDTO<T>(
        boolean success,
        String message,
        String errorCode,
        T data,
        LocalDateTime timestamp,
        String path) {
}
