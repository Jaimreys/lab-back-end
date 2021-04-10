package com.lpc.controller;

import com.lpc.entity.pojo.LeaveApplication;
import com.lpc.service.LeaveApplicationService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import javax.validation.groups.Default;
import java.util.List;

@Validated
@RestController
public class LeaveApplicationController {
    private LeaveApplicationService leaveApplicationService;

    @Autowired
    public LeaveApplicationController(LeaveApplicationService leaveApplicationService) {
        this.leaveApplicationService = leaveApplicationService;
    }

    /**
     * 学生发起请假申请
     * 申请的时候只是向请假申请表里插入一条数据，只有在同意的时候，才会形成job和trigger
     *
     * @param leaveApplication 请假申请
     *                         proposerUsername、startTime、endTime不能为null
     */
    @ApiOperation(value = "发起请假申请")
    @Secured("student")
    @PostMapping("/leave_application")
    public void addLeaveApplication(
            @NotNull(message = "请假申请对象不能为空")
            @Validated({LeaveApplication.insert.class, Default.class})
            @RequestBody LeaveApplication leaveApplication) {
        leaveApplicationService.addLeaveApplication(leaveApplication);
    }

    /**
     * 批准或是驳回学生的请假申请
     *
     * @param leaveApplication 请假申请
     *                         id、startTime、endTime、disapprovedReason、checkerUsername不能为null
     */
    @ApiOperation(value = "批准或驳回学生的请假申请")
    @Secured({"admin", "teacher"})
    @PutMapping("/leave_application")
    public void checkLeaveApplication(
            @NotNull(message = "请假申请对象不能为空")
            @Validated({LeaveApplication.update.class, Default.class})
            @RequestBody LeaveApplication leaveApplication) {
        leaveApplicationService.checkLeaveApplication(leaveApplication);
    }

    /**
     * 获取当前学生的所有请假申请
     *
     * @return 发起该请求的学生的所有请假申请
     */
    @ApiOperation(value = "获取发起请求的学生的所有请假申请")
    @Secured("student")
    @GetMapping("/leave_applications")
    public List<LeaveApplication> getAllLeaveApplications() {
        return leaveApplicationService.getAllLeaveApplications();
    }

    /**
     * 获取所有符合状态的请假申请
     *
     * @param state 要获取的请假申请的状态
     * @return 符合条件的请假申请List
     */
    @ApiOperation(value = "获取所有符合状态的请假申请")
    @ApiImplicitParams(
            @ApiImplicitParam(name = "state",
                    value = "请假申请的状态",
                    required = true,
                    paramType = "path")
    )
    @Secured({"admin", "teach"})
    @GetMapping("/leave_applications/{state}")
    public List<LeaveApplication> getAllLeaveApplicationsByState(
            @NotNull(message = "请假申请状态不能为空") @PathVariable("state") String state) {
        return leaveApplicationService.getAllLeaveApplicationsByState(state);
    }

}
