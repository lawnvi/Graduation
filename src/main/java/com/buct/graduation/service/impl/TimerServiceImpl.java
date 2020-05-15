package com.buct.graduation.service.impl;

import com.buct.graduation.mapper.StationMapper;
import com.buct.graduation.service.TimerService;
import com.buct.graduation.util.GlobalName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
public class TimerServiceImpl implements TimerService {
    @Autowired
    private StationMapper stationMapper;


    @Override
    public HashMap<String, String> updateByDay() {
        stationMapper.updateOverTime(GlobalName.station_stop, "逾期");
        return null;
    }
}
