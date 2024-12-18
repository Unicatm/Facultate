package com.example.recap4;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "burse")
public class Bursa implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private Long id;
    private String den;
    private int suma;
    private String tip;

    public Bursa(String den, int suma, String tip) {
        this.den = den;
        this.suma = suma;
        this.tip = tip;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDen() {
        return den;
    }

    public void setDen(String den) {
        this.den = den;
    }

    public int getSuma() {
        return suma;
    }

    public void setSuma(int suma) {
        this.suma = suma;
    }

    public String getTip() {
        return tip;
    }

    public void setTip(String tip) {
        this.tip = tip;
    }

    @Override
    public String toString() {
        return "Bursa{" +
                "den='" + den + '\'' +
                ", suma=" + suma +
                ", tip='" + tip + '\'' +
                '}';
    }
}
