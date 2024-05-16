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
public class ExecutedCodeRequest {

    private List<String> inputList;
    private String code;
    private String language;

}
