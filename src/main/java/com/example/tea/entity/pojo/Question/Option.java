package com.example.tea.entity.pojo.Question;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Option {
    private Integer optionId;
    private Integer questionId;
    private String optionContent;
    private String optionLabel;//选项字母
}
