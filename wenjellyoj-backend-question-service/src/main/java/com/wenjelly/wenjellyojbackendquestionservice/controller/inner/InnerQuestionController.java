package com.wenjelly.wenjellyojbackendquestionservice.controller.inner;

/*
 * @time 2024/6/18 14:12
 * @package com.wenjelly.wenjellyojbackenduserservice.controller.inner
 * @project wenjellyoj-backend-microservice
 * @author WenJelly
 */

import com.wenjelly.wenjellyojbacjendmodel.model.entity.Question;
import com.wenjelly.wenjellyojbacjendmodel.model.entity.QuestionSubmit;
import com.wenjelly.wenjellyojbackendquestionservice.service.QuestionService;
import com.wenjelly.wenjellyojbackendquestionservice.service.QuestionSubmitService;
import com.wenjelly.wenjellyojbackendserviceclient.service.QuestionFileClient;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 内部调用，处理中转过来的请求
 */
@RestController
@RequestMapping("/inner")
public class InnerQuestionController implements QuestionFileClient {

    @Resource
    private QuestionService questionService;

    @Resource
    private QuestionSubmitService questionSubmitService;


    @Override
    @GetMapping("/get/id")
    public Question getQuestionById(@RequestParam("questionId") long questionId) {
        return questionService.getById(questionId);
    }

    @Override
    @GetMapping("/question_submit/get/id")
    public QuestionSubmit getQuestionSubmitById(@RequestParam("questionSubmitId") long questionSubmitId) {
        return questionSubmitService.getById(questionSubmitId);
    }

    @Override
    @PostMapping("/question_submit/update/id")
    public boolean updateById(@RequestBody QuestionSubmit questionSubmit) {
        return questionSubmitService.updateById(questionSubmit);
    }
}
