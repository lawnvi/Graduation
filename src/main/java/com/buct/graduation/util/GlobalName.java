package com.buct.graduation.util;

public class GlobalName {
    //简历进度
    public static final String resume_init = "new";
    public static final String resume_notApply = "未投递";
    public static final String resume_wait = "等待处理";
    public static final String resume_processing = "处理中";
    public static final String resume_fail = "未通过";
    public static final String resume_pass = "通过";

    //面试记录状态
    public static final String interview_pass = "通过";
    public static final String interview_wait = "待评判";
    public static final String interview_fail = "未通过";

    //岗位状态
    public static final String station_on = "在招";
    public static final String station_stop = "停止";
    public static final String station_pause = "暂停";

    //成功/失败
    public static final String success = "ok";
    public static final String fail = "error";

    //session name
    public static final String session_userId = "session_user_id";
    public static final String session_user = "session_user";
    public static final String session_admin = "session_admin";
    public static final String session_teacher = "session_teacher";

    //level
    public static final String teacher = "讲师";
    public static final String newGay = "应聘者";
    public static final String professor = "教授";
    public static final String pProfessor = "见习教授";
    public static final String professor_min = "副教授";
    public static final String pProfessor_min = "见习副教授";

    //期刊论文添加方式
    public static final String addWay_candidate = "应聘者";
    public static final String addWay_admin = "管理员";
    public static final String addWay_System = "自动";
    public static final String addWay_missing = "待完善";
    public static final String addWay_missing_c = "应聘者待完善";
    public static final String addWay_missing_a = "管理员待完善";

    /**
     * 网站路径配置数据
     */
    //todo change path & db_psw
    //文件存储路径(ABSOLUTEPATH)
    public static final String ABSOLUTE_PATH = "D:\\schoolHelper\\mybuffer\\import\\upload\\";
//    public static final String ABSOLUTE_PATH = "/usr/local/programs/myBuffer/import/upload/";
    public static final String RELATIVE_PATH = "/import/";
    public static final String ROOT_PATH = "import";
    public static final String PIC_PATH = "/images/avatar-1.jpg";
    public static final String EXCEL_PATH = "excel/";
    public static final String EXCEL_MODEL = "综合评价数据模板.xlsx";
    public static final String EXCEL_MODEL_REPORTER = "综合评价结果模板.xlsx";
    public static final String EXCEL_BUFFER = "buffer/";

    /**
     * ip 状态
     */
    public static final String IP_FREE = "free";
    public static final String IP_BUSY = "busy";
    public static final String IP_OFFLINE = "offline";

    /**
     * 用户类型
     */
    public static final String type_admin = "admin-";
    public static final String type_user = "user-";
    public static final String type_teacher = "teacher-";

    /**
     * 期刊是否学院
     */
    public static final String belongSchool = "学院";
    public static final String belongVisitor = "访客";
    public static final String belongApply = "应聘";

    /**
     * 学院教师期刊论文状态
     */
    public static final String teacher_flag_normal = "正常";
    public static final String teacher_flag_apply = "申请";
    public static final String teacher_flag_claim = "待认领";
    public static final String teacher_flag_other = "其他";

    /**
     * 用户类型
     */
    public static final String user_type_user = "应聘者";
    public static final String user_type_teacher = "教师";

}
