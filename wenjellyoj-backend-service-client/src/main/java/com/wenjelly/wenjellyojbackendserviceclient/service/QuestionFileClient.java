package com.wenjelly.wenjellyojbackendserviceclient.service;


import com.wenjelly.wenjellyojbacjendmodel.model.entity.Question;
import com.wenjelly.wenjellyojbacjendmodel.model.entity.QuestionSubmit;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 请求转发 -> 题目模块
 * 负责将接收到的接口使用请求转发给 题目模块 / 内部处理
 */
@FeignClient(name = "wenjellyoj-backend-question-service", path = "/api/question/inner")
public interface QuestionFileClient {

    @GetMapping("/get/id")
    Question getQuestionById(@RequestParam("questionId") long questionId);

    @GetMapping("/question_submit/get/id")
    QuestionSubmit getQuestionSubmitById(@RequestParam("questionSubmitId") long questionSubmitId);

    @PostMapping("/question_submit/update/id")
    boolean updateById(@RequestBody QuestionSubmit questionSubmit);


}
