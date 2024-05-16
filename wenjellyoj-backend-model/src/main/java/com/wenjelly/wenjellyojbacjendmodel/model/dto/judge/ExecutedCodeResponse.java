package com.wenjelly.wenjellyojbacjendmodel.model.dto.judge;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/*
 * @time 2023/11/24 1:00
 * @project wenjellyoj-backend
 * @author WenJelly
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExecutedCodeResponse {

    private List<String> outputList;
    /**
     * 执行信息
     */
    private String message;
    /**
     * 执行状态
     */
    private Integer status;

    /**
     * 判题信息
     */
    private JudgeInfo judgeInfo;

}
