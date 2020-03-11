package com.buct.graduation.model.spider;

public class JCR{
    public String name_CN;
    public String name_EN;
    public int section;

    @Override
    public String toString(){
        return "nameCN:"+name_CN+" nameEN:"+name_EN+" section:"+section+"\n";
    }

    public String getName_CN() {
        return name_CN;
    }

    public void setName_CN(String name_CN) {
        this.name_CN = name_CN;
    }

    public String getName_EN() {
        return name_EN;
    }

    public void setName_EN(String name_EN) {
        this.name_EN = name_EN;
    }

    public int getSection() {
        return section;
    }

    public void setSection(int section) {
        this.section = section;
    }
}
