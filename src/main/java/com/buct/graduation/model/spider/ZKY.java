package com.buct.graduation.model.spider;

public class ZKY{
    public boolean TOP;
    public String name;
    public int section;

    @Override
    public String toString(){
        return "isTop:"+ TOP+" name:"+name+" section:"+section+"\n";
    }

    public boolean isTOP() {
        return TOP;
    }

    public void setTOP(boolean TOP) {
        this.TOP = TOP;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSection() {
        return section;
    }

    public void setSection(int section) {
        this.section = section;
    }
}
