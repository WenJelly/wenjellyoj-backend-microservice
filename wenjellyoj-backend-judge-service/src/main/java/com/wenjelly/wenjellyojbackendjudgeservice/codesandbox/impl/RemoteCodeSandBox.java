package com.wenjelly.wenjellyojbackendjudgeservice.codesandbox.impl;


import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONUtil;
import com.alibaba.nacos.common.utils.StringUtils;
import com.wenjelly.wenjellyojbacjendmodel.model.dto.judge.ExecutedCodeRequest;
import com.wenjelly.wenjellyojbacjendmodel.model.dto.judge.ExecutedCodeResponse;
import com.wenjelly.wenjellyojbackendcommon.common.ErrorCode;
import com.wenjelly.wenjellyojbackendcommon.exception.BusinessException;
import com.wenjelly.wenjellyojbackendjudgeservice.codesandbox.CodeSandBox;

/**
 * 远程代码沙箱（实际调用接口的沙箱）
 */
public class RemoteCodeSandBox implements CodeSandBox {

    // 保证安全性，增加安全头
    public static final String AUTH_REQUEST_HEADER = "auth";
    public static final String AUTH_REQUEST_SECRET = "secretKey";

    @Override
    public ExecutedCodeResponse executedCode(ExecutedCodeRequest executeCodedRequest) {
        // 得到远程代码沙箱的响应url（目前跑在虚拟机上）
        String url = "http://192.168.174.128:8090/executeCode";
        // 如果时运行在本地的话
//        String url = "http://localhost:8090/executeCode";
        // 将请求参数封装成JSON字符串
        String json = JSONUtil.toJsonStr(executeCodedRequest);
        // 发送响应
        String responseStr = HttpUtil.createPost(url)
                // 设置表头
                .header(AUTH_REQUEST_HEADER, AUTH_REQUEST_SECRET)
                // 设置消息体
                .body(json)
                // 得到响应信息
                .execute()
                // 获取响应信息的消息体
                .body();
        // 如果消息体为空，则抛异常报错
        if (StringUtils.isBlank(responseStr)) {
            throw new BusinessException(ErrorCode.API_REQUEST_ERROR, "executeCode remoteSandbox error, message = " + responseStr);
        }
        // 将消息体封装成ExecutedCodeResponse对象
        return JSONUtil.toBean(responseStr, ExecutedCodeResponse.class);

    }


}
