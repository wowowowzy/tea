package com.example.tea.entity.vo.Question;

import com.example.tea.entity.dto.Question.VaildateQuestionDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AnswerQuestionVO {
    private List<VaildateQuestionDTO> answerList;
    private String msg;
}
