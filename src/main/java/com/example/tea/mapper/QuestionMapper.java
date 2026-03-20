package com.example.tea.mapper;

import com.example.tea.entity.dto.Question.QuestionDTO;
import com.example.tea.entity.dto.Question.VaildateQuestionDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface QuestionMapper {
    List<QuestionDTO> queryQuestionByIds(List<Integer> randoms);

    @Select("select MAX(question_id) from tea_choice_question")
    Integer queryQuestionScope();

    List<VaildateQuestionDTO> getAnswer(List<Integer> questionIds);
}
