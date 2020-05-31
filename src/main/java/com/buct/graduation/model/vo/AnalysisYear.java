package com.buct.graduation.model.vo;

public class AnalysisYear {
    private int number = 0;
    private int citation = 0;
    private int top = 0;
    private int esi = 0;
    private int jcr_12 = 0;

    private int year = 1970;

    public int getJcr_12() {
        return jcr_12;
    }

    public void setJcr_12(int jcr_12) {
        this.jcr_12 += jcr_12;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number += number;
    }

    public int getCitation() {
        return citation;
    }

    public void setCitation(int citation) {
        this.citation += citation;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getTop() {
        return top;
    }

    public void setTop(int top) {
        this.top += top;
    }

    public int getEsi() {
        return esi;
    }

    public void setEsi(int esi) {
        this.esi += esi;
    }
}
