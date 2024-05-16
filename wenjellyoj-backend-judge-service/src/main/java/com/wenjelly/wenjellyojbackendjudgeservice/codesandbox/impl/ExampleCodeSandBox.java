package com.wenjelly.wenjellyojbackendjudgeservice.codesandbox.impl;


import com.wenjelly.wenjellyojbacjendmodel.model.dto.judge.ExecutedCodeRequest;
import com.wenjelly.wenjellyojbacjendmodel.model.dto.judge.ExecutedCodeResponse;
import com.wenjelly.wenjellyojbacjendmodel.model.dto.judge.JudgeInfo;
import com.wenjelly.wenjellyojbacjendmodel.model.enums.JudgeInfoMessageEnum;
import com.wenjelly.wenjellyojbacjendmodel.model.enums.QuestionSubmitStatusEnum;
import com.wenjelly.wenjellyojbackendjudgeservice.codesandbox.CodeSandBox;

import java.util.List;

/**
 * 示例代码沙箱（仅为了跑通流程）
 */
public class ExampleCodeSandBox implements CodeSandBox {
    @Override
    public ExecutedCodeResponse executedCode(ExecutedCodeRequest executedCodeRequest) {
        // 获取请求到的参数
        List<String> inputList = executedCodeRequest.getInputList();
        String code = executedCodeRequest.getCode();
        String language = executedCodeRequest.getLanguage();

        ExecutedCodeResponse executedCodeResponse = new ExecutedCodeResponse();
        executedCodeResponse.setOutputList(inputList);
        executedCodeResponse.setMessage("执行成功");
        executedCodeResponse.setStatus(QuestionSubmitStatusEnum.SUCCESS.getValue());
        JudgeInfo judgeInfo = new JudgeInfo();
        judgeInfo.setMessage(JudgeInfoMessageEnum.ACCEPTED.getValue());
        judgeInfo.setMemory(100L);
        judgeInfo.setTime(100L);
        executedCodeResponse.setJudgeInfo(judgeInfo);

        return executedCodeResponse;
    }
}
