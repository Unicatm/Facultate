package com.example.recap6;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;
import java.util.Date;

@Entity(tableName = "mancare")
public class Mancare implements Serializable {
    @PrimaryKey(autoGenerate = true)
    private Long id;
    private String den;
    private double kcal;
    private Date dataExp;

    public Mancare(String den, double kcal, Date dataExp) {
        this.den = den;
        this.kcal = kcal;
        this.dataExp = dataExp;
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

    public double getKcal() {
        return kcal;
    }

    public void setKcal(double kcal) {
        this.kcal = kcal;
    }

    public Date getDataExp() {
        return dataExp;
    }

    public void setDataExp(Date dataExp) {
        this.dataExp = dataExp;
    }

    @Override
    public String toString() {
        return "Mancare{" +
                "den='" + den + '\'' +
                ", kcal=" + kcal +
                ", dataExp=" + dataExp +
                '}';
    }
}
