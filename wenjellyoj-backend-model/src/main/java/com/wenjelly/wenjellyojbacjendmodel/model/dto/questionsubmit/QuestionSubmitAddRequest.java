package com.wenjelly.wenjellyojbacjendmodel.model.dto.questionsubmit;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.io.Serializable;

/**
 * 创建请求
 */
@Data
public class QuestionSubmitAddRequest implements Serializable {

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
    /**
     * 编程语言
     */
    private String language;
    /**
     * 用户代码
     */
    private String code;
    /**
     * 题目ID
     */
    private Long questionId;
}