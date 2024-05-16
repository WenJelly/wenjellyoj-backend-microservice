package com.wenjelly.wenjellyojbackendjudgeservice;


import com.wenjelly.wenjellyojbacjendmodel.model.dto.judge.JudgeInfo;
import com.wenjelly.wenjellyojbacjendmodel.model.entity.QuestionSubmit;
import com.wenjelly.wenjellyojbackendjudgeservice.strategy.DefaultJudgeStrategy;
import com.wenjelly.wenjellyojbackendjudgeservice.strategy.JavaLanguageJudgeStrategy;
import com.wenjelly.wenjellyojbackendjudgeservice.strategy.JudgeContext;
import com.wenjelly.wenjellyojbackendjudgeservice.strategy.JudgeStrategy;
import org.springframework.stereotype.Service;

/**
 * 判题管理、简化调用
 */
@Service
public class JudgeManage {

    /**
     * 执行判题
     *
     * @param judgeContext
     * @return
     */
    JudgeInfo doJudge(JudgeContext judgeContext) {
        // 获得问题提交记录信息
        QuestionSubmit questionSubmit = judgeContext.getQuestionSubmit();
        // 获得用户使用的编程语言
        String language = questionSubmit.getLanguage();
        // 创建一个默认的判题服务
        JudgeStrategy judgeStrategy = new DefaultJudgeStrategy();
        // 根据语言来创建对应的判题服务
        if ("java".equals(language)) {
            // 创建java判题服务
            judgeStrategy = new JavaLanguageJudgeStrategy();
        } else if ("cpp".equals(language)) {

        } else if ("golang".equals(language)) {

        }
        // 使用对应的语言判题服务进行判题
        return judgeStrategy.doJudge(judgeContext);
    }
}
