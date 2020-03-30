package com.buct.graduation.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class LogUtil {
    private static List<String> logs = new ArrayList<>();

    private static LogUtil logUtil;

    private LogUtil(){}

    public static LogUtil getInstance(){
        if(logUtil == null) {
            logUtil = new LogUtil();
            logs = Collections.synchronizedList(logs);
        }
        return logUtil;
    }

    public void addLog(String log){
        logs.add(Utils.getDate().toString()+" "+log);
    }

    public List<String> getLogs() {
        return logs;
    }
}
