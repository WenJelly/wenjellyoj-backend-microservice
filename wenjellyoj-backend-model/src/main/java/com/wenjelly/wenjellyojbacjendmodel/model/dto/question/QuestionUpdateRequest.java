package com.wenjelly.wenjellyojbacjendmodel.model.dto.question;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 更新请求
 */
@Data
public class QuestionUpdateRequest implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * id
     */
    @TableId(type = IdType.AUTO)
    private Long id;
    /**
     * 标题
     */
    private String title;
    /**
     * 内容
     */
    private String content;
    /**
     * 题目标签（json 数组）
     */
    private List<String> tags;
    /**
     * 题目答案
     */
    private String answer;
    /**
     * 判题用例,(JSON数组)
     */
    private List<JudgeCase> judgeCase;

//    /**
//     * 提交数
//     */
//    private Integer submitNum;

//    /**
//     * 通过数
//     */
//    private Integer acceptedNum;
    /**
     * 判题配置,(JSON对象)
     */
    private JudgeConfig judgeConfig;
}