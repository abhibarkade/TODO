package com.abhibarkade.todo.pojo;

public class CustomDays {
    String weekDay;
    int monthDay;

    public CustomDays() {
    }

    public CustomDays(String weekDay, int monthDay) {
        this.weekDay = weekDay;
        this.monthDay = monthDay;
    }

    public String getWeekDay() {
        return weekDay;
    }

    public void setWeekDay(String weekDay) {
        this.weekDay = weekDay;
    }

    public int getMonthDay() {
        return monthDay;
    }

    public void setMonthDay(int monthDay) {
        this.monthDay = monthDay;
    }
}
