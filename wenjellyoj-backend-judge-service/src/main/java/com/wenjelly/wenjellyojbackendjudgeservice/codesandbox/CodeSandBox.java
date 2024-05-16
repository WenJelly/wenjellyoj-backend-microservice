package com.wenjelly.wenjellyojbackendjudgeservice.codesandbox;


import com.wenjelly.wenjellyojbacjendmodel.model.dto.judge.ExecutedCodeRequest;
import com.wenjelly.wenjellyojbacjendmodel.model.dto.judge.ExecutedCodeResponse;

public interface CodeSandBox {

    /**
     * 执行代码
     *
     * @param executedCodeRequest
     * @return
     */
    ExecutedCodeResponse executedCode(ExecutedCodeRequest executedCodeRequest);
}
