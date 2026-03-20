package com.example.tea.service;

import com.example.tea.entity.dto.Question.VaildateQuestionDTO;
import com.example.tea.entity.vo.Question.QuestionVO;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public interface QuestionService {
    List<QuestionVO> getQuestion();

    String getAnswer(List<VaildateQuestionDTO> vaildateQuestionDTOS);
}
