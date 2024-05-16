package com.wenjelly.wenjellyojbacjendmodel.model.dto.questionsubmit;

/*
 * @time 2023/11/18 19:33
 * @project wenjellyoj-backend
 * @author WenJelly
 */

import lombok.Data;

/**
 * 判题信息
 */
@Data
public class JudgeInfo {
    /**
     * 程序执行信息(ms)
     */
    private String message;

    /**
     * 消耗时间(kb)
     */
    private Long memory;

    /**
     * 消耗内存(kb)
     */
    private Long time;
}
