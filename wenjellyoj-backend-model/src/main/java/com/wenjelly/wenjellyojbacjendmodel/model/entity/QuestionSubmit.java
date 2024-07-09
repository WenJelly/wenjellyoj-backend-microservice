package com.wenjelly.wenjellyojbacjendmodel.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 题目提交表
 *
 * @TableName question_submit
 */
@TableName(value = "question_submit")
@Data
public class QuestionSubmit implements Serializable {
    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
    /**
     * id
     */
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;
    /**
     * 题目 id
     */
    private Long questionId;
    /**
     * 编程语言
     */
    private String language;
    /**
     * 用户代码
     */
    private String code;
    /**
     * 判题信息(JSON 对象)
     */
    private String judgeInfo;
    /**
     * 题目提交数
     */
    private Integer submitNum;
    /**
     * 题目状态 (0-待判题 1-判题中 2-成功 3-失败)
     */
    private Integer status;
    /**
     * 创建用户 id
     */
    private Long userId;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 更新时间
     */
    private Date updateTime;

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        QuestionSubmit other = (QuestionSubmit) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
                && (this.getQuestionId() == null ? other.getQuestionId() == null : this.getQuestionId().equals(other.getQuestionId()))
                && (this.getLanguage() == null ? other.getLanguage() == null : this.getLanguage().equals(other.getLanguage()))
                && (this.getCode() == null ? other.getCode() == null : this.getCode().equals(other.getCode()))
                && (this.getJudgeInfo() == null ? other.getJudgeInfo() == null : this.getJudgeInfo().equals(other.getJudgeInfo()))
                && (this.getSubmitNum() == null ? other.getSubmitNum() == null : this.getSubmitNum().equals(other.getSubmitNum()))
                && (this.getStatus() == null ? other.getStatus() == null : this.getStatus().equals(other.getStatus()))
                && (this.getUserId() == null ? other.getUserId() == null : this.getUserId().equals(other.getUserId()))
                && (this.getCreateTime() == null ? other.getCreateTime() == null : this.getCreateTime().equals(other.getCreateTime()))
                && (this.getUpdateTime() == null ? other.getUpdateTime() == null : this.getUpdateTime().equals(other.getUpdateTime()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getQuestionId() == null) ? 0 : getQuestionId().hashCode());
        result = prime * result + ((getLanguage() == null) ? 0 : getLanguage().hashCode());
        result = prime * result + ((getCode() == null) ? 0 : getCode().hashCode());
        result = prime * result + ((getJudgeInfo() == null) ? 0 : getJudgeInfo().hashCode());
        result = prime * result + ((getSubmitNum() == null) ? 0 : getSubmitNum().hashCode());
        result = prime * result + ((getStatus() == null) ? 0 : getStatus().hashCode());
        result = prime * result + ((getUserId() == null) ? 0 : getUserId().hashCode());
        result = prime * result + ((getCreateTime() == null) ? 0 : getCreateTime().hashCode());
        result = prime * result + ((getUpdateTime() == null) ? 0 : getUpdateTime().hashCode());
        return result;
    }

    @Override
    public String toString() {
        String sb = getClass().getSimpleName() +
                " [" +
                "Hash = " + hashCode() +
                ", id=" + id +
                ", questionId=" + questionId +
                ", language=" + language +
                ", code=" + code +
                ", judgeInfo=" + judgeInfo +
                ", submitNum=" + submitNum +
                ", status=" + status +
                ", userId=" + userId +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", serialVersionUID=" + serialVersionUID +
                "]";
        return sb;
    }
}