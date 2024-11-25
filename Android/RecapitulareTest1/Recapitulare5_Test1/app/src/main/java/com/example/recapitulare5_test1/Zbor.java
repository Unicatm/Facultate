package com.example.recapitulare5_test1;

import java.io.Serializable;
import java.util.Date;

public class Zbor implements Serializable {
    private String cod;
    private Date dataZbor;
    private String tara;
    private String tipZbor;

    public Zbor(String cod, Date dataZbor, String tara, String tipZbor) {
        this.cod = cod;
        this.dataZbor = dataZbor;
        this.tara = tara;
        this.tipZbor = tipZbor;
    }

    public String getCod() {
        return cod;
    }

    public void setCod(String cod) {
        this.cod = cod;
    }

    public Date getDataZbor() {
        return dataZbor;
    }

    public void setDataZbor(Date dataZbor) {
        this.dataZbor = dataZbor;
    }

    public String getTara() {
        return tara;
    }

    public void setTara(String tara) {
        this.tara = tara;
    }

    public String getTipZbor() {
        return tipZbor;
    }

    public void setTipZbor(String tipZbor) {
        this.tipZbor = tipZbor;
    }

    @Override
    public String toString() {
        return "Zbor{" +
                "cod='" + cod + '\'' +
                ", dataZbor=" + dataZbor +
                ", tara='" + tara + '\'' +
                ", tipZbor='" + tipZbor + '\'' +
                '}';
    }
}
