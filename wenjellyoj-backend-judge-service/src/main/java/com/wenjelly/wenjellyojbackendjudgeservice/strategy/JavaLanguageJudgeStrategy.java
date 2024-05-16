package com.wenjelly.wenjellyojbackendjudgeservice.strategy;


import cn.hutool.json.JSONUtil;
import com.wenjelly.wenjellyojbacjendmodel.model.dto.judge.JudgeInfo;
import com.wenjelly.wenjellyojbacjendmodel.model.dto.question.JudgeCase;
import com.wenjelly.wenjellyojbacjendmodel.model.dto.question.JudgeConfig;
import com.wenjelly.wenjellyojbacjendmodel.model.entity.Question;
import com.wenjelly.wenjellyojbacjendmodel.model.enums.JudgeInfoMessageEnum;

import java.util.List;
import java.util.Optional;

public class JavaLanguageJudgeStrategy implements JudgeStrategy {

    /**
     * Java判题策略
     *
     * @param judgeContext
     * @return
     */

    // todo 有些代码冗余，可以略微删减
    @Override
    public JudgeInfo doJudge(JudgeContext judgeContext) {

        // 获取用户程序执行后的JudgeInfo（time、memory、message等）
        JudgeInfo judgeInfo = judgeContext.getJudgeInfo();
        // 获取用户程序执行后的输出结果 ** 是执行用户代码后的
        List<String> outputList = judgeContext.getOutputList();
        // 获取该题目所需的输入用例
        List<String> inputList = judgeContext.getInputList();
        // 获取题目的详细信息
        Question question = judgeContext.getQuestion();
        // 获取这个题目的输入和输出用例
        List<JudgeCase> judgeCaseList = judgeContext.getJudgeCaseList();
        // 从判题结果中获取这段代码的空间消耗，如果为空默认为0
        Long memory = Optional.ofNullable(judgeInfo.getMemory()).orElse(0L);
        // 从判题结果中获取这段代码的时间消耗，如果为空默认为0
        Long time = Optional.ofNullable(judgeInfo.getTime()).orElse(0L);

        // 用来封装Java判题结果
        JudgeInfo judgeInfoResponse = new JudgeInfo();
        // 将执行后的结果设置到封装返回对象中
        judgeInfoResponse.setMemory(memory);
        judgeInfoResponse.setTime(time);

        // 设置题目提交结果，默认为成功
        JudgeInfoMessageEnum judgeInfoMessageEnum = JudgeInfoMessageEnum.ACCEPTED;

        // 如果执行后的输出用例数量与输入用例数量不相等
        if (outputList.size() != inputList.size()) {
            // 将判题结果设置为答案错误
            judgeInfoMessageEnum = JudgeInfoMessageEnum.WRONG_ANSWER;
            // 将结果设置到封装好的返回对象中
            judgeInfoResponse.setMessage(judgeInfoMessageEnum.getValue());
            // 返回
            return judgeInfoResponse;
        }

        // 遍历题目的所有用例，对比输出结果和所需的输出结果
        for (int i = 0; i < judgeCaseList.size(); i++) {
            JudgeCase judgeCase = judgeCaseList.get(i);
            // 如果用户执行完的输出结果和所需的输出用例不相同，即为答案错误
            if (!judgeCase.getOutput().equals(outputList.get(i))) {
                // 将判题结果设置为答案错误
                judgeInfoMessageEnum = JudgeInfoMessageEnum.WRONG_ANSWER;
                // 将结果设置到封装好的返回对象中
                judgeInfoResponse.setMessage(judgeInfoMessageEnum.getValue());
                // 返回
                return judgeInfoResponse;
            }
        }

        // 判断题目限制
        // 获取这个题目的要求（空间、时间、堆栈）等限制
        String judgeConfigStr = question.getJudgeConfig();
        // 先预处理成JudgeConfig对象（timeLimit、memoryLimit、stackLimit）
        JudgeConfig judgeConfig = JSONUtil.toBean(judgeConfigStr, JudgeConfig.class);
        // 得到题目的空间限制
        Long needMemoryLimit = judgeConfig.getMemoryLimit();
        // 得到题目的时间限制
        Long needTimeLimit = judgeConfig.getTimeLimit();

        // 判断用户程序执行后的空间是否满足空间限制
        if (memory > needMemoryLimit) {
            // 将判题结果设置为内存溢出
            judgeInfoMessageEnum = JudgeInfoMessageEnum.MEMORY_LIMIT_EXCEEDED;
            // 将结果设置到封装好的返回对象中
            judgeInfoResponse.setMessage(judgeInfoMessageEnum.getValue());
            // 返回
            return judgeInfoResponse;
        }


        if (time > needTimeLimit) {
            // 将判题结果设置为时间溢出
            judgeInfoMessageEnum = JudgeInfoMessageEnum.TIME_LIMIT_EXCEEDED;
            // 将结果设置到封装好的返回对象中
            judgeInfoResponse.setMessage(judgeInfoMessageEnum.getValue());
            // 返回
            return judgeInfoResponse;
        }
        judgeInfoResponse.setMessage(judgeInfoMessageEnum.getValue());
        return judgeInfoResponse;
    }
}
