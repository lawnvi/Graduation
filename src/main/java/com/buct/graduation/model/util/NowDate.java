package com.buct.graduation.model.util;

import java.util.Calendar;

/**
 * 当前日期，时间
 */
public class NowDate {
    private int year;
    private int month;
    private int day;
    private int hour;//24
    private int minute;
    private int second;

    public NowDate(){
        Calendar cal = Calendar.getInstance();
        this.day = cal.get(Calendar.DATE);
        this.month = cal.get(Calendar.MONTH) + 1;
        this.year = cal.get(Calendar.YEAR);
        this.hour = cal.get(Calendar.HOUR_OF_DAY);
        this.minute = cal.get(Calendar.MINUTE);
        this.second = cal.get(Calendar.SECOND);
    }

    @Override
    public String toString(){
        return year+"年"+month+"月"+day+"日 "+hour+":"+minute+":"+second;
    }

    public String toDate(){
        return year+"年"+month+"月"+day+"日 "+hour+":"+minute+":"+second;
    }

    public int getYear() {
        return year;
    }

    public int getMonth() {
        return month;
    }

    public int getDay() {
        return day;
    }

    public int getHour() {
        return hour;
    }

    public int getMinute() {
        return minute;
    }

    public int getSecond() {
        return second;
    }
}
