package com.buct.graduation.service;

import com.buct.graduation.model.pojo.recruit.Station;

import java.util.List;

public interface StationService {
    //发布新岗位
    int postStation(Station station);

    //更新岗位信息
    int updateStation(Station station);

    Station findStationById(int id);

    List<Station> findAllStations();
}
