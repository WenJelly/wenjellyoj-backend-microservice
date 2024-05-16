package com.wenjelly.wenjellyojbackendjudgeservice.strategy;


import com.wenjelly.wenjellyojbacjendmodel.model.dto.judge.JudgeInfo;
import com.wenjelly.wenjellyojbacjendmodel.model.dto.question.JudgeCase;
import com.wenjelly.wenjellyojbacjendmodel.model.entity.Question;
import com.wenjelly.wenjellyojbacjendmodel.model.entity.QuestionSubmit;
import lombok.Data;

import java.util.List;

/**
 * 上下文（用于定于在策略中传递的参数）
 */
@Data
public class JudgeContext {

    // 判题信息
    JudgeInfo judgeInfo;
    // 输入用例
    private List<String> inputList;
    // 输出用例
    private List<String> outputList;
    // 问题详细
    private Question question;
    // 判题的配置
    private List<JudgeCase> judgeCaseList;
    // 本条判题的题目提交记录
    private QuestionSubmit questionSubmit;


}
