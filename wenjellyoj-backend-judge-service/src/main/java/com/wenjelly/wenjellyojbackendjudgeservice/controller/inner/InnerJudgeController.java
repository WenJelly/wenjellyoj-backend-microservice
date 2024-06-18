package com.wenjelly.wenjellyojbackendjudgeservice.controller.inner;

/*
 * @time 2024/6/18 14:12
 * @package com.wenjelly.wenjellyojbackenduserservice.controller.inner
 * @project wenjellyoj-backend-microservice
 * @author WenJelly
 */

import com.wenjelly.wenjellyojbacjendmodel.model.entity.QuestionSubmit;
import com.wenjelly.wenjellyojbackendjudgeservice.JudgeService;
import com.wenjelly.wenjellyojbackendserviceclient.service.JudgeFeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 内部调用，处理中转过来的请求
 */
@RestController
@RequestMapping("/inner")
public class InnerJudgeController implements JudgeFeignClient {

    @Resource
    private JudgeService judgeService;

    @Override
    public QuestionSubmit doJudge(long questionSubmitId) {
        return judgeService.doJudge(questionSubmitId);
    }
}
