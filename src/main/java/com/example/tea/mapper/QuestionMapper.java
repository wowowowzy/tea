package com.example.tea.mapper;

import com.example.tea.entity.dto.Question.QuestionDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface QuestionMapper {
    List<QuestionDTO> queryQuestionById(Integer questionId);

    @Select("select MAX(question_id) from tea_choice_question")
    Integer queryQuestionScope();

    String getAnswer(Integer id);
}
