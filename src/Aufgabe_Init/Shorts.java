package Aufgabe_Init;

import java.text.NumberFormat;
import java.util.Locale;

public class Shorts {

    //String Format
    String number = "42";
    String formatString = String.format(Locale.US, "%.2f", Double.parseDouble(number));

    //System separator verwenden
    String path = "Ordner" + System.lineSeparator() + "File";

    //String Builder
    StringBuilder tempString = new StringBuilder();
    public void buildString(){
        tempString.append("You: \t");
        tempString.append(System.lineSeparator());
    }





    Number nbr = 2;
    double dbl = nbr.doubleValue();

}

public class Nf {

    //Beispiel Instanziierung
    public void initNUmberFormatter(){
        NumberFormat numberFormat = NumberFormat.getInstance();
        numberFormat.setMaxiumFractionDigits(2);
    }

    //Verwendung
    double zahl = numberFormat.parse("3,15").doubleValue();
}
