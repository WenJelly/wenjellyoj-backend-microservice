package com.wenjelly.wenjellyojbackendjudgeservice;


import cn.hutool.json.JSONUtil;
import com.wenjelly.wenjellyojbacjendmodel.model.dto.judge.ExecutedCodeRequest;
import com.wenjelly.wenjellyojbacjendmodel.model.dto.judge.ExecutedCodeResponse;
import com.wenjelly.wenjellyojbacjendmodel.model.dto.judge.JudgeInfo;
import com.wenjelly.wenjellyojbacjendmodel.model.dto.question.JudgeCase;
import com.wenjelly.wenjellyojbacjendmodel.model.entity.Question;
import com.wenjelly.wenjellyojbacjendmodel.model.entity.QuestionSubmit;
import com.wenjelly.wenjellyojbacjendmodel.model.enums.QuestionSubmitStatusEnum;
import com.wenjelly.wenjellyojbackendcommon.common.ErrorCode;
import com.wenjelly.wenjellyojbackendcommon.exception.BusinessException;
import com.wenjelly.wenjellyojbackendjudgeservice.codesandbox.CodeSandBox;
import com.wenjelly.wenjellyojbackendjudgeservice.codesandbox.CodeSandBoxFactory;
import com.wenjelly.wenjellyojbackendjudgeservice.codesandbox.CodeSandBoxProxy;
import com.wenjelly.wenjellyojbackendjudgeservice.strategy.JudgeContext;
import com.wenjelly.wenjellyojbackendserviceclient.service.QuestionFileClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class JudgeServiceImpl implements JudgeService {

    @Resource
    private QuestionFileClient questionFileClient;
    @Resource
    private JudgeManage judgeManage;

    @Value("${codesandbox.type:example}")
    private String type;

    // 用于存放判题结果信息
    private JudgeInfo judgeInfo = new JudgeInfo();

    @Override
    public QuestionSubmit doJudge(long questionSubmitId) {
        // 1. 传入题目的提交id，获取到对应的题目、提交信息（包含代码、编程语言）
        QuestionSubmit questionSubmit = questionFileClient.getQuestionSubmitById(questionSubmitId);

        // 如果提交不存在，抛异常
        if (questionSubmit == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR, "提交信息不存在");
        }
        // 根据提交id获取题目
        Long questionId = questionSubmit.getQuestionId();
        Question question = questionFileClient.getQuestionById(questionId);

        // 如果题目不存在，抛异常
        if (question == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR, "题目不存在");
        }

        // 2.如果题目的提交状态不为等待中，就不用重复执行了
        if (!questionSubmit.getStatus().equals(QuestionSubmitStatusEnum.WAITING.getValue())) {
            throw new BusinessException(ErrorCode.OPERATION_ERROR, "题目正在判题中");
        }

        // 3.更改判题状态
        QuestionSubmit questionSubmitUpdate = new QuestionSubmit();
        // 设置id为了根据id来进行修改
        questionSubmitUpdate.setId(questionSubmitId);
        // 更改判题状态，设置为判题中----1
        questionSubmitUpdate.setStatus(QuestionSubmitStatusEnum.RUNNING.getValue());
        // 修改数据库
        boolean update = questionFileClient.updateById(questionSubmitUpdate);
        // 修改失败
        if (!update) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "题目状态更新错误");
        }
        // 4.调用代码沙箱，获取执行结果
        // 代码沙箱的实例创建用了工厂模式和代理模式
        CodeSandBox codeSandBox = CodeSandBoxFactory.newInstance(type);
        codeSandBox = new CodeSandBoxProxy(codeSandBox);
        // 获取提交的代码
        String code = questionSubmit.getCode();
        // 获取提交的编程语言
        String language = questionSubmit.getLanguage();
        // 获取输入用例
        String judgeCaseStr = question.getJudgeCase();

        // 将Question里面的JudgeCase判题用例（String）封装成JudgeCase对象（input、output）
        List<JudgeCase> judgeCaseList = JSONUtil.toList(judgeCaseStr, JudgeCase.class);
        // 得到封装好的封装成JudgeCase对象里面的input
        List<String> inputList = judgeCaseList.stream().map(JudgeCase::getInput).collect(Collectors.toList());
        // ExecutedCodeRequest用来封装传递给代码沙箱的参数（code、language、input）
        ExecutedCodeRequest executedCodeRequest = ExecutedCodeRequest.builder()
                .code(code)
                .language(language)
                .inputList(inputList)
                .build();
        // 执行代码沙箱--->executedCode并得到代码沙箱的返回结果
        ExecutedCodeResponse executedCodeResponse = codeSandBox.executedCode(executedCodeRequest);

        /**
         * executedCodeResponse返回结果
         * outputList：如果程序编译、运行无异常，会有输出结果
         * message：暂时有：编译失败、运行失败、运行成功
         * status：未在代码沙箱中设置
         * judgeInfo：
         *      time：程序执行的最大时间
         *      memory：程序执行的最大内存
         */

        // 5.执行判题服务，返回判题结果
        // 如果executedCodeResponse返回结果中message等于编译错误的话，不再执行判题服务，直接将判题结果设置为编译错误
        if ("编译错误".equals(executedCodeResponse.getMessage())) {
            // 设置判题结果，修改为编译错误
            judgeInfo.setMessage("编译错误");
            judgeInfo.setTime(0L);
            judgeInfo.setMemory(0L);
        }// 如果executedCodeResponse返回结果中message等于运行的话，不再执行判题服务，直接将判题结果设置为运行错误
        else if ("运行错误".equals(executedCodeResponse.getMessage())) {
            // 设置判题结果，修改为运行错误
            judgeInfo.setMessage("运行错误");
            judgeInfo.setTime(0L);
            judgeInfo.setMemory(0L);
        } else {
            // JudgeContext用于包装判题服务所需要的参数，简化传递
            JudgeContext judgeContext = new JudgeContext();
            // 获得程序执行结果的JudgeInfo信息（所用时间、空间等），是执行完用户代码后所得的
            judgeContext.setJudgeInfo(executedCodeResponse.getJudgeInfo());
            // 获得程序执行结果中的输出结果
            judgeContext.setOutputList(executedCodeResponse.getOutputList());
            // 设置输入用例
            judgeContext.setInputList(inputList);
            // 设置问题详细
            judgeContext.setQuestion(question);
            // JudgeCaseList面包含了题目所需输入用例和所需的输出结果，是题目所需的，即正确答案。而不是用户执行后的输出结果
            judgeContext.setJudgeCaseList(judgeCaseList);
            // 设置这条提交记录
            judgeContext.setQuestionSubmit(questionSubmit);
            // 将封装好的参数传递给沙箱并执行判题
            judgeInfo = judgeManage.doJudge(judgeContext);
        }

        /**
         * 判题完成后返回的judgeInfo包含以下内容
         * 1. message：判题结果：答案错误、答案正常、内存溢出、超时
         * 2. time：ms
         * 3. memory：kb
         */

        // 设置判题状态，修改为判题完成----2
        questionSubmitUpdate.setStatus(QuestionSubmitStatusEnum.SUCCESS.getValue());
        // 设置将判题结果信息
        questionSubmitUpdate.setJudgeInfo(JSONUtil.toJsonStr(judgeInfo));
        // 最后一步，修改信息，并更新数据库
        update = questionFileClient.updateById(questionSubmitUpdate);
        if (!update) {
            // 这里可能因为异步传输的原因，好像没有输出throw，尝试用控制台输出
            System.out.println(ErrorCode.SYSTEM_ERROR.getMessage());
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "题目状态更新错误");
        }
        QuestionSubmit questionSubmitResult = questionFileClient.getQuestionSubmitById(questionId);
        return questionSubmitResult;
    }
}
