package com.vss.lynt.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Time;

@Entity
@Table(name = "schedule")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Schedule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "Time")
    private Time time;

    @Column(name = "MON")
    private String mon;

    @Column(name = "TUE")
    private String tue;

    @Column(name = "WED")
    private String wed;

    @Column(name = "THU")
    private String thu;

    @Column(name = "FRI")
    private String fri;

    @Column(name = "SAT")
    private String sat;

}
