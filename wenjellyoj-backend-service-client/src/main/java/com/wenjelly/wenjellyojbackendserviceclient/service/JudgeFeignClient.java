package com.wenjelly.wenjellyojbackendserviceclient.service;


import com.wenjelly.wenjellyojbacjendmodel.model.entity.QuestionSubmit;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 请求转发 -> 判题模块
 * 负责将接收到的接口使用请求转发给 判题模块 / 内部处理
 */
@FeignClient(name = "wenjellyoj-backend-judge-service", path = "/api/do/inner")
public interface JudgeFeignClient {

    @GetMapping("/do")
    QuestionSubmit doJudge(@RequestParam("questionSubmitId") long questionSubmitId);

}
