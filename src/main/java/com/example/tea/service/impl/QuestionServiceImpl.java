package com.example.tea.service.impl;

import com.alibaba.druid.util.StringUtils;
import com.example.tea.entity.dto.Question.QuestionDTO;
import com.example.tea.entity.pojo.Coupon.Coupon;
import com.example.tea.entity.vo.Question.QuestionVO;
import com.example.tea.mapper.CouponMapper;
import com.example.tea.mapper.QuestionMapper;
import com.example.tea.service.QuestionService;
import com.example.tea.utils.ThreadLocalUserIdUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class QuestionServiceImpl implements QuestionService {
    @Autowired
    private QuestionMapper questionMapper;
    @Autowired
    private CouponMapper couponMapper;
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

    @Override
    public String getAnswer(Integer id, String optionLabel) {
       String answer = questionMapper.getAnswer(id);
       if(StringUtils.equalsIgnoreCase(answer, optionLabel)){
           couponMapper.getQuestionCoupon(Coupon.builder()
                   .userId(ThreadLocalUserIdUtil.getCurrentId())
                   .intro("满200-50全场通用")
                   .reduceAmount(BigDecimal.valueOf(50))
                   .minAmount(BigDecimal.valueOf(200))
                   .startTime(LocalDateTime.now())
                   .endTime(LocalDateTime.now().plusDays(7))
                   .status(Coupon.STATUS_UNUSED)
                   .createTime(LocalDateTime.now())
                   .updateTime(LocalDateTime.now())
                   .build()
           );
           return "回答正确!获得的满200-50全场通用卷已经发放到您的账户";
       }else {
           return "回答错误!";
       }
    }
}
