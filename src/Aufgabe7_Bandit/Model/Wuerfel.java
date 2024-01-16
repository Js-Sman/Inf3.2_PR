package Aufgabe7_Bandit.Model;

/**
 * Da der Bandit mit mehreren Würfeln arbeitet, ist es leichter eine Klasse für einen Würfel anzulegen.
 * Diese Klasse hält eine eindeutige ID und den Wert.
 *
 * Die Subscriber und Publisher müssen dann einen Würfel veröffentlichen bzw abholen und keinen einfachen Wert mehr.
 */
public class Wuerfel {

    private final int id;
    private int wert;

    public Wuerfel(int id, int wert) {
        this.id = id;
        this.wert = wert;
    }

    public int getId(){
        return id;
    }

    public int getWert() {
        return wert;
    }

    public void setWert(int wert) {
        this.wert = wert;
    }


}
