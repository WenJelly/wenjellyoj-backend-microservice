package com.wenjelly.wenjellyojbackendjudgeservice.codesandbox;


import com.wenjelly.wenjellyojbacjendmodel.model.dto.judge.ExecutedCodeRequest;
import com.wenjelly.wenjellyojbacjendmodel.model.dto.judge.ExecutedCodeResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
// 所有参数构造器
@AllArgsConstructor
public class CodeSandBoxProxy implements CodeSandBox {

    private CodeSandBox codeSandBox;

    @Override
    public ExecutedCodeResponse executedCode(ExecutedCodeRequest executedCodeRequest) {
        // 在调用前做一些事情
        log.info("代码沙箱请求信息:" + executedCodeRequest.toString());
        ExecutedCodeResponse executedCodeResponse = codeSandBox.executedCode(executedCodeRequest);
        log.info("代码沙箱响应信息:" + executedCodeResponse.toString());
        return executedCodeResponse;
    }
}
