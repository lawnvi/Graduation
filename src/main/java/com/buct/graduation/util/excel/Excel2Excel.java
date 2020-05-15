package com.buct.graduation.util.excel;

import com.buct.graduation.model.pojo.*;
import com.buct.graduation.util.LogUtil;
import com.buct.graduation.util.ThreadPoolUtil;
import com.buct.graduation.util.Utils;
import com.buct.graduation.util.spider.SpiderLetpubJournal;
import com.buct.graduation.util.spider.SpiderWOS;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.*;

public class Excel2Excel {
    private List<Project> projects = new ArrayList<>();
    private List<Patent> patents = new ArrayList<>();
    private List<Article> articles = new ArrayList<>();
    private List<ConferencePaper> papers = new ArrayList<>();
    private Reporter reporter = new Reporter();

    public static void main(String[] args) {
                String path = "D:\\temp\\reporter\\";
//        String file = "刘金铎-附件5：信息学院人才引进综合评价模板.xlsx";
        String file = "附件 信息学院人才引进综合评价模板 秦泗甜.xlsx";
//        String file = "宋文凤-附件5：信息学院人才引进综合评价模板.xlsx";
//        String file = "王微微-附件5：信息学院人才引进综合评价模板.xlsx";
//        String file = "王晓静-附件5：信息学院人才引进综合评价模板.xlsx";
        getReporter(path+file);
    }
    public static String getReporter(String path) {
        Excel2Excel excel_obj = new Excel2Excel();

        //readExcel
        try {
            excel_obj = excel_obj.readExcel(path);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //getPaperDate
        //excel_obj.getPaperDate(excel_obj);

        //saveExcel


        //readExcel
//        try {
//            excel_obj = excel_obj.readExcel(path+file);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
        //getScore
        Reporter reporter = Utils.getScore(excel_obj.reporter, excel_obj.projects, excel_obj.patents, excel_obj.articles, excel_obj.papers);
        excel_obj.getReporter().setCheckbox(new ExcelCheckBox(reporter));
        //saveExcel
        //System.out.println(reporter.getEducation());
        String path2 = XLSTransformerExport.exportData(excel_obj);
        System.out.println("path:"+path2);
        return path2;
    }

    public static String getReporterNoData(String path) {
        Excel2Excel excel_obj = new Excel2Excel();

        //readExcel
        try {
            excel_obj = excel_obj.readExcelNoData(path);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //getPaperDate
        excel_obj.getPaperDate(excel_obj);

        //saveExcel

        //getScore
        Reporter reporter = Utils.getScore(excel_obj.reporter, excel_obj.projects, excel_obj.patents, excel_obj.articles, excel_obj.papers);
        excel_obj.getReporter().setCheckbox(new ExcelCheckBox(reporter));
        //saveExcel
        //System.out.println(reporter.getEducation());
        return XLSTransformerExport.exportData(excel_obj);
    }

    private static volatile int op = 0;
    private static synchronized void count(){
        op++;
    }

    private void saveReporter(Reporter reporter){

    }

    private Excel2Excel getPaperDate(Excel2Excel excel){
        excel.articles = Collections.synchronizedList(excel.articles);
        op = 0;
        for (Article article: excel.articles){
/*            System.out.println("article1:"+article.getId());
            SpiderWOS wos = new SpiderWOS();
            Article a = wos.getArticleByTitle(article.getName());
            //todo find bug FORGET ID_NUMBER fixed

            if(a != null){
                a.setId(article.getId());
                int index = excel.articles.indexOf(article);
                excel.articles.set(index, a);
            }else {
                article.setNotes("not find");
            }
            System.out.println("article2:"+article.getId());
            count();*/
            ThreadPoolUtil.getThreadPool().execute(new Runnable() {
                @Override
                public void run() {
                    System.out.println("article1:"+article.getId());
                    int index = excel.articles.indexOf(article);
                    SpiderWOS wos = new SpiderWOS();
                    Article a = wos.getArticleByTitle(article.getName());
                    //todo find bug FORGET ID_NUMBER
                    if(a != null){
                        a.setId(article.getId());
                        excel.articles.set(index, a);
                        System.out.println("article21:"+article.getId());
                        count();
                    }else {
                        articles.get(index).setNotes("not find article");
                        System.out.println("article20:"+article.getId());
                        count();
                    }
                }
            });
        }
//        ThreadPoolUtil.getThreadPool().shutdown();
        while (op < articles.size()){
            try {
                System.out.println("wait for finish search"+op);
                LogUtil.getInstance().addLog("log-wait for finish search");
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        for (Article a: excel.articles){
            SpiderLetpubJournal letpub = new SpiderLetpubJournal();
            if(a.getNotes().equals("not find") || a.getJournalIssn().equals("")){
                System.out.println("not find "+a.getName()+" not search journal");
                continue;
            }
            System.out.println("search0 J:" + a.getJournalIssn());
            Journal journal = letpub.getJournal(a.getJournalIssn());
            if(journal == null){
                a.setNotes(a.getNotes()+", journal either");
                System.out.println("search J error:" + a.getJournalIssn());
            }else {
                System.out.println("find J:" + journal.getName());
                a.setJournal(journal);
                //excel.articles.get(index).setJournal(letpub.getJournal(article.getJournalIssn()));
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return excel;
    }

    private Excel2Excel readExcel(String path) throws Exception {
        Excel2Excel excelObj = new Excel2Excel();
        InputStream is = new FileInputStream(new File(path));
        Workbook excel = WorkbookFactory.create(is);
        is.close();

        // 遍历所有表格
        for (int numSheet = 0; numSheet < excel.getNumberOfSheets(); numSheet++) {
            Sheet sheet = excel.getSheetAt(numSheet);
            //System.out.println(sheet.getSheetName()); //输出该表格的名称
            //System.out.println(sheet.getLastRowNum());
            switch (sheet.getSheetName()){
                case "基本信息":{
                    if(sheet.getRow(2).getCell(2) != null)
                        excelObj.reporter.setName(sheet.getRow(2).getCell(2).getStringCellValue());
                    if(sheet.getRow(3).getCell(2) != null)
                        excelObj.reporter.setEducation(sheet.getRow(3).getCell(2).getStringCellValue());
                    if(sheet.getRow(4).getCell(2) != null)
                        excelObj.reporter.setTitle(sheet.getRow(4).getCell(2).getStringCellValue());
                    if(sheet.getRow(5).getCell(2) != null)
                        excelObj.reporter.setFund(sheet.getRow(5).getCell(2).getStringCellValue());
                    //System.out.println(excelObj.reporter.getEducation());
                    break;
                }
                case "项目":{
                    for (int rowNum = 2; rowNum <= sheet.getLastRowNum(); rowNum++) {
                        Row row = sheet.getRow(rowNum);
                        if (row == null) {
                            continue;
                        }
                        if(row.getCell(1) == null || row.getCell(1).getStringCellValue() == null || row.getCell(1).getStringCellValue().equals(""))
                            break;
                        Project stu = new Project();
//                        Cell cell0 = row.getCell(0);
                        stu.setId(rowNum-1);
                        ////System.out.println(stu.getId());
                        Cell cell1 = row.getCell(1);
                        stu.setName(cell1.getStringCellValue());
                        Cell cell2 = row.getCell(2);
                        stu.setFunds(cell2.getNumericCellValue());
                        excelObj.projects.add(stu);
                    }
                    //System.out.println("项目"+excelObj.projects.size());
                    break;
                }
                case "专利":{
                    for (int rowNum = 2; rowNum <= sheet.getLastRowNum(); rowNum++) {
                        Row row = sheet.getRow(rowNum);
                        if (row == null) {
                            continue;
                        }
                        if(row.getCell(1) == null || row.getCell(1).getStringCellValue() == null || row.getCell(1).getStringCellValue().equals(""))
                            break;
                        Patent stu = new Patent();
                        stu.setId(rowNum-1);
//                        Cell cell0 = row.getCell(0);
//                        stu.setId(new Double(cell0.getNumericCellValue()).intValue());
                        Cell cell1 = row.getCell(1);
                        stu.setName(cell1.getStringCellValue());
                        Cell cell2 = row.getCell(2);
                        stu.setCategory(cell2.getStringCellValue());
                        excelObj.patents.add(stu);
                    }
                    //System.out.println("专利"+excelObj.patents.size());
                    break;
                }
                case "期刊论文":{
                    for (int rowNum = 2; rowNum <= sheet.getLastRowNum(); rowNum++) {
                        Row row = sheet.getRow(rowNum);
                        if (row == null) {
                            continue;
                        }
                        if(row.getCell(1) == null || row.getCell(1).getStringCellValue() == null || row.getCell(1).getStringCellValue().equals(""))
                            break;
                        Article stu = new Article();
//                        Cell cell0 = row.getCell(0);
//                        stu.setId(new Double(cell0.getNumericCellValue()).intValue());
                        stu.setId(rowNum-1);
                        Journal journal = new Journal();
                        Cell cell1 = row.getCell(1);
                        stu.setName(cell1.getStringCellValue());

                        Cell cell2 = row.getCell(2);
                        stu.setJournalIssn(cell2.getStringCellValue());
                        journal.setName(cell2.getStringCellValue());

                        Cell cell3 = row.getCell(3);

                        journal.setSection(cell3.getStringCellValue());

                        Cell cell4 = row.getCell(4);

                        if(cell4 == null){
                            journal.setTop(false);
                        }else {
                            journal.setTop("是".equals(cell4.getStringCellValue()));
                        }
                        Cell cell5 = row.getCell(5);
                        if(cell5 != null)
                            journal.setIF(Float.parseFloat(""+cell5.getNumericCellValue()));

                        Cell cell6 = row.getCell(6);
                        if(cell6 != null) {
                            stu.setCitation(new Double(cell6.getNumericCellValue()).intValue());
                        }else {
                            stu.setCitation(0);
                        }
                            Cell cell7 = row.getCell(7);
                        //System.out.println(""+rowNum+" ");
                        if(cell7 == null){
                            stu.setESI(false);
                        }else {
                            stu.setESI("是".equals(cell7.getStringCellValue()));
                        }
                        stu.setJournal(journal);
                        excelObj.articles.add(stu);
                    }
                    //System.out.println("期刊"+excelObj.articles.size());
                    break;
                }
                case "会议论文":{
                    for (int rowNum = 2; rowNum <= sheet.getLastRowNum(); rowNum++) {
                        Row row = sheet.getRow(rowNum);
                        if (row == null) {
                            continue;
                        }
                        if(row.getCell(1) == null || row.getCell(1).getStringCellValue() == null || row.getCell(1).getStringCellValue().equals(""))
                            break;
                        ConferencePaper stu = new ConferencePaper();
                        stu.setId(rowNum-1);
//                        Cell cell0 = row.getCell(0);
//                        stu.setId(new Double(cell0.getNumericCellValue()).intValue());
                        Cell cell1 = row.getCell(1);
                        stu.setName(cell1.getStringCellValue());

                        Cell cell2 = row.getCell(2);
                        stu.setConference(cell2.getStringCellValue());


//                        Journal journal = new Journal();
                        Cell cell3 = row.getCell(3);
                        if(cell3 != null)
                            stu.setSection(cell3.getStringCellValue());
                        else
                            stu.setSection("无");
//istop
//                        Cell cell4 = row.getCell(4);
//                        stu.setTop("是".equals(cell4.getStringCellValue()));
//影响因子
//                        Cell cell5 = row.getCell(5);
//                        journal.setIF(Float.parseFloat(""+cell5.getNumericCellValue()));

                        Cell cell4 = row.getCell(4);
                        if(cell4 != null)
                            stu.setCitation(new Double(cell4.getNumericCellValue()).intValue());
                        else
                            stu.setCitation(0);
                        Cell cell5 = row.getCell(5);
//                        stu.setEsi("是".equals(cell7.getStringCellValue()));
                        if(cell5 == null){
                            stu.setEsi(false);
                        }else {
                            stu.setEsi("是".equals(cell5.getStringCellValue()));
                        }
                        Cell cell6 = row.getCell(6);
                        if(cell6 != null) {
                            stu.setNotes(cell6.getStringCellValue());
                        }

                        excelObj.papers.add(stu);
                    }
                    //System.out.println("会议"+excelObj.papers.size());
                    break;
                }
            }
        }
        return excelObj;
    }

    private Excel2Excel readExcelNoData(String path) throws Exception {
        Excel2Excel excelObj = new Excel2Excel();
        InputStream is = new FileInputStream(new File(path));
        Workbook excel = WorkbookFactory.create(is);
        is.close();

        // 遍历所有表格
        for (int numSheet = 0; numSheet < excel.getNumberOfSheets(); numSheet++) {
            Sheet sheet = excel.getSheetAt(numSheet);
            //System.out.println(sheet.getSheetName()); //输出该表格的名称
            //System.out.println(sheet.getLastRowNum());
            switch (sheet.getSheetName()){
                case "基本信息":{
                    if(sheet.getRow(2).getCell(2) != null)
                        excelObj.reporter.setName(sheet.getRow(2).getCell(2).getStringCellValue());
                    if(sheet.getRow(3).getCell(2) != null)
                        excelObj.reporter.setEducation(sheet.getRow(3).getCell(2).getStringCellValue());
                    if(sheet.getRow(4).getCell(2) != null)
                        excelObj.reporter.setTitle(sheet.getRow(4).getCell(2).getStringCellValue());
                    if(sheet.getRow(5).getCell(2) != null)
                        excelObj.reporter.setFund(sheet.getRow(5).getCell(2).getStringCellValue());
                    //System.out.println(excelObj.reporter.getEducation());
                    break;
                }
                case "项目":{
                    for (int rowNum = 2; rowNum <= sheet.getLastRowNum(); rowNum++) {
                        Row row = sheet.getRow(rowNum);
                        if (row == null) {
                            continue;
                        }
                        if(row.getCell(1) == null || row.getCell(1).getStringCellValue() == null || row.getCell(1).getStringCellValue().equals(""))
                            break;
                        Project stu = new Project();
                        stu.setId(rowNum-1);
//                        Cell cell0 = row.getCell(0);
//                        stu.setId(new Double(cell0.getNumericCellValue()).intValue());
                        ////System.out.println(stu.getId());
                        Cell cell1 = row.getCell(1);
                        stu.setName(cell1.getStringCellValue());
                        Cell cell2 = row.getCell(2);
                        if(cell2 != null) {
                            stu.setFunds(cell2.getNumericCellValue());
                        }else {
                            stu.setFunds(Double.parseDouble("0"));
                        }
                        excelObj.projects.add(stu);
                    }
                    //System.out.println("项目"+excelObj.projects.size());
                    break;
                }
                case "专利":{
                    for (int rowNum = 2; rowNum <= sheet.getLastRowNum(); rowNum++) {
                        Row row = sheet.getRow(rowNum);
                        if (row == null) {
                            continue;
                        }
                        if(row.getCell(1) == null || row.getCell(1).getStringCellValue() == null || row.getCell(1).getStringCellValue().equals(""))
                            break;
                        Patent stu = new Patent();
                        stu.setId(rowNum-1);
//                        Cell cell0 = row.getCell(0);
//                        stu.setId(new Double(cell0.getNumericCellValue()).intValue());
                        Cell cell1 = row.getCell(1);
                        stu.setName(cell1.getStringCellValue());
                        Cell cell2 = row.getCell(2);
                        stu.setCategory(cell2.getStringCellValue());
                        excelObj.patents.add(stu);
                    }
                    //System.out.println("专利"+excelObj.patents.size());
                    break;
                }
                case "期刊论文":{
                    for (int rowNum = 2; rowNum <= sheet.getLastRowNum(); rowNum++) {
                        Row row = sheet.getRow(rowNum);
                        if (row == null) {
                            continue;
                        }
                        if(row.getCell(1) == null || row.getCell(1).getStringCellValue() == null || row.getCell(1).getStringCellValue().equals(""))
                            break;
                        Article stu = new Article();
                        stu.setId(rowNum-1);
//                        Cell cell0 = row.getCell(0);
//                        stu.setId(new Double(cell0.getNumericCellValue()).intValue());
                        Journal journal = new Journal();
                        Cell cell1 = row.getCell(1);
                        stu.setName(cell1.getStringCellValue());

                        if(!(row.getCell(2) == null || row.getCell(2).getStringCellValue() == null || row.getCell(2).getStringCellValue().equals(""))){
                            Cell cell2 = row.getCell(2);
                            stu.setJournalIssn(cell2.getStringCellValue());
                            journal.setName(cell2.getStringCellValue());
                        }

                        /*Cell cell3 = row.getCell(3);

                        journal.setSection(cell3.getStringCellValue());

                        Cell cell4 = row.getCell(4);

                        if(cell4 == null){
                            journal.setTop(false);
                        }else {
                            journal.setTop("是".equals(cell4.getStringCellValue()));
                        }
                        Cell cell5 = row.getCell(5);
                        if(cell5 != null)
                            journal.setIF(Float.parseFloat(""+cell5.getNumericCellValue()));

                        Cell cell6 = row.getCell(6);
                        if(cell6 != null) {
                            stu.setCitation(new Double(cell6.getNumericCellValue()).intValue());
                        }else {
                            stu.setCitation(0);
                        }
                        Cell cell7 = row.getCell(7);
                        //System.out.println(""+rowNum+" ");
                        if(cell7 == null){
                            stu.setESI(false);
                        }else {
                            stu.setESI("是".equals(cell7.getStringCellValue()));
                        }*/
                        stu.setJournal(journal);
                        excelObj.articles.add(stu);
                    }
                    //System.out.println("期刊"+excelObj.articles.size());
                    break;
                }
                case "会议论文":{
                    for (int rowNum = 2; rowNum <= sheet.getLastRowNum(); rowNum++) {
                        Row row = sheet.getRow(rowNum);
                        if (row == null) {
                            continue;
                        }
                        if(row.getCell(1) == null || row.getCell(1).getStringCellValue() == null || row.getCell(1).getStringCellValue().equals(""))
                            break;
                        ConferencePaper stu = new ConferencePaper();
                        stu.setId(rowNum-1);
//                        Cell cell0 = row.getCell(0);
//                        stu.setId(new Double(cell0.getNumericCellValue()).intValue());
                        Cell cell1 = row.getCell(1);
                        stu.setName(cell1.getStringCellValue());

                        Cell cell2 = row.getCell(2);
                        stu.setConference(cell2.getStringCellValue());


//                        Journal journal = new Journal();
                        Cell cell3 = row.getCell(3);
                        if(cell3 != null)
                            stu.setSection(cell3.getStringCellValue());
                        else
                            stu.setSection("无");
//istop
//                        Cell cell4 = row.getCell(4);
//                        stu.setTop("是".equals(cell4.getStringCellValue()));
//影响因子
//                        Cell cell5 = row.getCell(5);
//                        journal.setIF(Float.parseFloat(""+cell5.getNumericCellValue()));

                        Cell cell4 = row.getCell(4);
                        if(cell4 != null)
                            stu.setCitation(new Double(cell4.getNumericCellValue()).intValue());
                        else
                            stu.setCitation(0);
                        Cell cell5 = row.getCell(5);
//                        stu.setEsi("是".equals(cell7.getStringCellValue()));
                        if(cell5 == null){
                            stu.setEsi(false);
                        }else {
                            stu.setEsi("是".equals(cell5.getStringCellValue()));
                        }
                        Cell cell6 = row.getCell(6);
                        if(cell5 != null) {
                            stu.setNotes(cell6.getStringCellValue());
                        }

                        excelObj.papers.add(stu);
                    }
                    //System.out.println("会议"+excelObj.papers.size());
                    break;
                }
            }
        }
        return excelObj;
    }


    /**
     * 替换Excel模板文件内容 (主方法测试用)
     * @Title: replaceModelTest
     * @param sheetNum 代表第几个sheet，下标从0开始
     * @param map   键值对存储要替换的数据
     * @param sourceFilePath Excel模板文件路径
     * @param targetFilePath Excel生成文件路径
     * @return boolean    返回类型
     * @author duyp
     * @date 2018年10月18日 下午4:05:21
     *
     */
    public static boolean replaceModelTest(int sheetNum, Map<String, String> map, String sourceFilePath, String targetFilePath) {
        boolean bool = true;
        try {
            POIFSFileSystem fs  =new POIFSFileSystem(new FileInputStream(sourceFilePath));
            HSSFWorkbook wb = new HSSFWorkbook(fs);
            HSSFSheet sheet = wb.getSheetAt(sheetNum);//获取第一个sheet里的内容
            //循环map中的键值对，替换excel中对应的键的值（注意，excel模板中的要替换的值必须跟map中的key值对应，不然替换不成功）
            if(map.size() > 0){
                for(String atr:map.keySet()){
                    int rowNum= sheet.getLastRowNum();//该sheet页里最多有几行内容
                    for(int i=0;i<rowNum;i++){//循环每一行
                        HSSFRow row=sheet.getRow(i);
                        int colNum=row.getLastCellNum();//该行存在几列
                        for(int j=0;j<colNum;j++){//循环每一列
                            HSSFCell cell = row.getCell((short)j);
                            String str = cell.getStringCellValue();//获取单元格内容  （行列定位）
                            if(atr.equals(str)){
                                //写入单元格内容
                                cell.setCellType(CellType.STRING);
                                cell.setCellValue(map.get(atr)); //替换单元格内容
                            }
                        }
                    }
                }
            }
            // 输出文件
            FileOutputStream out = new FileOutputStream(targetFilePath);
            wb.write(out);
            out.close();

        } catch (Exception e) {
            bool = false;
            e.printStackTrace();
        }
        return bool;
    }

    public List<Project> getProjects() {
        return projects;
    }

    public List<Patent> getPatents() {
        return patents;
    }

    public List<Article> getArticles() {
        return articles;
    }

    public List<ConferencePaper> getPapers() {
        return papers;
    }

    public Reporter getReporter() {
        return reporter;
    }
}
