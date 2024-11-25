package com.example.recapitulare3_test;

import java.io.Serializable;
import java.util.Date;

public class Melodie implements Serializable {
    private String numeMelodie;
    private String gen;
    private float durata;
    private Date publicare;
    private String feedback;


    public Melodie(String numeMelodie, String gen, float durata, Date publicare, String feedback) {
        this.numeMelodie = numeMelodie;
        this.gen = gen;
        this.durata = durata;
        this.publicare = publicare;
        this.feedback = feedback;
    }

    public String getNumeMelodie() {
        return numeMelodie;
    }

    public void setNumeMelodie(String numeMelodie) {
        this.numeMelodie = numeMelodie;
    }

    public String getGen() {
        return gen;
    }

    public void setGen(String gen) {
        this.gen = gen;
    }

    public float getDurata() {
        return durata;
    }

    public void setDurata(float durata) {
        this.durata = durata;
    }

    public Date getPublicare() {
        return publicare;
    }

    public void setPublicare(Date publicare) {
        this.publicare = publicare;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }

    @Override
    public String toString() {
        return "Melodie{" +
                "numeMelodie='" + numeMelodie + '\'' +
                ", gen='" + gen + '\'' +
                ", durata=" + durata +
                ", publicare=" + publicare +
                ", feedback='" + feedback + '\'' +
                '}';
    }
}
