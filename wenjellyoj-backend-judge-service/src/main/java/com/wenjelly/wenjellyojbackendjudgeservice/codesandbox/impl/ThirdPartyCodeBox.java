package com.wenjelly.wenjellyojbackendjudgeservice.codesandbox.impl;


import com.wenjelly.wenjellyojbacjendmodel.model.dto.judge.ExecutedCodeRequest;
import com.wenjelly.wenjellyojbacjendmodel.model.dto.judge.ExecutedCodeResponse;
import com.wenjelly.wenjellyojbackendjudgeservice.codesandbox.CodeSandBox;

/**
 * 第三方代码沙箱(调用网上现成的代码沙箱)
 */
public class ThirdPartyCodeBox implements CodeSandBox {
    @Override
    public ExecutedCodeResponse executedCode(ExecutedCodeRequest executedCodeRequest) {
        System.out.println("第三方代码沙箱");
        return null;
    }
}
