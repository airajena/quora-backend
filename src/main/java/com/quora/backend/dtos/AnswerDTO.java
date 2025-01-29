package com.quora.backend.dtos;

import lombok.Data;

import java.util.Set;

@Data
public class AnswerDTO {
    private Long id;
    private String content;
    private Long questionId;
    private Long userId;
}
