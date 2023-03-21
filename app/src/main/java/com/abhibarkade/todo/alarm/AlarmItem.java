package com.abhibarkade.todo.alarm;

import java.time.LocalDateTime;

public class AlarmItem {
    LocalDateTime dateTime;
    String msg;

    public AlarmItem(LocalDateTime dateTime, String msg) {
        this.dateTime = dateTime;
        this.msg = msg;
    }
}

