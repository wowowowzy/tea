package com.example.tea.controller.user;

import com.example.tea.entity.pojo.Result;
import com.example.tea.entity.vo.Question.QuestionVO;
import com.example.tea.service.QuestionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
@Slf4j
public class QuestionController {
    @Autowired
    private QuestionService questionService;
    @GetMapping("/getQuestion")
    public Result getQuestion(){
        return Result.success(questionService.getQuestion());
    }
}
