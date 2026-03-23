package com.example.tea.service.impl;

import com.example.tea.entity.dto.Question.QuestionDTO;
import com.example.tea.entity.dto.Question.VaildateQuestionDTO;
import com.example.tea.entity.pojo.Coupon.Coupon;
import com.example.tea.entity.vo.Question.AnswerQuestionVO;
import com.example.tea.entity.vo.Question.QuestionVO;
import com.example.tea.mapper.CouponMapper;
import com.example.tea.mapper.QuestionMapper;
import com.example.tea.rabbitmq.producer.CouponDelayProducer;
import com.example.tea.service.QuestionService;
import com.example.tea.utils.ThreadLocalUserIdUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

@Service
public class QuestionServiceImpl implements QuestionService {
    @Autowired
    private QuestionMapper questionMapper;
    @Autowired
    private CouponMapper couponMapper;
    @Autowired
    private CouponDelayProducer couponDelayProducer;
    @Override
    public List<QuestionVO> getQuestion() {
        Integer scope = questionMapper.queryQuestionScope();//查询当前questionId的最大值方便分配id
        //随机数id根据sqlid变化
        List<Integer> randoms = new ArrayList<>(5);
        while (true){
            int random = ThreadLocalRandom.current().nextInt(1, scope+1);
            if (!randoms.contains(random)) {
                randoms.add(random);
            }
            if (randoms.size() == 5) {
                break;
            }
        }
        List<QuestionDTO> questionDTOS = questionMapper.queryQuestionByIds(randoms);
        Map<Integer, List<QuestionDTO>> collect = questionDTOS.stream().collect(Collectors.groupingBy(QuestionDTO::getQuestionId));
        return collect.entrySet().stream().map(entry -> {
            List<String> list = entry.getValue().stream().map(dto -> {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append(dto.getOptionLabel()).append(".").append(dto.getOptionContent());
                return stringBuilder.toString();
            }).toList();
            return QuestionVO.builder()
                    .questionId(entry.getKey())
                    .questionContent(entry.getValue().get(0).getQuestionContent())
                    .options(list).build();
        }).toList();
    }

    @Override
    public AnswerQuestionVO getAnswer(List<VaildateQuestionDTO> userOptions) {
        List<Integer> numList = userOptions.stream().map(VaildateQuestionDTO::getQuestionId).toList();
        List<VaildateQuestionDTO> answer = questionMapper.getAnswer(numList);

        List<VaildateQuestionDTO> wrong = answer.stream().filter(user -> {
            List<VaildateQuestionDTO> list = userOptions.stream().filter(option ->
                    option.getQuestionId().equals(user.getQuestionId())).toList();
            return !list.get(0).getOptionLabel().equalsIgnoreCase(user.getOptionLabel());
        }).toList();

        if(userOptions.size()-wrong.size()>=2){
            //检验是否有未过期优惠卷
            if (couponMapper.getUnusedCoupon(ThreadLocalUserIdUtil.getCurrentId()).size()==0) {
                Coupon coupon = Coupon.builder()
                        .userId(ThreadLocalUserIdUtil.getCurrentId())
                        .intro("满200-50全场通用")
                        .reduceAmount(BigDecimal.valueOf(50))
                        .minAmount(BigDecimal.valueOf(200))
                        .startTime(LocalDateTime.now())
                        .endTime(LocalDateTime.now().plusDays(1))
                        .status(Coupon.STATUS_UNUSED)
                        .createTime(LocalDateTime.now())
                        .updateTime(LocalDateTime.now())
                        .build();
                couponMapper.insertQuestionCoupon(coupon);
                couponDelayProducer.sendDelayCouponMessage(coupon.getId(), 1000 * 60 * 60 * 24);
                return AnswerQuestionVO.builder()
                        .msg(new StringBuffer().append("回答正确").append(userOptions.size()-wrong.size()).append("道题目").append("!获得的满200-50全场通用卷已经发放到您的账户").toString())
                        .answerList(answer)
                        .build();
            }else return AnswerQuestionVO.builder()
                    .msg(new StringBuffer().append("回答正确").append(userOptions.size()-wrong.size()).append("道题目").append("但24小时只能获取一张优惠卷").toString())
                    .answerList(answer)
                    .build();

       }else return AnswerQuestionVO.builder()
                    .msg(new StringBuffer().append("回答正确").append(userOptions.size()-wrong.size()).append("道题目").append("需要三题以上才能获取优惠卷").toString())
                .answerList(answer)
                    .build();


    }
}
