package com.example.recapitulare2_test;

import java.io.Serializable;

public class HusaTelefon implements Serializable {
    private String material;
    private  float lungime;
    private String culoare;

    public HusaTelefon(String material, float lungime, String culoare) {
        this.material = material;
        this.lungime = lungime;
        this.culoare = culoare;
    }

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    public float getLungime() {
        return lungime;
    }

    public void setLungime(float lungime) {
        this.lungime = lungime;
    }

    public String getCuloare() {
        return culoare;
    }

    public void setCuloare(String culoare) {
        this.culoare = culoare;
    }

    @Override
    public String toString() {
        return "HusaTelefon{" +
                "material='" + material + '\'' +
                ", lungime=" + lungime +
                ", culoare='" + culoare + '\'' +
                '}';
    }
}
