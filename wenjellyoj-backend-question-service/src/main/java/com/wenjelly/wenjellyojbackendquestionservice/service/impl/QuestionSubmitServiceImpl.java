package com.wenjelly.wenjellyojbackendquestionservice.service.impl;


import com.alibaba.nacos.common.utils.CollectionUtils;
import com.alibaba.nacos.common.utils.StringUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wenjelly.wenjellyojbacjendmodel.model.dto.questionsubmit.QuestionSubmitAddRequest;
import com.wenjelly.wenjellyojbacjendmodel.model.dto.questionsubmit.QuestionSubmitQueryRequest;
import com.wenjelly.wenjellyojbacjendmodel.model.entity.Question;
import com.wenjelly.wenjellyojbacjendmodel.model.entity.QuestionSubmit;
import com.wenjelly.wenjellyojbacjendmodel.model.entity.User;
import com.wenjelly.wenjellyojbacjendmodel.model.enums.QuestionSubmitLanguageEnum;
import com.wenjelly.wenjellyojbacjendmodel.model.enums.QuestionSubmitStatusEnum;
import com.wenjelly.wenjellyojbacjendmodel.model.vo.QuestionSubmitVO;
import com.wenjelly.wenjellyojbacjendmodel.model.vo.QuestionVO;
import com.wenjelly.wenjellyojbacjendmodel.model.vo.UserVO;
import com.wenjelly.wenjellyojbackendcommon.common.ErrorCode;
import com.wenjelly.wenjellyojbackendcommon.constant.CommonConstant;
import com.wenjelly.wenjellyojbackendcommon.exception.BusinessException;
import com.wenjelly.wenjellyojbackendcommon.utils.SqlUtils;
import com.wenjelly.wenjellyojbackendquestionservice.RabbitMQ.MessageSend;
import com.wenjelly.wenjellyojbackendquestionservice.mapper.QuestionSubmitMapper;
import com.wenjelly.wenjellyojbackendquestionservice.service.QuestionService;
import com.wenjelly.wenjellyojbackendquestionservice.service.QuestionSubmitService;
import com.wenjelly.wenjellyojbackendserviceclient.service.UserFeignClient;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class QuestionSubmitServiceImpl extends ServiceImpl<QuestionSubmitMapper, QuestionSubmit>
        implements QuestionSubmitService {

    @Resource
    private QuestionService questionService;
    @Resource
    private UserFeignClient userFeignClient;
    @Resource
    private MessageSend messageSend;

    /**
     * 题目提交
     *
     * @param questionSubmitAddRequest 题目提交请求对象
     * @param loginUser                当前登录用户
     * @return 提交记录的id
     */
    @Override
    public long doQuestionSubmit(QuestionSubmitAddRequest questionSubmitAddRequest, User loginUser) {

        // 得到题目的id
        Long questionId = questionSubmitAddRequest.getQuestionId();
        // 判断编程语言是否合法
        String language = questionSubmitAddRequest.getLanguage();
        QuestionSubmitLanguageEnum enumByValue = QuestionSubmitLanguageEnum.getEnumByValue(language);
        // 如果不合法直接报异常
        if (enumByValue == null) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "编程语言错误");
        }

        // 根据题目的id去获取题目
        Question question = questionService.getById(questionId);
        // 如果题目为空，则报错
        if (question == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR);
        }
        // 是否已题目提交
        long userId = loginUser.getId();

        // 在原本的提交数上+1
        question.setSubmitNum(question.getSubmitNum() + 1);
        // todo 更新提交数目，可以等到结果出来一并提交，如果运行结果为成功的话、成功数也要+1
        boolean update = questionService.updateById(question);
        if (!update) {
            System.out.println("提交数更新失败");
        }

        // 每个用户串行题目提交
        QuestionSubmit questionSubmit = new QuestionSubmit();
        questionSubmit.setUserId(userId);
        questionSubmit.setQuestionId(questionId);
        questionSubmit.setCode(questionSubmitAddRequest.getCode());
        questionSubmit.setLanguage(language);
        // 先设置该条提交记录的初始状态
        // 状态设置为等待判题----0
        questionSubmit.setStatus(QuestionSubmitStatusEnum.WAITING.getValue());
        // 判题结果设置为空----{}
        questionSubmit.setJudgeInfo("{}");
        boolean save = this.save(questionSubmit);
        if (!save) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "数据插入失败");
        }

        // 获取提交记录id用来传递给判题服务模块
        Long questionSubmitId = questionSubmit.getId();

        messageSend.doMessage(questionSubmitId);

        // 异步执行判题服务模块
//        CompletableFuture.runAsync(() -> {
//            judgeFeignClient.doJudge(questionSubmitId);
//        });

//        CompletableFuture<Void> voidCompletableFuture = CompletableFuture.runAsync(() -> {
//            judgeService.doJudge(questionSubmitId);
//        });
        // 设置超时时间为2秒，并在超时时执行特定操作
//        voidCompletableFuture.orTimeout(2000, TimeUnit.MILLISECONDS);
//
//        // 等待异步操作完成
//        try {
//            voidCompletableFuture.get(); // 这里会抛出 TimeoutException，因为设置了2秒的超时时间，但实际操作需要3秒
//        } catch (InterruptedException | ExecutionException e) {
//            // 如果抛异常了说明超时了
//            // 设置判题状态
//            // 状态设置为判题失败----3
//            questionSubmit.setStatus(QuestionSubmitStatusEnum.FAILED.getValue());
//        }

        return questionSubmitId;
    }

    /**
     * 获取查询包装类（用户根据那些字段查询，根据前端传来的请求对象，得到Mybatis框架支持的查询QueryWrapper类）
     *
     * @param questionSubmitQueryRequest
     * @return
     */
    @Override
    public QueryWrapper<QuestionSubmit> getQueryWrapper(QuestionSubmitQueryRequest questionSubmitQueryRequest) {
        QueryWrapper<QuestionSubmit> queryWrapper = new QueryWrapper<>();
        if (questionSubmitQueryRequest == null) {
            return queryWrapper;
        }
        String language = questionSubmitQueryRequest.getLanguage();
        Integer status = questionSubmitQueryRequest.getStatus();
        Long questionId = questionSubmitQueryRequest.getQuestionId();
        Long userId = questionSubmitQueryRequest.getUserId();
        // todo 不知道什么意思
        String sortField = questionSubmitQueryRequest.getSortField();
        String sortOrder = questionSubmitQueryRequest.getSortOrder();

        // 拼接查询条件
        queryWrapper.eq(StringUtils.isNotBlank(language), "language", language);
        queryWrapper.eq(ObjectUtils.isNotEmpty(userId), "userId", userId);
        queryWrapper.eq(ObjectUtils.isNotEmpty(questionId), "questionId", questionId);
        queryWrapper.eq(QuestionSubmitStatusEnum.getEnumByValue(status) != null, "status", status);
        queryWrapper.orderBy(SqlUtils.validSortField(sortField), sortOrder.equals(CommonConstant.SORT_ORDER_ASC),
                sortField);
        System.out.println("========================================================");
        System.out.println(queryWrapper);
        return queryWrapper;
    }

    @Override
    public QuestionSubmitVO getQuestionSubmitVO(QuestionSubmit questionSubmit, User loginUser) {

        // 得到这条记录的具体问题信息
        Long questionId = questionSubmit.getQuestionId();
        Question question = questionService.getById(questionId);
        QuestionVO questionVO = QuestionVO.objToVo(question);
        // 得到提交这条记录的具体用户信息
        Long userId1 = questionSubmit.getUserId();
        User user = userFeignClient.getById(userId1);
        UserVO userVO = UserVO.objToVo(user);


        QuestionSubmitVO questionSubmitVO = QuestionSubmitVO.objToVo(questionSubmit);
        // 脱敏：仅本人和管理员可以看见（提交userId 和登录用户 id 不同）提交的代码
        long userId = loginUser.getId();
        // 处理脱敏
        if (userId != questionSubmit.getUserId() && userFeignClient.isAdmin(loginUser)) {
            questionSubmitVO.setCode(null);
        }

        // 将具体问题VO和用户VO设置进questionSubmit中
        questionSubmitVO.setUserVO(userVO);
        questionSubmitVO.setQuestionVO(questionVO);
        return questionSubmitVO;
    }

    @Override
    public Page<QuestionSubmitVO> getQuestionSubmitVOPage(Page<QuestionSubmit> questionSubmitPage, User loginUser) {
        List<QuestionSubmit> questionSubmitList = questionSubmitPage.getRecords();
        Page<QuestionSubmitVO> questionSubmitSubmitVOPage = new Page<>(questionSubmitPage.getCurrent(), questionSubmitPage.getSize(), questionSubmitPage.getTotal());
        if (CollectionUtils.isEmpty(questionSubmitList)) {
            return questionSubmitSubmitVOPage;
        }

        List<QuestionSubmitVO> questionSubmitVOList = questionSubmitList.stream()
                .map(questionSubmit -> getQuestionSubmitVO(questionSubmit, loginUser))
                .collect(Collectors.toList());

        System.out.println("test1===================================");
        System.out.println(questionSubmitVOList);
        questionSubmitSubmitVOPage.setRecords(questionSubmitVOList);
        return questionSubmitSubmitVOPage;
    }
}




