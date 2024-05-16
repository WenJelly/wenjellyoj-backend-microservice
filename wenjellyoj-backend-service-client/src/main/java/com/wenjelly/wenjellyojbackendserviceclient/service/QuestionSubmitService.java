package com.wenjelly.wenjellyojbackendserviceclient.service;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.wenjelly.wenjellyojbacjendmodel.model.dto.questionsubmit.QuestionSubmitAddRequest;
import com.wenjelly.wenjellyojbacjendmodel.model.dto.questionsubmit.QuestionSubmitQueryRequest;
import com.wenjelly.wenjellyojbacjendmodel.model.entity.QuestionSubmit;
import com.wenjelly.wenjellyojbacjendmodel.model.entity.User;
import com.wenjelly.wenjellyojbacjendmodel.model.vo.QuestionSubmitVO;

/**
 * @description 针对表【question_submit(题目提交表)】的数据库操作Service
 */
public interface QuestionSubmitService extends IService<QuestionSubmit> {
    /**
     * 题目提交
     *
     * @param questionSubmitAddRequest
     * @param loginUser
     * @return
     */
    long doQuestionSubmit(QuestionSubmitAddRequest questionSubmitAddRequest, User loginUser);

    /**
     * 获取查询条件
     *
     * @param questionSubmitQueryRequest
     * @return
     */
    QueryWrapper<QuestionSubmit> getQueryWrapper(QuestionSubmitQueryRequest questionSubmitQueryRequest);

    /**
     * 获取题目封装
     *
     * @param questionSubmit
     * @param loginUser
     * @return
     */
    QuestionSubmitVO getQuestionSubmitVO(QuestionSubmit questionSubmit, User loginUser);

    /**
     * 分页获取题目封装
     *
     * @param questionSubmitPage
     * @param loginUser
     * @return
     */
    Page<QuestionSubmitVO> getQuestionSubmitVOPage(Page<QuestionSubmit> questionSubmitPage, User loginUser);


}
