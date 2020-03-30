package com.buct.graduation.util.excel;

import com.buct.graduation.model.pojo.Reporter;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Component
public class ExcelUtils {

    private SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

//    @Resource
//    private ResumeService resumeService;

    //声明一个该工具类的静态的内部对象
    private static ExcelUtils excelUtils;

    //工具类中需要注入service，dao等需要
    //使用注解@PostConstruct把需要使用的service，dao等加载到上面定义的静态内部对象中
    @PostConstruct
    public void init() {
        excelUtils = this;
//        excelUtils.resumeService = this.resumeService;
    }

    /**
     * tempPath 模板文件路径
     * path 文件路径
     * list 集合数据
     */
    public void exportExcel(String tempPath, String path, HttpServletResponse response, Reporter reporter) {
        File newFile = createNewFile(tempPath, path);
        InputStream is = null;
        HSSFWorkbook workbook = null;
        HSSFSheet sheet = null;
        try {
            is = new FileInputStream(newFile);// 将excel文件转为输入流
            workbook = new HSSFWorkbook();
            // 获取第一个sheet
            sheet = workbook.getSheetAt(0);
        } catch (Exception e1) {
            e1.printStackTrace();
        }

        if (sheet != null) {
            try {
                // 写数据
                FileOutputStream fos = new FileOutputStream(newFile);
                HSSFRow row = sheet.getRow(0);
                if (row == null) {
                    row = sheet.createRow(0);
                }
                HSSFCell cell = row.getCell(0);
                if (cell == null) {
                    cell = row.createCell(0);
                }
                cell.setCellValue("我是标题");

                row = sheet.createRow(2); //从第三行开始

                //这里就可以使用sysUserMapper，做相应的操作
                //User user = excelUtils.sysUserMapper.selectByPrimaryKey(vo.getId());

                //根据excel模板格式写入数据....
                createRowAndCell(reporter.getScore(), row, cell, 0);
                createRowAndCell(reporter.getJcr(), row, cell, 1);
                createRowAndCell(reporter.getJcrScore(), row, cell, 2);
                createRowAndCell(reporter.getPost(), row, cell, 3);
                //.....

                workbook.write(fos);
                fos.flush();
                fos.close();

                // 下载
                InputStream fis = new BufferedInputStream(new FileInputStream(
                        newFile));
                byte[] buffer = new byte[fis.available()];
                fis.read(buffer);
                fis.close();
                response.reset();
                response.setContentType("text/html;charset=UTF-8");
                OutputStream toClient = new BufferedOutputStream(
                        response.getOutputStream());
                response.setContentType("application/x-msdownload");
                String newName = URLEncoder.encode(
                        "reporter" + System.currentTimeMillis() + ".xls",
                        "UTF-8");
                response.addHeader("Content-Disposition",
                        "attachment;filename=\"" + newName + "\"");
                response.addHeader("Content-Length", "" + newFile.length());
                toClient.write(buffer);
                toClient.flush();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    if (null != is) {
                        is.close();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        // 删除创建的新文件
        this.deleteFile(newFile);
    }

    /**
     * 根据当前row行，来创建index标记的列数,并赋值数据
     */
    private void createRowAndCell(Object obj, HSSFRow row, HSSFCell cell, int index) {
        cell = row.getCell(index);
        if (cell == null) {
            cell = row.createCell(index);
        }

        if (obj != null)
            cell.setCellValue(obj.toString());
        else
            cell.setCellValue("");
    }

    /**
     * 复制文件
     *
     * @param s 源文件
     * @param t 复制到的新文件
     */

    public void fileChannelCopy(File s, File t) {
        try {
            InputStream in = null;
            OutputStream out = null;
            try {
                in = new BufferedInputStream(new FileInputStream(s), 1024);
                out = new BufferedOutputStream(new FileOutputStream(t), 1024);
                byte[] buffer = new byte[1024];
                int len;
                while ((len = in.read(buffer)) != -1) {
                    out.write(buffer, 0, len);
                }
            } finally {
                if (null != in) {
                    in.close();
                }
                if (null != out) {
                    out.close();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 读取excel模板，并复制到新文件中供写入和下载
     *
     * @return
     */
    public File createNewFile(String tempPath, String rPath) {
        // 读取模板，并赋值到新文件************************************************************
        // 文件模板路径
        String path = (tempPath);
        File file = new File(path);
        // 保存文件的路径
        String realPath = rPath;
        // 新的文件名
        String newFileName = System.currentTimeMillis() + ".xls";
        // 判断路径是否存在
        File dir = new File(realPath);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        // 写入到新的excel
        File newFile = new File(realPath, newFileName);
        try {
            newFile.createNewFile();
            // 复制模板到新文件
            fileChannelCopy(file, newFile);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return newFile;
    }

    /**
     * 下载成功后删除
     *
     * @param files
     */
    private void deleteFile(File... files) {
        for (File file : files) {
            if (file.exists()) {
                file.delete();
            }
        }
    }

    public static List<Student> readXlSX(String path) throws Exception {
        List<Student> stuList = new ArrayList<Student>();
        InputStream is = new FileInputStream(new File(path));
        Workbook excel = WorkbookFactory.create(is);
        is.close();

        // 遍历所有表格
        for (int numSheet = 0; numSheet < excel.getNumberOfSheets(); numSheet++) {
            Sheet sheet = excel.getSheetAt(numSheet);
            System.out.println(sheet.getSheetName()); //输出该表格的名称
            //遍历所有行
            for (int rowNum = 2; rowNum <= sheet.getLastRowNum(); rowNum++) {
                Row row = sheet.getRow(rowNum);
                if (row == null) {
                    continue;
                }
                Student stu = new Student();
                Cell cell0 = row.getCell(0);
                stu.setYear(new Double(cell0.getNumericCellValue()).intValue()+"");
                Cell cell1 = row.getCell(1);
                stu.setNumber((int) cell1.getNumericCellValue());
                Cell cell2 = row.getCell(2);
                stu.setName(cell2.getStringCellValue());
                Cell cell3 = row.getCell(3);
                stu.setAuthorType(cell3.getStringCellValue());
                Cell cell4 = row.getCell(4);
                stu.setSubject(cell4.getStringCellValue());
                Cell cell5 = row.getCell(5);
                stu.setPaper(cell5.getStringCellValue());
                Cell cell6 = row.getCell(6);
                stu.setJournal(cell6.getStringCellValue());
                Cell cell7 = row.getCell(7);
                stu.setSci(cell7.getStringCellValue());
                Cell cell8 = row.getCell(8);
                stu.setTime(cell8.getStringCellValue());
                Cell cell9 = row.getCell(9);
                stu.setXueyuan(cell9.getStringCellValue());
                Cell cell10 = row.getCell(10);
                stu.setIsEsi(cell10.getStringCellValue());
                stuList.add(stu);
            }
        }
        return stuList;
    }

    public static Workbook write2ExcelStudent(List<Student> list) {
        List<String> title = new ArrayList<>();
        title.add("年度");
        title.add("序号");
        title.add("作者姓名");
        title.add("第一作者/通讯作者");
        title.add("学科");
        title.add("论文标题");
        title.add("发表期刊");
        title.add("卷号");
        title.add("期号");
        title.add("页码");
        title.add("收录情况");
        title.add("发表年月");
        title.add("所在学院");
        title.add("是否高被引");
        String style = ".xlsx";
        Workbook workbook;
        if (".XLS".equals(style.toUpperCase())) {
            workbook = new HSSFWorkbook();
        } else {
            workbook = new XSSFWorkbook();
        }
        // 生成一个表格
        Sheet sheet = workbook.createSheet("sheet1");
        Row row = sheet.createRow(0);
        /**
         * 创建表头信息
         */
        for (int i = 0; i < title.size(); i++) {
            Cell cell = row.createCell(i);
            cell.setCellValue(title.get(i));
        }
        /**
         * 创建表格信息
         */
        Iterator<Student> iterator = list.iterator();
        int index = 0;
        while (iterator.hasNext()) {
            index++;
            row = sheet.createRow(index);
            Student aArticle = iterator.next();
            int length = aArticle.getClass().getDeclaredFields().length;
            System.out.println("Article类属性数量为：" + length);
            for (int i = 0; i < length; i++) {
                Cell cell = row.createCell(i);
                //依次对应实体对象的属性
                switch (i) {
                    case 0:
                        cell.setCellValue(aArticle.getYear());
                        break;
                    case 1:
                        cell.setCellValue(aArticle.getNumber());
                        break;
                    case 2:
                        cell.setCellValue(aArticle.getName());
                        break;
                    case 3:
                        cell.setCellValue(aArticle.getAuthorType());
                        break;
                    case 4:
                        cell.setCellValue(aArticle.getSubject());
                        break;
                    case 5:
                        cell.setCellValue(aArticle.getPaper());
                        break;
                    case 6:
                        cell.setCellValue(aArticle.getJournal());
                        break;
                    case 7:
                        cell.setCellValue(aArticle.getVolume());
                        break;
                    case 8:
                        cell.setCellValue(aArticle.getIssue());
                        break;
                    case 9:
                        cell.setCellValue(aArticle.getPage());
                        break;
                    case 10:
                        cell.setCellValue(aArticle.getSci());
                        break;
                    case 11:
                        cell.setCellValue(aArticle.getTime());
                        break;
                    case 12:
                        cell.setCellValue(aArticle.getXueyuan());
                        break;
                    case 13:
                        cell.setCellValue(aArticle.getIsEsi());
                        break;
                    default:
                        System.out.println("【异常】Article类属性数量为：" + length + " | i=" + i);
                        break;
                }
            }
        }
        /**
         * 写入到文件中
         */
        /*
        boolean isCorrect = false;
        File file = new File("D:\\");
        OutputStream outputStream = null;
        try {
            outputStream = new FileOutputStream(file);
            workbook.write(outputStream);
            isCorrect = true;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (null != outputStream) {
                    outputStream.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }*/


        return workbook;
    }
}
