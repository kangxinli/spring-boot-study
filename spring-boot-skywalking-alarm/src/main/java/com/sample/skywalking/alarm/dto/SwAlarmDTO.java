package com.sample.skywalking.alarm.dto;

import lombok.Data;

@Data
public class SwAlarmDTO {

    private Integer scopeId;
    private String scope;
    private String name;
    private Integer id0;
    private Integer id1;
    private String ruleName;
    private String alarmMessage;
    private Long startTime;
}
