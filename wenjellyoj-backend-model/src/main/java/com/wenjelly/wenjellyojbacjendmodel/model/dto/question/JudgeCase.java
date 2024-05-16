package com.wenjelly.wenjellyojbacjendmodel.model.dto.question;

/*
 * @time 2023/11/18 19:31
 * @project wenjellyoj-backend
 * @author WenJelly
 */

import lombok.Data;

/**
 * 题目用例
 */

@Data
public class JudgeCase {

    /**
     * 输入用例
     */
    private String input;

    /**
     * 输出用例
     */
    private String output;

}
