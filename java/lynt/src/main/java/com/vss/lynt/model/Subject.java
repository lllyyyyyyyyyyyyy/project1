package com.vss.lynt.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Time;

@Entity
@Table(name = "subject")
@NoArgsConstructor
@Data
public class Subject {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "subject_id")
    private String subjectId;

    @Column(name = "name")
    private String name;

    @Column(name = "start_time")
    private Time startTime;

    @Column(name = "end_time")
    private Time endTime;

    @Column(name = "day_of_week")
    private Long dayOfWeek;

    public Subject(Integer id, String subjectId, String name, Time startTime, Time endTime, Long dayOfWeek) {
        this.id = id;
        this.subjectId = subjectId;
        this.name = name;
        this.startTime = startTime;
        this.endTime = endTime;
        this.dayOfWeek = dayOfWeek;
    }

    @Column(name = "document")
    private String document;

}
