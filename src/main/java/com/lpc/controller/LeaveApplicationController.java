package com.lpc.controller;

import com.lpc.entity.pojo.LeaveApplication;
import com.lpc.service.LeaveApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import java.util.List;

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
     */
    @PostMapping("/leave_application")
    public void addLeaveApplication(@RequestBody LeaveApplication leaveApplication) {
        leaveApplicationService.addLeaveApplication(leaveApplication);
    }

    /**
     * 审核学生的请假申请
     */
    @PutMapping("/leave_application")
    public void checkLeaveApplication(@RequestBody LeaveApplication leaveApplication) {
        leaveApplicationService.checkLeaveApplication(leaveApplication);
    }

    /**
     * 获取当前学生的所有请假申请
     */
    @GetMapping("/leave_applications")
    public List<LeaveApplication> getAllLeaveApplications() {
        return leaveApplicationService.getAllLeaveApplications();
    }

    /**
     * 获取所有符合状态的请假申请
     */
    @GetMapping("/leave_applications/{state}")
    public List<LeaveApplication> getAllLeaveApplicationsByState(
            @PathVariable("state") String state) {
        return leaveApplicationService.getAllLeaveApplicationsByState(state);
    }

}
