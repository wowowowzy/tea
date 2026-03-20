package com.example.tea.service;

import com.example.tea.entity.dto.Question.VaildateQuestionDTO;
import com.example.tea.entity.vo.Question.AnswerQuestionVO;
import com.example.tea.entity.vo.Question.QuestionVO;

import java.util.List;

public interface QuestionService {
    List<QuestionVO> getQuestion();

    AnswerQuestionVO getAnswer(List<VaildateQuestionDTO> vaildateQuestionDTOS);
}
