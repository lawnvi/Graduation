package com.buct.graduation.util;

import com.buct.graduation.model.pojo.*;
import com.buct.graduation.model.util.NowDate;
import com.buct.graduation.util.spider.IpPoolUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.lang.reflect.Array;
import java.util.*;

public class Utils {

    //ip池
    public static IpPoolUtil ipPool = new IpPoolUtil();

    /**
     * 当前年份
     * @return
     */
    public static NowDate getDate(){
        return new NowDate();
    }

    /**
     * wos作者格式化
     * @param str
     * @return
     */
    public static String getNames(String str){
        if(str.equals(""))
            return str;
        StringBuffer s = new StringBuffer();
        boolean temp = false;
        for(int i = 0; i < str.length(); i++){
            if(str.charAt(i) == '('){
                temp = true;
                continue;
            }else if(str.charAt(i) == ')'){
                s.append(", ");
                temp = false;
            }
            if(temp){
                if(str.charAt(i) != ',')
                    s.append(str.charAt(i));
            }
        }
        String name = s.toString();
        if(name.endsWith(", ")) {
            name = name.substring(0, s.length() - 2);
        }
        return name;
    }

    /**
     * wos发表时间格式化
     * @param s
     * @return
     */
    public static String formatTime(String s){
        String year = "";
        String month = "";
        String day = "";
        Map<String, String> map = new HashMap<>();
        map.put("JAN", "01");
        map.put("FEB", "02");
        map.put("MAR", "03");
        map.put("APR", "04");
        map.put("MAY", "05");
        map.put("JUN", "06");
        map.put("JUL", "07");
        map.put("AUG", "08");
        map.put("SEP", "09");
        map.put("OCT", "10");
        map.put("NOV", "11");
        map.put("DEC", "12");
        List<String> list = new ArrayList<>();
        int temp = 0;
        for(int i = 0; i < s.length(); i++){
            if(s.charAt(i) == ' '){
                list.add(s.substring(temp, i));
                temp = i + 1;
            }else if(i == s.length() - 1){
                list.add(s.substring(temp, i + 1));
                temp = i + 1;
            }
        }
        for (String str: list){
            System.out.println(str);
            switch (str.length()){
                case 4: year = str; break;
                case 3: month = map.get(str); break;
                case 2:
                case 1:
                    day = str; break;
                default:break;
            }
        }
        if(month.equals(""))
            return year;
        if(day.equals(""))
            return year+"."+month;
        return year+"."+month+"."+day;
    }

    public static String getCode() {
        int n = 6;
        String string = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";//保存数字0-9 和 大小写字母
        StringBuffer sb = new StringBuffer(); //声明一个StringBuffer对象sb 保存 验证码
        for (int i = 0; i < n; i++) {
            Random random = new Random();//创建一个新的随机数生成器
            int index = random.nextInt(string.length());//返回[0,string.length)范围的int值    作用：保存下标
            char ch = string.charAt(index);//charAt() : 返回指定索引处的 char 值   ==》赋值给char字符对象ch
            sb.append(ch);// append(char c) :将 char 参数的字符串表示形式追加到此序列  ==》即将每次获取的ch值作拼接
        }
        return sb.toString();//toString() : 返回此序列中数据的字符串表示形式   ==》即返回一个String类型的数据
    }

    /*
    public static String saveFile(MultipartFile file, String path){
        if(!file.isEmpty()) {
            String fileName = file.getOriginalFilename();  // 文件名
            //fileName = UUID.randomUUID() + suffixName; // 新文件名
            String newName= Utils.getCode()+"-" +Utils.getCode() + fileName.substring(fileName.lastIndexOf(".")); // 新文件名

            File file2 = new File(Global.ABSOLUTEPATH+path);
            if(!file2.isDirectory()) {
                //递归生成文件夹
                file2.mkdirs();
            }
            try {
                //构建真实的文件路径
                File newFile = new File(file2.getAbsolutePath() + File.separator + newName);
                //转存文件到指定路径，如果文件名重复的话，将会覆盖掉之前的文件,这里是把文件上传到 “绝对路径”
                file.transferTo(newFile);
                return "/"+Global.PACKAGE_NAME+path+File.separator+newName;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return "";
    }

     */

    /**
     * session
     */
    public static void saveSession(String key, String value, HttpServletRequest request){
        HttpSession session = request.getSession();
        session.setAttribute(key, value);
        //时限600s
        session.setMaxInactiveInterval(600);
    }

    public static String getSession(HttpServletRequest request, String name){
        HttpSession session = request.getSession();
        return (String)session.getAttribute(name);
    }

    public static String removeSession(HttpServletRequest request, String name){
        HttpSession session = request.getSession();
        String temp = (String)session.getAttribute(name);
        session.removeAttribute(name);
        return temp;
    }

    /**
     * String[] -> String/String/...
     * @param s
     * @return
     */
    public static String checkboxToString(String[] s){
        if(s.length == 0)
            return "无";
        StringBuffer temp = new StringBuffer();
        for(String str: s){
            temp.append(str+"/");
        }
        return temp.substring(0, temp.length()-1);
    }

    public static List<String> toCheckbox(String str){
        if(str == null || str.equals(""))
            return null;
        return Arrays.asList(str.split("[/]"));
    }


    /**
     * 计算评价分数
//     * @param reporter
     * @param projects
     * @param patents
     * @param articles
     * @param papers
     * @return
     */
    public static Reporter getScore(List<Project> projects, List<Patent> patents, List<Article> articles, List<ConferencePaper> papers){
        Reporter reporter = new Reporter();
        //评价指标 resume
        //期刊论文相关指标
        int jcr = 0;//jcr分区1/2
        int sciCitation = 0;//sci被引次数
        int citation = 0;
        int esi = 0;//esi论文数量
        double IF = 0;//影响因子
        for(Article article: articles){
            if(article.getJournal().getSection() == 1 || article.getJournal().getSection() == 2){
                jcr++;
            }
            sciCitation += article.getCitation();
            IF += article.getJournal().getIF();
            if(article.getESI()){
                esi++;
            }
        }
        citation = sciCitation;
        //会议论文
        for(ConferencePaper paper: papers){
            if(paper.getSection().toUpperCase().contains("CCF-")){
                sciCitation += paper.getCitation();
            }
            if(paper.isEsi()){
                esi++;
            }
            citation += paper.getCitation();
        }


        //project指标
        double money = 0;
        for(Project project : projects){
            money += project.getFunds();
        }
        int jcrScore = getJCRScore(articles, patents, papers);
        double score = jcrScore + Math.log(IF+1) + Math.log(citation+1);
        reporter.setCitation(citation);
        reporter.setJcr(jcr);
        reporter.setSciCitation(sciCitation);
        reporter.setEsi(esi);
        reporter.setIF(IF);
        reporter.setFunds(money);
        reporter.setJcrScore(jcrScore);
        reporter.setScore(score);

        String post = getPost(reporter);
        reporter.setPost(post);

        return reporter;
    }

    /**
     * 计算jcr分数
     * @param articles
     * @return
     */
    private static int getJCRScore(List<Article> articles, List<Patent> patents, List<ConferencePaper> papers) {
        int global = 0;
        int country = 0;
        for(Patent patent: patents){
            if(patent.getCategory().equals("国际")){
                global++;
            }else {
                country++;
            }
        }

        int esi_1 = 0;
        int esi_2 = 0;
        int esi_3 = 0;

        int top_1 = 0;
        int noTop_1 = 0;
        int top_2 = 0;
        int noTop_2 = 0;
        int section_3 = 0;
        int section_4 = 0;

        int section_a = 0;
        int section_b = 0;
        int section_c = 0;

        for (Article article: articles){
            switch (article.getJournal().getSection()){
                case 1:{
                    if(article.getESI()){
                        esi_1++;
                    }
                    if(article.getJournal().getTop()){
                        top_1++;
                    }else {
                        noTop_1++;
                    }
                    break;
                }
                case 2:{
                    if(article.getESI()){
                        esi_2++;
                    }
                    if(article.getJournal().getTop()){
                        top_2++;
                    }else {
                        noTop_2++;
                    }
                    break;
                }
                case 3:{
                    if(article.getESI()){
                        esi_3++;
                    }
                    section_3++;
                    break;
                }
                case 4:{
                    section_4++;
                    break;
                }
            }
        }

        for(ConferencePaper paper: papers){
            switch (paper.getSection().toUpperCase()){
                case "CCF-A": section_a++;break;
                case "CCF-B": section_b++;break;
                case "CCF-C": section_c++;break;
                default:break;
            }
        }

        return 5*global + country
                + 10*esi_1 + 7*esi_2 + esi_3
                + 6*section_a + 4*section_b + 2*section_c
                + 7*top_1 + 5*noTop_1 + 4*top_2 + 3*noTop_2 + 2*section_3 + section_4;
    }

    public static String getPost(Reporter reporter){
        //教授
        if(!reporter.getTitle().equals("") || reporter.getEsi() > 0 || reporter.getFunds() >= 3000000){
            return "教授";
        }
        int professor = 0;//教授
        int pProfessor = 0;//见习教授
        int aProfessor = 0;//副教授
        int apProfessor = 0;//见习副教授
        int teacher = 0;//讲师

        int jcr = reporter.getJcr();
        double score = reporter.getScore();
        int citation = reporter.getSciCitation();
        String fund = reporter.getFund();
        if(jcr >= 1 || score >= 5 || citation >= 7){
            teacher++;
        }
        if(jcr >= 2 || score >= 20 || citation >= 20 || fund.contains("NSFC青年基金")){
            apProfessor++;
        }
        if(jcr >= 3 || score >= 30 || citation >= 30 || fund.contains("NSFC面上基金")){
            aProfessor++;
        }
        if(jcr >= 4 || score >= 40 || citation >= 40 || fund.contains("NSFC重点基金")){
            pProfessor++;
        }
        if(jcr >= 5 || score >= 50 || citation >= 50 || fund.contains("NSFC重点基金")){
            professor++;
        }

        if(professor >= 2)
            return "教授";
        if(pProfessor >= 2)
            return "见习教授";
        if(aProfessor >= 2)
            return "副教授";
        if(apProfessor >= 2)
            return "见习副教授";
        if(teacher >= 2)
            return "讲师";
        return "其他";
    }


}
