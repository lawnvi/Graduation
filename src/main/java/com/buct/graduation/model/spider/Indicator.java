package com.buct.graduation.model.spider;

public class Indicator {
    private float ImpactFactor;
    private float IFavg;
    private int TotalCite;
    private int TotalCites;

    @Override
    public String toString(){
        return "IF:"+ImpactFactor+" IFavg:"+IFavg+" TC:"+TotalCite+" TCs:"+TotalCites+"\n";
    }

    public float getImpactFactor() {
        return ImpactFactor;
    }

    public void setImpactFactor(float impactFactor) {
        ImpactFactor = impactFactor;
    }

    public float getIFavg() {
        return IFavg;
    }

    public void setIFavg(float IFavg) {
        this.IFavg = IFavg;
    }

    public int getTotalCite() {
        return TotalCite;
    }

    public void setTotalCite(int totalCite) {
        TotalCite = totalCite;
    }

    public int getTotalCites() {
        return TotalCites;
    }

    public void setTotalCites(int totalCites) {
        TotalCites = totalCites;
    }
}
