package com.example.tea.entity.pojo.Question;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Question {
    private Integer questionId;//问题id
    private String questionContent;//问题文本
    private Integer correctOptionId;//正确选项id
    private String teaType;//茶叶类型
}
