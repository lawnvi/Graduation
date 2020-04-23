package com.buct.graduation.model.vo;

/**
 * 岗位总体描述
 */
public class StationData {
    private Integer number = 0;//总共
    private Integer on = 0;//在招
    private Integer stop = 0;//招聘结束
    private Integer pause = 0;//暂停岗位数

    //config
    //单人最大投递岗位数
    private Integer maxApplyTimes = 1;


    public Integer getMaxApplyTimes() {
        return maxApplyTimes;
    }

    public void setMaxApplyTimes(Integer maxApplyTimes) {
        this.maxApplyTimes = maxApplyTimes;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public Integer getOn() {
        return on;
    }

    public void setOn(Integer on) {
        this.on = on;
    }

    public Integer getStop() {
        return stop;
    }

    public void setStop(Integer stop) {
        this.stop = stop;
    }

    public Integer getPause() {
        return pause;
    }

    public void setPause(Integer pause) {
        this.pause = pause;
    }
}
