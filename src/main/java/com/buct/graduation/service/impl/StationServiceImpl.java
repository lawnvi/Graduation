package com.buct.graduation.service.impl;

import com.buct.graduation.mapper.ResumeMapper;
import com.buct.graduation.mapper.StationMapper;
import com.buct.graduation.model.pojo.recruit.Station;
import com.buct.graduation.model.vo.StationData;
import com.buct.graduation.service.StationService;
import com.buct.graduation.util.GlobalName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StationServiceImpl implements StationService {
    @Autowired
    private StationMapper stationMapper;
    @Autowired
    private ResumeMapper resumeMapper;

    @Override
    public int postStation(Station station) {
        return stationMapper.add(station);
    }

    @Override
    public int updateStation(Station station) {
        return stationMapper.update(station);
    }

    @Override
    public Station findStationById(int id) {
        return stationMapper.findById(id);
    }

    @Override
    public List<Station> findAllStations() {
        List<Station> stations = stationMapper.findAll();
        countResume(stations);
        return stations;
    }

    @Override
    public List<Station> findStationsByStatus(String status) {
        List<Station> stations = stationMapper.findByStatus(status);
        countResume(stations);
        return stations;
    }

    @Override
    public List<Station> findJobsWithPage(int page, int number, String keyword) {
        if(keyword == null || "".equals(keyword))
            return stationMapper.findJobsWithPage((page-1)*number, number);
        keyword = "%"+keyword+"%";
        return stationMapper.findJobsByKeywordWithPage((page-1)*number, number, keyword);
    }

    @Override
    public StationData findStationData() {
        StationData data = new StationData();
        data.setNumber(stationMapper.countAll());
        data.setOn(stationMapper.countByStatus(GlobalName.station_on));
        data.setPause(stationMapper.countByStatus(GlobalName.station_pause));
        data.setStop(stationMapper.countByStatus(GlobalName.station_stop));
        return data;
    }

    private void countResume(List<Station> stations){
        for(Station station:stations){
            station.setResumeNumber(resumeMapper.countBySid(station.getId()));
            station.setInterviewing(resumeMapper.countBySid_Status(station.getId(), GlobalName.resume_processing));
            station.setNewResume(resumeMapper.countBySid_Status(station.getId(), GlobalName.resume_wait));
            station.setPassed(resumeMapper.countBySid_Status(station.getId(), GlobalName.resume_pass));
        }
    }
}
