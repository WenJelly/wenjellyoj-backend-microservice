package com.wenjelly.wenjellyojbacjendmodel.model.dto.question;

/*
 * @time 2023/11/18 19:33
 * @project wenjellyoj-backend
 * @author WenJelly
 */

import lombok.Data;

/**
 * 题目配置
 */
@Data
public class JudgeConfig {
    /**
     * 时间限制(ms)
     */
    private Long timeLimit;

    /**
     * 内存限制(kb)
     */
    private Long memoryLimit;

    /**
     * 堆栈限制(kb)
     */
    private Long stackLimit;
}
