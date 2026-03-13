package com.example.tea.service.impl;

import com.example.tea.entity.dto.Question.QuestionDTO;
import com.example.tea.entity.vo.Question.QuestionVO;
import com.example.tea.mapper.QuestionMapper;
import com.example.tea.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

@Service
public class QuestionServiceImpl implements QuestionService {
    @Autowired
    private QuestionMapper questionMapper;
    @Override
    public QuestionVO getQuestion() {
        Integer scope = questionMapper.queryQuestionScope();//查询当前questionId的最大值方便分配id
        int random = ThreadLocalRandom.current().nextInt(1, scope+1);//随机数id根据sqlid变化
        List<QuestionDTO> questionDTOS = questionMapper.queryQuestionById(random);
        List<String> list = questionDTOS.stream().map(questionDTO -> {
            StringBuilder builder = new StringBuilder();
            builder.append(questionDTO.getOptionLabel())
                    .append(".")
                    .append(questionDTO.getOptionContent());
            return builder.toString();
        }).toList();
        return QuestionVO.builder()
                .questionId(questionDTOS.get(1).getQuestionId())
                .questionContent(questionDTOS.get(1).getQuestionContent())
                .options(list)
                .build();
    }
}
