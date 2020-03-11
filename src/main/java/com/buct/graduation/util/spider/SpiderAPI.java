package com.buct.graduation.util.spider;

import com.buct.graduation.model.spider.Periodical;
import com.buct.graduation.model.spider.PeriodicalTable;

import java.util.Calendar;

/**
 * 期刊分区信息获取
 */
public class SpiderAPI {
    /**
     * 模糊搜索期刊
     * @param keyword
     * @param year
     * @return
     */
    public PeriodicalTable getAPI_1(String keyword, int year){
        //好像只能查到前三年的
        year = Math.min(year, (Calendar.getInstance().get(Calendar.YEAR) - 3));
        HttpUtil util = new HttpUtil();
        keyword = keyword.replace(" ", "%20");
        String html = util.getHtml("https://webapi.fenqubiao.com/api/user/search?year="+year+"&keyword="+keyword+"&user=BUCT_admin&password=1204705");
        PeriodicalTable table = new PeriodicalTable(html);
        System.out.println("匹配的期刊数:"+ table.getNumber());

//        for(int i = 0; i < list.size(); i++){
//            System.out.println(list.get(i).toString());
//            //取第一个？？？？
//            if(list.get(i).isMatch() || i == 0){
//                System.out.println("matched "+ list.get(i).getTitle());
//                return list.get(i).getTitle();
//            }
//        }
        if(table.getNumber() == 0) {
            SpiderLetpub letpub = new SpiderLetpub();
            table = letpub.getPeriodicals(keyword);
        }
        return table;
    }

    /**
     * 获取期刊详细信息
     * @param kw
     * @param year
     * @return
     */
    public Periodical getAPI_2(String kw, int year){
        year = Math.min(year, (Calendar.getInstance().get(Calendar.YEAR) - 3));
        if(kw.equals("")){
            return null;
        }
        String keyword = kw.replace(" ", "%20");
        HttpUtil util = new HttpUtil();
        String html = util.getHtml("https://webapi.fenqubiao.com/api/user/get?year="+year+"&keyword="+keyword+"&user=BUCT_admin&password=1204705");
        System.out.println("分区详细信息：");
        if(html.contains("Message") || html.equals("[]")){
            System.out.println("出现错误");
        }
//        System.out.println(temp.toString());
        return new Periodical(html);
    }
}
