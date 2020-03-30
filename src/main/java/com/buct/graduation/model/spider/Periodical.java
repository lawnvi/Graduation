package com.buct.graduation.model.spider;

import com.buct.graduation.model.pojo.Journal;
import com.buct.graduation.util.Utils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * 期刊信息
 */
public class Periodical {
    private String Title;//期刊名
    private String AbbrTitle;//简称
    private String ISSN;//唯一码？
    private int Year = Utils.getDate().getYear() - 2;//数据年份
    private boolean Review;//。。。
    private boolean Match;//是否匹配
    private List<ZKY> zkys = new ArrayList<>();//大学科分区信息
    private List<JCR> jcrs = new ArrayList<>();//小学科分区信息
    private Indicator indicator;//影响因子等数据
    private String url = "";//letpub 网址 api能查到的没有
    private String json;

    public Periodical() {
    }

    public Periodical(String s) {
        this.json = s;
        if(s.contains("Message") || s.equals("[]")){
            System.out.println("newP:s出现错误");
        }else {
            JSONObject jsonObject = new JSONObject(s);
            this.setTitle(jsonObject.getString("Title"));
            this.setAbbrTitle(jsonObject.getString("AbbrTitle"));
            this.setISSN(jsonObject.getString("ISSN"));
            this.setYear(jsonObject.getInt("Year"));
            this.setReview(jsonObject.getBoolean("Review"));
            JSONArray zkyJson = jsonObject.getJSONArray("ZKY");
            List<ZKY> list = new ArrayList<>();
            for (int i = 0; i < zkyJson.length(); i++) {
                JSONObject obj = zkyJson.getJSONObject(i);
                ZKY zky = new ZKY();
                zky.setName(obj.getString("Name"));
                zky.setTOP(obj.getBoolean("Top"));
                zky.setSection(obj.getInt("Section"));
                list.add(zky);
            }
            this.setZkys(list);
            List<JCR> listJCR = new ArrayList<>();
            JSONArray jcrJson = jsonObject.getJSONArray("JCR");
            for (int i = 0; i < jcrJson.length(); i++) {
                JSONObject obj = jcrJson.getJSONObject(i);
                JCR jcr = new JCR();
                jcr.setName_CN(obj.getString("NameCN"));
                jcr.setName_EN(obj.getString("Name"));
                jcr.setSection(obj.getInt("Section"));
                listJCR.add(jcr);
            }
            this.setJcrs(listJCR);
            JSONObject indicatorJson = jsonObject.getJSONObject("Indicator");
            Indicator indicator = new Indicator();
            indicator.setIFavg(indicatorJson.getFloat("IFavg"));
            indicator.setImpactFactor(indicatorJson.getFloat("ImpactFactor"));
            indicator.setTotalCite(indicatorJson.getInt("TotalCite"));
            indicator.setTotalCites(indicatorJson.getInt("TotalCites"));
            this.setIndicator(indicator);
        }
    }

    public Journal toJournal(){
        Journal journal = new Journal();
        journal.setName(this.Title);
        journal.setAbbrTitle(this.AbbrTitle);
        journal.setISSN(this.ISSN);
        journal.setYear(this.Year);
        journal.setIF(this.indicator.getImpactFactor());
        journal.setTop(this.zkys.get(0).isTOP());
        journal.setSection("JCR-"+this.zkys.get(0).section);
        journal.setNotes("from 中科院 API");
        journal.setUrl(this.url);
        return journal;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public boolean isMatch() {
        return Match;
    }

    public void setMatch(boolean match) {
        Match = match;
    }
    
    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getISSN() {
        return ISSN;
    }

    public void setISSN(String ISSN) {
        this.ISSN = ISSN;
    }

    public List<ZKY> getZkys() {
        return zkys;
    }

    public void setZkys(List<ZKY> zkys) {
        this.zkys = zkys;
    }

    public List<JCR> getJcrs() {
        return jcrs;
    }

    public void setJcrs(List<JCR> jcrs) {
        this.jcrs = jcrs;
    }

    public String getAbbrTitle() {
        return AbbrTitle;
    }

    public void setAbbrTitle(String abbrTitle) {
        AbbrTitle = abbrTitle;
    }

    public int getYear() {
        return Year;
    }

    public void setYear(int year) {
        Year = year;
    }

    public boolean isReview() {
        return Review;
    }

    public void setReview(boolean review) {
        Review = review;
    }

    public Indicator getIndicator() {
        return indicator;
    }

    public void setIndicator(Indicator indicator) {
        this.indicator = indicator;
    }

    public String getJson() {
        return json;
    }

    public void setJson(String json) {
        this.json = json;
    }

    public String toStringJCR(){
        StringBuffer op = new StringBuffer();
        for(int i = 0; i < jcrs.size(); i++){
            op.append(jcrs.get(i).toString());
        }
        return op.toString();
    }

    public String toStringZKY(){
        StringBuffer op = new StringBuffer();
        for(int i = 0; i < zkys.size(); i++){
            op.append(zkys.get(i).toString());
        }
        return op.toString();
    }

    @Override
    public String toString(){
        return "Title:"+Title+"\nJCR:"+AbbrTitle+"\nISSN:"+ISSN+"\nYear:"+Year+"\nReview:"+Review+"\nJCR:"+"\n"+
                toStringJCR()+"ZKY:"+toStringZKY()+"Indicator:"+indicator.toString()+"\n";
    }
}
