package com.example.schedule_upgrade.schedule.service;

import com.example.schedule_upgrade.global.exception.ErrorCode;
import com.example.schedule_upgrade.global.exception.ServiceException;
import com.example.schedule_upgrade.schedule.entity.Schedule;
import com.example.schedule_upgrade.schedule.repository.ScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ScheduleReadService {
    private final ScheduleRepository scheduleRepository;

    public Schedule getScheduleById(Long scheduleId){
        return scheduleRepository.findById(scheduleId).orElseThrow(
                ()->new ServiceException(ErrorCode.SCHEDULE_NOT_FOUND)
        );
    }

    public boolean chkExistScheduleById(Long scheduleId){
        return scheduleRepository.existsById(scheduleId);
    }

}
