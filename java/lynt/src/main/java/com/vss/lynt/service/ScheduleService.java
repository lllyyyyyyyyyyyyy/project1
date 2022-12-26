package com.vss.lynt.service;

import com.vss.lynt.model.Schedule;

import java.sql.Time;
import java.util.List;
import java.util.Optional;

public interface ScheduleService {
    Optional<Schedule> findByTime(Time time);

    Optional<Schedule> findById(Integer id);

    Schedule insertSchedule(Schedule schedule);

    void deleteSchdule();

    List<Schedule> findAll();
}
