package com.lpc.entity.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

// @ApiModelProperty怎么没有效果
@ApiModel(value = "请假申请对象")
public class LeaveApplication {
    /**
     * 参数校验分组
     */
    public interface insert {
    }

    /**
     * 参数校验分组
     */
    public interface update {
    }

    @TableId(type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "请假申请发起人的用户名")
    @NotNull(message = "请假申请发起人的用户名不能为空", groups = insert.class)
    private Long proposerUsername;

    @ApiModelProperty(value = "假期开始时间")
    @NotNull(message = "假期开始时间不能为空", groups = {insert.class, update.class})
    private LocalDateTime startTime;

    @ApiModelProperty(value = "假期结束时间")
    @NotNull(groups = {insert.class, update.class})
    private LocalDateTime endTime;

    @ApiModelProperty(value = "请假原因")
    private String reason;

    @ApiModelProperty(value = "请假申请的状态")
    private String state;

    @ApiModelProperty(value = "驳回请假申请的理由")
    @NotNull(groups = update.class)
    @Length(message = "原因的字数在1到140之间", min = 1, max = 140, groups = {update.class})
    private String disapprovedReason;

    @ApiModelProperty(value = "请假申请审核人的用户名")
    private Long checkerUsername;

    @ApiModelProperty(value = "请假申请审核时间")
    private LocalDateTime checkTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Long getProposerUsername() {
        return proposerUsername;
    }

    public void setProposerUsername(Long proposerUsername) {
        this.proposerUsername = proposerUsername;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Long getCheckerUsername() {
        return checkerUsername;
    }

    public void setCheckerUsername(Long checkerUsername) {
        this.checkerUsername = checkerUsername;
    }

    public LocalDateTime getCheckTime() {
        return checkTime;
    }

    public void setCheckTime(LocalDateTime checkTime) {
        this.checkTime = checkTime;
    }

    public String getDisapprovedReason() {
        return disapprovedReason;
    }

    public void setDisapprovedReason(String disapprovedReason) {
        this.disapprovedReason = disapprovedReason;
    }
}
