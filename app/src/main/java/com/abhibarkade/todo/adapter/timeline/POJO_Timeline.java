package com.abhibarkade.todo.adapter.timeline;

import java.util.List;

public class POJO_Timeline {
    String time;
    List<Integer> list;

    public POJO_Timeline(String time, List<Integer> list) {
        this.time = time;
        this.list = list;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public List<Integer> getList() {
        return list;
    }

    public void setList(List<Integer> list) {
        this.list = list;
    }
}
