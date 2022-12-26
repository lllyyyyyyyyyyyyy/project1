package com.vss.lynt.service.impliment;

import com.vss.lynt.model.Schedule;
import com.vss.lynt.repository.ScheduleRepository;
import com.vss.lynt.service.ScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Time;
import java.util.List;
import java.util.Optional;

@Service
public class ScheduleServiceImpl implements ScheduleService {

    @Autowired
    ScheduleRepository scheduleRepository;

    @Override
    public Optional<Schedule> findByTime(Time time) {
        return scheduleRepository.findByTime(time);
    }

    @Override
    public Optional<Schedule> findById(Integer id) {
        return scheduleRepository.findById(id);
    }

    @Override
    public Schedule insertSchedule(Schedule schedule) {
        return scheduleRepository.save(schedule);
    }

    @Override
    public void deleteSchdule() {
        scheduleRepository.deleteAll();
    }

    @Override
    public List<Schedule> findAll() {
        return scheduleRepository.findAll();
    }

}
