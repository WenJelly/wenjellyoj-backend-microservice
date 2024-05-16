package com.wenjelly.wenjellyojbackendserviceclient.service;


import com.wenjelly.wenjellyojbacjendmodel.model.entity.QuestionSubmit;

/**
 * 判题服务的流程
 * 1. 传入题目的提交id，获取到对应的题目、提交信息（包含代码、编程语言）
 * 2. 如果题目的提交状态不为等待中，就不用重复执行了
 * 3. 调用沙箱，获取到执行结果
 * 4. 根据沙箱的执行结果，设置题目判题状态和信息
 */
public interface JudgeService {

    /**
     * 判题
     *
     * @param questionSubmitId
     * @return
     */
    QuestionSubmit doJudge(long questionSubmitId);

}
