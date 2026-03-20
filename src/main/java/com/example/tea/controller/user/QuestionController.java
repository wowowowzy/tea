package com.example.tea.controller.user;

import com.example.tea.entity.dto.Question.VaildateQuestionDTO;
import com.example.tea.entity.pojo.Result;
import com.example.tea.service.QuestionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api/user")
@Slf4j
public class QuestionController {
    @Autowired
    private QuestionService questionService;

    /**
     * 获取问题列表
     * @return
     */
    @GetMapping("/getQuestion")
    public Result getQuestion(){
        return Result.success(questionService.getQuestion());
    }

    /**
     * 检验答案以及优惠卷派送
     * @param questionId
     * @param optionLabel
     * @return
     */
    @GetMapping("/getAnswer")
    public Result getAnswer(@RequestParam(value = "questionId") Integer[] questionId,String[] optionLabel){
        List<VaildateQuestionDTO> dtoList = new ArrayList<>();
        for (int i = 0; i < questionId.length; i++) {
            dtoList.add(new VaildateQuestionDTO(
                    questionId[i],
                    optionLabel[i]
            ));
        }
        return Result.success(questionService.getAnswer(dtoList));
    }
}
