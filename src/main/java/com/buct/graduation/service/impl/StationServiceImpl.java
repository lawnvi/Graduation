package com.buct.graduation.service.impl;

import com.buct.graduation.mapper.StationMapper;
import com.buct.graduation.model.pojo.recruit.Station;
import com.buct.graduation.service.StationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StationServiceImpl implements StationService {
    @Autowired
    private StationMapper stationMapper;

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
        return stationMapper.findAll();
    }
}
