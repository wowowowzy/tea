package com.example.tea.entity.vo.Question;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class QuestionVO {
    private Integer questionId;
    private String questionContent;
    private List<String> options;
}
