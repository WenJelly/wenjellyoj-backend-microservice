package com.wenjelly.wenjellyojbackendjudgeservice.strategy;


import cn.hutool.json.JSONUtil;
import com.wenjelly.wenjellyojbacjendmodel.model.dto.judge.JudgeInfo;
import com.wenjelly.wenjellyojbacjendmodel.model.dto.question.JudgeCase;
import com.wenjelly.wenjellyojbacjendmodel.model.dto.question.JudgeConfig;
import com.wenjelly.wenjellyojbacjendmodel.model.entity.Question;
import com.wenjelly.wenjellyojbacjendmodel.model.enums.JudgeInfoMessageEnum;

import java.util.List;

public class DefaultJudgeStrategy implements JudgeStrategy {

    /**
     * 默认判题策略
     *
     * @param judgeContext
     * @return
     */
    @Override
    public JudgeInfo doJudge(JudgeContext judgeContext) {

        JudgeInfo judgeInfo = judgeContext.getJudgeInfo();
        List<String> inputList = judgeContext.getInputList();
        List<String> outputList = judgeContext.getOutputList();
        Question question = judgeContext.getQuestion();
        List<JudgeCase> judgeCaseList = judgeContext.getJudgeCaseList();
        // 判题结果
        JudgeInfo judgeInfoResponse = new JudgeInfo();
        // 从判题结果中获取这段代码的时间和空间消耗
        Long memory = judgeInfo.getMemory();
        Long time = judgeInfo.getTime();
        judgeInfoResponse.setMemory(memory);
        judgeInfoResponse.setTime(time);
        // 设置题目提交结果
        JudgeInfoMessageEnum judgeInfoMessageEnum = JudgeInfoMessageEnum.ACCEPTED;
        // 如果不相等
        if (outputList.size() != inputList.size()) {
            // 设置结果为答案错误
            judgeInfoMessageEnum = JudgeInfoMessageEnum.WRONG_ANSWER;
            judgeInfoResponse.setMessage(judgeInfoMessageEnum.getValue());
            return judgeInfoResponse;
        }
        for (int i = 0; i < judgeCaseList.size(); i++) {
            JudgeCase judgeCase = judgeCaseList.get(i);
            if (!judgeCase.getOutput().equals(outputList.get(i))) {
                // 设置结果为答案错误
                judgeInfoMessageEnum = JudgeInfoMessageEnum.WRONG_ANSWER;
                judgeInfoResponse.setMessage(judgeInfoMessageEnum.getValue());
                return judgeInfoResponse;
            }
        }
        // 判断题目限制
        // 获取这个题目的消耗限制
        String judgeConfigStr = question.getJudgeConfig();
        JudgeConfig judgeConfig = JSONUtil.toBean(judgeConfigStr, JudgeConfig.class);
        Long needMemoryLimit = judgeConfig.getMemoryLimit();
        Long needTimeLimit = judgeConfig.getTimeLimit();
        if (memory > needMemoryLimit && needMemoryLimit != null) {
            // 内存溢出
            judgeInfoMessageEnum = JudgeInfoMessageEnum.MEMORY_LIMIT_EXCEEDED;
            judgeInfoResponse.setMessage(judgeInfoMessageEnum.getValue());
            return judgeInfoResponse;
        }
        if (time > needTimeLimit && needTimeLimit != null) {
            // 时间溢出
            judgeInfoMessageEnum = JudgeInfoMessageEnum.TIME_LIMIT_EXCEEDED;
            judgeInfoResponse.setMessage(judgeInfoMessageEnum.getValue());
            return judgeInfoResponse;
        }
        judgeInfoResponse.setMessage(judgeInfoMessageEnum.getValue());
        return judgeInfoResponse;


    }
}
