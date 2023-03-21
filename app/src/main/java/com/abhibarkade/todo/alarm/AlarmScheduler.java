package com.abhibarkade.todo.alarm;

public interface AlarmScheduler {
    void schedule(AlarmItem item);

    void cancel(AlarmItem item);
}