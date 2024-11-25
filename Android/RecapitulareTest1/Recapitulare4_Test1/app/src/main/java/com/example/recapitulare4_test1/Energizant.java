package com.example.recapitulare4_test1;

import java.io.Serializable;

public class Energizant implements Serializable {
    private String den;
    private int cantitate;
    private String aroma;
    private String indulcit;

    public Energizant(String den, int cantitate, String aroma, String indulcit) {
        this.den = den;
        this.cantitate = cantitate;
        this.aroma = aroma;
        this.indulcit = indulcit;
    }

    public String getDen() {
        return den;
    }

    public void setDen(String den) {
        this.den = den;
    }

    public int getCantitate() {
        return cantitate;
    }

    public void setCantitate(int cantitate) {
        this.cantitate = cantitate;
    }

    public String getAroma() {
        return aroma;
    }

    public void setAroma(String aroma) {
        this.aroma = aroma;
    }

    public String getIndulcit() {
        return indulcit;
    }

    public void setIndulcit(String indulcit) {
        this.indulcit = indulcit;
    }

    @Override
    public String toString() {
        return "Energizant{" +
                "den='" + den + '\'' +
                ", cantitate=" + cantitate +
                ", aroma='" + aroma + '\'' +
                ", indulcit='" + indulcit + '\'' +
                '}';
    }
}
