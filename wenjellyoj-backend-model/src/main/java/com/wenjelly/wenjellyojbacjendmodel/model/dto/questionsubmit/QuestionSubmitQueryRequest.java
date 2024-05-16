package com.wenjelly.wenjellyojbacjendmodel.model.dto.questionsubmit;

import com.baomidou.mybatisplus.annotation.TableField;
import com.wenjelly.wenjellyojbackendcommon.common.PageRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * 查询请求
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class QuestionSubmitQueryRequest extends PageRequest implements Serializable {

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
    /**
     * 编程语言
     */
    private String language;
    /**
     * 提交状态
     */
    private Integer status;
    /**
     * 题目ID
     */
    private Long questionId;
    /**
     * 用户ID
     */
    private Long userId;
}