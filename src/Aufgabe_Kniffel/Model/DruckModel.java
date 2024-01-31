package Aufgabe_Kniffel.Model;

import Aufgabe_Kniffel.Kniffel;

import java.awt.*;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.io.File;
import java.util.prefs.Preferences;

public class DruckModel implements Printable {

    private Preferences preferences;

    private String filePath;



    public DruckModel(String filePath){
        this.preferences = Preferences.userNodeForPackage(getClass());
        this.filePath = filePath;
    }

    public String getPreferences(){
        return preferences.get("lastDirectory", null);
    }

    public void setPreferences(String key, String value){
        this.preferences.put(key,value);
    }


    @Override
    public int print(Graphics graphics, PageFormat pageFormat, int pageIndex) throws PrinterException {
        File logFile = new File(filePath);
        return 0;
    }
}