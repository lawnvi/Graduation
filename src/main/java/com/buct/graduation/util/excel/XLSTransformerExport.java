package com.buct.graduation.util.excel;


import java.io.File;
import java.io.IOException;
import java.util.*;

import javax.swing.*;

import com.buct.graduation.model.pojo.*;
import com.buct.graduation.util.GlobalName;
import com.buct.graduation.util.Utils;
import javafx.scene.Parent;
import net.sf.jxls.exception.ParsePropertyException;
import net.sf.jxls.transformer.XLSTransformer;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

/**
 * 利用模板导出excel文件
 * @typename：XLSTransformerExport
 * @author： FishRoad
 * @since： 2015年8月24日 下午1:35:29
 *
 */
public class XLSTransformerExport {

    public static void export(Reporter reporter){

        //组织数据
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        Map<String, Object> map = new HashMap<String,Object>();
        map.put("jcr12", reporter.getJcr());
        map.put("sci", reporter.getSciCitation());
        map.put("fund", reporter.getFund());
        map.put("title", reporter.getTitle());
        map.put("esi", reporter.getEsi());
        map.put("funds", reporter.getFunds());
        map.put("post", reporter.getPost());
        map.put("jcrScore", reporter.getJcrScore());
        map.put("score", reporter.getScore());
        map.put("IF", reporter.getIF());
        map.put("citation", reporter.getCitation());
        list.add(map);
        Map<String, Object> para = new HashMap<String, Object>();
        para.put("result", list);
        XLSTransformer transformer = new XLSTransformer();

        //String temppath = "C:\\Users\\Administrator\\Documents\\测试模板.xlsx";
        //模板路径，如果用的模板是xlsx，则生成的文件类型也必须为xlsx类型，否则由于格式不对，会打不开文件
        String temppath = "D:\\temp\\reporter\\综合评价结果模板.xlsx";
        //输出文件路径，以及路径名称
        //String exportpath =getDirPath("测试结果_xls.xlsx", new File("D:\\temp\\reporter\\"));
        String exportpath="D:\\temp\\"+reporter.getName()+new Random().nextInt(5)+".xlsx";
        System.out.println(exportpath);
        try {
            //利用transformXLS来输出文件
            transformer.transformXLS(temppath, para,exportpath);
            //生成文件后提示是否立即打开该文件
            if(JOptionPane.showConfirmDialog(null, "导出成功,是否打开文件？")==0){
                openDirFile(exportpath);
            }

        } catch (ParsePropertyException e) {
            System.out.println("失败！");
        } catch (IOException e) {
            System.out.println("失败！");
        } catch (InvalidFormatException e) {
            e.printStackTrace();
        }

    }

    public static String exportData(Excel2Excel excel2Excel){
        //模板路径，如果用的模板是xlsx，则生成的文件类型也必须为xlsx类型，否则由于格式不对，会打不开文件
        String temppath = "D:\\temp\\reporter\\综合评价结果模板.xlsx";
        temppath = GlobalName.ABSOLUTE_PATH+GlobalName.EXCEL_PATH+GlobalName.EXCEL_MODEL_REPORTER;
        //组织数据
        Reporter reporter = excel2Excel.getReporter();
        List<Project> projects = excel2Excel.getProjects();
        List<Patent> patents = excel2Excel.getPatents();
        List<Article> articles = excel2Excel.getArticles();
        List<ConferencePaper> papers = excel2Excel.getPapers();
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        Map<String, Object> map = new HashMap();
//        map.put("jcr12", reporter.getJcr());
//        map.put("sci", reporter.getSciCitation());
//        map.put("fund", reporter.getFund());
//        map.put("title", reporter.getTitle());
//        map.put("esi", reporter.getEsi());
//        map.put("funds", reporter.getFunds());
//        map.put("post", reporter.getPost());
//        map.put("jcrScore", reporter.getJcrScore());
//        map.put("score", reporter.getScore());
//        map.put("IF", reporter.getIF());
//        map.put("citation", reporter.getCitation());

        Map<String, List<Patent>> patentMap = new HashMap<String,List<Patent>>();
        Map<String, List<Project>> projectMap = new HashMap<String,List<Project>>();
        Map<String, List<Article>> articleMap = new HashMap<String,List<Article>>();
        Map<String, List<ConferencePaper>> paperMap = new HashMap<String,List<ConferencePaper>>();

        map.put("reporter", reporter);
//        map.put("projects", projects);
//        map.put("patents", patents);
//        map.put("articles", articles);
//        map.put("papers", papers);
        list.add(map);
        Map<String, Object> para = new HashMap<String, Object>();
        para.put("result", list);
        para.put("projects", projects);
        para.put("patents", patents);
        para.put("articles", articles);
        para.put("papers", papers);
        XLSTransformer transformer = new XLSTransformer();

        //输出文件路径，以及路径名称
        //String exportPath = getDirPath(reporter.getName()+".xlsx", new File("D:\\temp\\reporter\\"));
        String exportPath = "D:\\temp\\"+reporter.getName()+".xlsx";
        exportPath = GlobalName.ABSOLUTE_PATH+GlobalName.EXCEL_PATH+GlobalName.EXCEL_BUFFER + Utils.getTodayPath()+Utils.getNowName()+reporter.getName()+".xlsx";
        String exportDir = GlobalName.ABSOLUTE_PATH+GlobalName.EXCEL_PATH+GlobalName.EXCEL_BUFFER + Utils.getTodayPath();
        System.out.println(exportPath);
        try {
            File file2 = new File(exportDir);
            if(!file2.isDirectory()) {
                //递归生成文件夹
                file2.mkdirs();
            }
            //转存文件到指定路径，如果文件名重复的话，将会覆盖掉之前的文件,这里是把文件上传到 “绝对路径”
            //利用transformXLS来输出文件
            transformer.transformXLS(temppath, para, exportPath);
            //生成文件后提示是否立即打开该文件
//            if(JOptionPane.showConfirmDialog(null, "导出成功,是否打开文件？")==0){
//                openDirFile(exportPath);
//            }
            return exportPath;

        } catch (ParsePropertyException | IOException e) {
            System.out.println("失败！");
        } catch (InvalidFormatException e) {
            e.printStackTrace();
        }
        return "";
    }


    /**
     * 保存文件的时候指定文件名和文件的保存路径
     * @param filename
     * @param path
     * @return
     */
    public static String getDirPath(String filename, File path) {
        JFileChooser parseDir = new JFileChooser();
        parseDir.setCurrentDirectory(path);
        parseDir.setAcceptAllFileFilterUsed(false);
        parseDir.setSelectedFile(new File(filename));
        int result = parseDir.showSaveDialog(null);
        if (result == JFileChooser.APPROVE_OPTION) {
            return parseDir.getSelectedFile().getAbsolutePath();
        } else {
            return "";
        }
    }

    /**
     * 打开文件所在的目录
     *
     * @param destFileName
     * @throws IOException
     */
    public static void openDirFile(String destFileName) throws IOException {
        File file = new File(destFileName);
        java.awt.Desktop.getDesktop().open(file);
        //

    }
}