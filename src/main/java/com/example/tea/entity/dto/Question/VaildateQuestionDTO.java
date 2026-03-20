package com.example.tea.entity.dto.Question;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VaildateQuestionDTO {
    Integer questionId;

    String optionLabel;

}
