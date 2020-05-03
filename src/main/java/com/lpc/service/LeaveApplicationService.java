package com.lpc.service;

import com.lpc.entity.pojo.LeaveApplication;

import java.util.List;

public interface LeaveApplicationService {
    void addLeaveApplication(LeaveApplication leaveApplication);

    void checkLeaveApplication(LeaveApplication leaveApplication);

    List<LeaveApplication> getAllLeaveApplications();

    List<LeaveApplication> getAllLeaveApplicationsByState(String state);
}
