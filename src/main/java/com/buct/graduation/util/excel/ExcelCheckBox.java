package com.buct.graduation.util.excel;

import com.buct.graduation.model.pojo.Reporter;

/**
 * 评价报告定制类
 */
public class ExcelCheckBox {
    public String professor = "";//教授
    public String pProfessor = "";//见习教授
    public String aProfessor = "";//副教授
    public String apProfessor = "";//见习副教授
    public String teacher = "";//讲师
    private final static String checkMark = "√";

    public ExcelCheckBox(Reporter reporter){
        switch (reporter.getPost()){
            case "教授": {
                professor = checkMark;
                if(reporter.getTitle().contains("院士") || reporter.getTitle().contains("千人")|| reporter.getTitle().contains("杰青") ||reporter.getTitle().contains("优青"))
                    c_6_1 = checkMark;
                if(reporter.getEsi() > 0)
                    c_6_2 = checkMark;
                if(reporter.getFunds() > 300)
                    c_6_3 = checkMark;
                if(reporter.getJcr() >= 5)
                    c_5_1 = checkMark;
                if(reporter.getScore() >= 50)
                    c_5_2 = checkMark;
                if (reporter.getSciCitation() > 50)
                    c_5_3 = checkMark;
                if(reporter.getFund().contains("NSFC重点基金"))
                    c_5_4 = checkMark;
                break;
            }
            case "见习教授":{
                pProfessor = checkMark;
                if(reporter.getJcr() >= 4)
                    c_4_1 = checkMark;
                if(reporter.getScore() >= 40)
                    c_4_2 = checkMark;
                if (reporter.getSciCitation() > 40)
                    c_4_3 = checkMark;
                if(reporter.getFund().contains("NSFC重点基金"))
                    c_4_4 = checkMark;
                break;
            }
            case "副教授":{
                aProfessor = checkMark;
                if(reporter.getJcr() >= 3)
                    c_3_1 = checkMark;
                if(reporter.getScore() >= 30)
                    c_3_2 = checkMark;
                if (reporter.getSciCitation() > 30)
                    c_3_3 = checkMark;
                if(reporter.getFund().contains("NSFC面上基金") || reporter.getFund().contains("NSFC重点基金"))
                    c_3_4 = checkMark;
                break;
            }
            case "见习副教授":{
                apProfessor = checkMark;
                if(reporter.getJcr() >= 2)
                    c_2_1 = checkMark;
                if(reporter.getScore() >= 20)
                    c_2_2 = checkMark;
                if (reporter.getSciCitation() > 20)
                    c_2_3 = checkMark;
                if(reporter.getFund().contains("NSFC青年基金") ||reporter.getFund().contains("NSFC面上基金") || reporter.getFund().contains("NSFC重点基金"))
                    c_2_4 = checkMark;
                break;
            }
            case "讲师":{
                teacher = checkMark;
                if(reporter.getJcr() >= 1)
                    c_1_1 = checkMark;
                if(reporter.getScore() >= 5)
                    c_1_2 = checkMark;
                if (reporter.getSciCitation() > 7)
                    c_1_3 = checkMark;
//                if(reporter.getFund().contains("NSFC青年基金") ||reporter.getFund().contains("NSFC面上基金") || reporter.getFund().contains("NSFC重点基金"))
//                    c_4_4 = checkMark;
                break;
            }
            default:break;
        }
    }

    public String c_1_1 = "";
    public String c_1_2 = "";
    public String c_1_3 = "";

    public String c_2_1 = "";
    public String c_2_2 = "";
    public String c_2_3 = "";
    public String c_2_4 = "";

    public String c_3_1 = "";
    public String c_3_2 = "";
    public String c_3_3 = "";
    public String c_3_4 = "";

    public String c_4_1 = "";
    public String c_4_2 = "";
    public String c_4_3 = "";
    public String c_4_4 = "";

    public String c_5_1 = "";
    public String c_5_2 = "";
    public String c_5_3 = "";
    public String c_5_4 = "";

    public String c_6_1 = "";
    public String c_6_2 = "";
    public String c_6_3 = "";
}
