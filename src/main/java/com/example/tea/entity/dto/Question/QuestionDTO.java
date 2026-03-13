package com.example.tea.entity.dto.Question;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class QuestionDTO {
    private Integer questionId;
    private String questionContent;
    private String optionContent;
    private String optionLabel;//选项字母
    private Integer correctOptionId;//正确选项id
}
