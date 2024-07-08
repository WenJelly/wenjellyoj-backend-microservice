package com.wenjelly.wenjellyojbacjendmodel.model.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 题目
 *
 * @TableName question
 */
@TableName(value = "question")
@Data
public class Question implements Serializable {
    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
    /**
     * id
     */
    @TableId(type = IdType.ASSIGN_ID)
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
    private String tags;
    /**
     * 题目答案
     */
    private String answer;
    /**
     * 题目提交数
     */
    private Integer submitNum;
    /**
     * 题目通过数
     */
    private Integer acceptedNum;
    /**
     * 判题用例,(JSON数组)
     */
    private String judgeCase;
    /**
     * 判题配置,(JSON对象)
     */
    private String judgeConfig;
    /**
     * 点赞数
     */
    private Integer thumbNum;
    /**
     * 收藏数
     */
    private Integer favourNum;
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
    /**
     * 是否删除
     */
    @TableLogic
    private Integer isDelete;

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
        Question other = (Question) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
                && (this.getTitle() == null ? other.getTitle() == null : this.getTitle().equals(other.getTitle()))
                && (this.getContent() == null ? other.getContent() == null : this.getContent().equals(other.getContent()))
                && (this.getTags() == null ? other.getTags() == null : this.getTags().equals(other.getTags()))
                && (this.getAnswer() == null ? other.getAnswer() == null : this.getAnswer().equals(other.getAnswer()))
                && (this.getSubmitNum() == null ? other.getSubmitNum() == null : this.getSubmitNum().equals(other.getSubmitNum()))
                && (this.getAcceptedNum() == null ? other.getAcceptedNum() == null : this.getAcceptedNum().equals(other.getAcceptedNum()))
                && (this.getJudgeCase() == null ? other.getJudgeCase() == null : this.getJudgeCase().equals(other.getJudgeCase()))
                && (this.getJudgeConfig() == null ? other.getJudgeConfig() == null : this.getJudgeConfig().equals(other.getJudgeConfig()))
                && (this.getThumbNum() == null ? other.getThumbNum() == null : this.getThumbNum().equals(other.getThumbNum()))
                && (this.getFavourNum() == null ? other.getFavourNum() == null : this.getFavourNum().equals(other.getFavourNum()))
                && (this.getUserId() == null ? other.getUserId() == null : this.getUserId().equals(other.getUserId()))
                && (this.getCreateTime() == null ? other.getCreateTime() == null : this.getCreateTime().equals(other.getCreateTime()))
                && (this.getUpdateTime() == null ? other.getUpdateTime() == null : this.getUpdateTime().equals(other.getUpdateTime()))
                && (this.getIsDelete() == null ? other.getIsDelete() == null : this.getIsDelete().equals(other.getIsDelete()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getTitle() == null) ? 0 : getTitle().hashCode());
        result = prime * result + ((getContent() == null) ? 0 : getContent().hashCode());
        result = prime * result + ((getTags() == null) ? 0 : getTags().hashCode());
        result = prime * result + ((getAnswer() == null) ? 0 : getAnswer().hashCode());
        result = prime * result + ((getSubmitNum() == null) ? 0 : getSubmitNum().hashCode());
        result = prime * result + ((getAcceptedNum() == null) ? 0 : getAcceptedNum().hashCode());
        result = prime * result + ((getJudgeCase() == null) ? 0 : getJudgeCase().hashCode());
        result = prime * result + ((getJudgeConfig() == null) ? 0 : getJudgeConfig().hashCode());
        result = prime * result + ((getThumbNum() == null) ? 0 : getThumbNum().hashCode());
        result = prime * result + ((getFavourNum() == null) ? 0 : getFavourNum().hashCode());
        result = prime * result + ((getUserId() == null) ? 0 : getUserId().hashCode());
        result = prime * result + ((getCreateTime() == null) ? 0 : getCreateTime().hashCode());
        result = prime * result + ((getUpdateTime() == null) ? 0 : getUpdateTime().hashCode());
        result = prime * result + ((getIsDelete() == null) ? 0 : getIsDelete().hashCode());
        return result;
    }

    @Override
    public String toString() {
        String sb = getClass().getSimpleName() +
                " [" +
                "Hash = " + hashCode() +
                ", id=" + id +
                ", title=" + title +
                ", content=" + content +
                ", tags=" + tags +
                ", answer=" + answer +
                ", submitNum=" + submitNum +
                ", acceptedNum=" + acceptedNum +
                ", judgeCase=" + judgeCase +
                ", judgeConfig=" + judgeConfig +
                ", thumbNum=" + thumbNum +
                ", favourNum=" + favourNum +
                ", userId=" + userId +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", isDelete=" + isDelete +
                ", serialVersionUID=" + serialVersionUID +
                "]";
        return sb;
    }
}