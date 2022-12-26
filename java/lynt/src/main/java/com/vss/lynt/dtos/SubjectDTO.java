package com.vss.lynt.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class SubjectDTO {
    private Integer id;
    private  String subjectId;
    private String name;
    private String startTime;
    private String endTime;
    private Long dayOfWeek;
}
