package com.buct.graduation.model.spider;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class PeriodicalTable {
    private int number = 0;
    private String json;
    private List<Periodical> list = new ArrayList<>();

    public PeriodicalTable() {
    }

    public PeriodicalTable(String json) {
        this.json = json;
        if(json.equals("[]")){
            System.out.println("无匹配期刊");
        }
        else if(json.contains("Message")){
            System.out.println("参数错误");
        }
        else {
            this.list = json2ObjList(json);
            this.number = this.list.size();
        }
    }

    private List<Periodical> json2ObjList(String s){
        List<Periodical> periodicals = new ArrayList<>();
        JSONArray jsonArray = new JSONArray(s);
        for(int i = 0; i < jsonArray.length(); i++){
            Periodical temp = new Periodical();
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            temp.setTitle(jsonObject.getString("Title"));
            temp.setAbbrTitle(jsonObject.getString("AbbrTitle"));
            temp.setISSN(jsonObject.getString("ISSN"));
            temp.setYear(jsonObject.getInt("Year"));
            temp.setReview(jsonObject.getBoolean("Review"));
            temp.setMatch(jsonObject.getBoolean("Match"));
            periodicals.add(temp);
        }
        return periodicals;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getJson() {
        return json;
    }

    public void setJson(String json) {
        this.json = json;
    }

    public List<Periodical> getList() {
        return list;
    }

    public void setList(List<Periodical> list) {
        this.list = list;
    }
}

class JournalsOld{
    private String Title;
    private String AbbrTitle;
    private String ISSN;
    private int Year;
    private boolean Review;
    private boolean Match;

    @Override
    public String toString(){
        return "Title:"+Title+"\nJCR:"+AbbrTitle+"\nISSN:"+ISSN+"\nYear:"+Year+"\nReview:"+Review+"\nMatch:"+Match+"\n";
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getAbbrTitle() {
        return AbbrTitle;
    }

    public void setAbbrTitle(String abbrTitle) {
        AbbrTitle = abbrTitle;
    }

    public String getISSN() {
        return ISSN;
    }

    public void setISSN(String ISSN) {
        this.ISSN = ISSN;
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

    public boolean isMatch() {
        return Match;
    }

    public void setMatch(boolean match) {
        Match = match;
    }
}
