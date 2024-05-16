package com.wenjelly.wenjellyojbackendjudgeservice.strategy;


import com.wenjelly.wenjellyojbacjendmodel.model.dto.judge.JudgeInfo;

/**
 * 判题策略
 */
public interface JudgeStrategy {

    /**
     * 执行判题
     *
     * @param judgeContext
     * @return
     */
    JudgeInfo doJudge(JudgeContext judgeContext);

}
