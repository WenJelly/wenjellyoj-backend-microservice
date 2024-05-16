package com.wenjelly.wenjellyojbackendjudgeservice.codesandbox;


import com.wenjelly.wenjellyojbackendjudgeservice.codesandbox.impl.ExampleCodeSandBox;
import com.wenjelly.wenjellyojbackendjudgeservice.codesandbox.impl.RemoteCodeSandBox;
import com.wenjelly.wenjellyojbackendjudgeservice.codesandbox.impl.ThirdPartyCodeBox;

/**
 * 代码沙箱工厂（根据字符串参数创建指定的代码沙箱实例）
 */

public class CodeSandBoxFactory {

    public static CodeSandBox newInstance(String type) {
        switch (type) {
            case "example":
                return new ExampleCodeSandBox();
            case "remote":
                return new RemoteCodeSandBox();
            case "thirdParty":
                return new ThirdPartyCodeBox();
            default:
                // 如果用户传的不是上面三个，就返回一个默认值
                return new ExampleCodeSandBox();
        }
    }
}
