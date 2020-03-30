package com.buct.graduation.model.vo;

import com.buct.graduation.model.pojo.Reporter;
import com.buct.graduation.model.pojo.recruit.Interview;
import com.buct.graduation.model.pojo.recruit.Resume;
import com.buct.graduation.model.pojo.recruit.Station;

import java.util.List;

public class Apply {
    private Station station;
    private Resume resume;
    private Reporter reporter;
    private List<Interview> interviews;

    public Station getStation() {
        return station;
    }

    public void setStation(Station station) {
        this.station = station;
    }

    public Resume getResume() {
        return resume;
    }

    public void setResume(Resume resume) {
        this.resume = resume;
    }

    public Reporter getReporter() {
        return reporter;
    }

    public void setReporter(Reporter reporter) {
        this.reporter = reporter;
    }

    public List<Interview> getInterviews() {
        return interviews;
    }

    public void setInterviews(List<Interview> interviews) {
        this.interviews = interviews;
    }
}
