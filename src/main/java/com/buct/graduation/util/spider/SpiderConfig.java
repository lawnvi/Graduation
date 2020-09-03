package com.buct.graduation.util.spider;

import com.buct.graduation.util.Utils;

public class SpiderConfig {
    private static String SID = "";
    private static String sessionSID = "";
    private static int SIDTimes = 0;
    private static int maxTimes = 32;

    public static String getSID() {
        visitSIDTimes();
        return SID;
    }

    public static synchronized void visitSIDTimes(){
        System.out.println("sidUseTimes:"+SIDTimes);
        if(SIDTimes > maxTimes || SID.equals("")) {
            getNewSID();
            if("".equals(SID)){
                return;
            }
        }
        SIDTimes++;
    }

    public static synchronized String visitSIDTimes(int n){
        System.out.println("sidUseTimes:"+SIDTimes+"  qid:"+n);
//        if(n > maxTimes)
//            getNewSID();
        return SID;
    }

    public static int getSIDTimes(){
        return SIDTimes;
    }

    public static void getNewSID(){
        if("".equals(getSessionSID())){
            SIDTimes = 0;
            SID = SpiderWOS.getSID();
        }
    }

    private static String getSessionSID(){
        SID = sessionSID;
        return SID;
    }

    public static void setSessionSID(String sessionSID) {
        SpiderConfig.sessionSID = sessionSID;
    }

    public static String initSID(){
        return SpiderWOS.getSID();
    }

    public static int getMaxTimes() {
        return maxTimes;
    }

    public static void setMaxTimes(int maxTimes) {
        SpiderConfig.maxTimes = maxTimes;
    }
}
