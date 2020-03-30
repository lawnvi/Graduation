package com.buct.graduation.util.excel;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.buct.graduation.model.pojo.Reporter;
import org.apache.commons.collections.map.HashedMap;
import org.apache.poi.ss.usermodel.Workbook;

import net.sf.jxls.transformer.XLSTransformer;

public class ModelExcel {
    public static void main(String[] args) {
        // 测试导出数据
        Reporter reporter = new Reporter();
        reporter.setFund("NSFC青年");
        reporter.setName("ali");
        reporter.setTitle("优青");
        reporter.setIF(235);
        reporter.setSciCitation(23456);
        reporter.setCitation(23423);
        reporter.setEsi(243);
        reporter.setFunds(846489);
        reporter.setJcr(124);
        reporter.setScore(99999.0);
        reporter.setJcrScore(34534);
        reporter.setPost("教授");
        export(reporter);
    }

    
    @SuppressWarnings({"unchecked"})
    public static void export(Reporter reporter) {
        FileOutputStream out = null;
        InputStream in = null;
        try {
            // 导出文件名
            String name = reporter.getName()+".xlsx";

            // 导出路径（本地路劲）
            String exportPath = "D:\\temp\\reporter";

            String modelPath = "D:\\temp\\reporter\\综合评价结果模板.xlsx";
            
            // 时间节
//            String dateSplit = UploadUtils.getDateSplit(null);

            File file = new File(modelPath); // 判断文件是否存在，不存在就新建
            if (!file.exists()) {
                file.mkdirs();
            }
            // 获取数据（模拟查库）reporter
            
            // 模板数据以Map封装List集合进行遍历读取
            Map<String, Object> map = new HashMap();
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

            String path = exportPath + "/" + name;
            XLSTransformer transformer = new XLSTransformer();
            
            // 读入Excel模板
            InputStream stream =  ModelExcel.class.getResourceAsStream(modelPath);
            in = new BufferedInputStream(stream);
            
            // 获取Excel
            Workbook workBook = transformer.transformXLS(in, map);
            
            // 输出Excel文件
            out = new FileOutputStream(path);
            workBook.write(out);
            out.flush();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                }
            }
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                }
            }
        }
    }

}