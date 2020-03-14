package com.buct.graduation.util.spider;

public class SpiderConfig {
    private static String SID = "";
    private static int SIDTimes = 0;
    private static int maxTimes = 32;

    public static String getSID() {
        visitSIDTimes();
        return SID;
    }

    public static synchronized void visitSIDTimes(){
        System.out.println("sidUseTimes:"+SIDTimes);
        if(SIDTimes > maxTimes || SID.equals(""))
            getNewSID();
        SIDTimes++;
    }

    public static synchronized String visitSIDTimes(int n){
        System.out.println("sidUseTimes:"+SIDTimes+"  qid:"+n);
//        if(n > maxTimes)
//            getNewSID();
        return SID;
    }

    public static void getNewSID(){
        SIDTimes = 0;
        SID = SpiderWOS.getSID();
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
