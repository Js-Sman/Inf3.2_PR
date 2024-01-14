package Aufgabe3_MVC.Model;

/**
 * Im Model werden alle berechnungen durchgeführt.
 * Die Datenhaltung wird auch vom Model übernommen.
 */
public class DatenModel {
    private final float faktor = 2.54F;
    private float rawValue;
    private float convValue;

    public void setRawValue(float rawValue) {
        this.rawValue = rawValue;
    }

    public float getConvValue() {
        return convValue;
    }

    public void calcCm(){
        this.convValue = this.rawValue/faktor;
    }
    public void calcZoll(){
        this.convValue = this.rawValue*faktor;
    }



}
