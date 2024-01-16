package Aufgabe7_Bandit.Model;

import java.util.concurrent.Flow.*;
import java.util.concurrent.SubmissionPublisher;

/**
 * Das Model soll in einem eigenen Thread im Hintergrund laufen.
 * Dadurch sind die Buttons in der UI bedienbar während der Würfel aktiv würfelt.
 * <p>
 * Um eine Klasse Thread fähig zu machen, muss sie die Klasse Runnable implementieren.
 * <p>
 * In dieser Klasse wird ein Thread verwendet und mit wait bzw notify start und stop realisiert.
 */
public class WuerfelModel implements Runnable {

    private Wuerfel wuerfel;    //Das Model nutzt jetzt die Wuerfel Klasse statt einen int Wert
    private boolean laufend;    //Eine Variable um den Thread ein- oder auszuschalten
    private Thread thd;     //Jeder Thread kann als KlassenAttribut betrachtet werden → Das Model hat genau einen Thread
    private SubmissionPublisher<Wuerfel> wuerfelPublisher;  //Der Publisher veröffentlicht jetzt einen Würfel

    public WuerfelModel(int wuerfelId) {
        //Einen neuen Würfel anlegen → Der Bandit legt ein neues WuerfelModel an und vergibt dabei die id
        int wuerfelWert = (int) (Math.random() * 6) + 1;
        wuerfel = new Wuerfel(wuerfelId, wuerfelWert);

        laufend = false;

        thd = null;     //Es ist besser einen Thread erst dann anzulegen, wenn er gebraucht wird.
        //thd = new Thread(this)
        //thd.wait()    //Alternative

        wuerfelPublisher = new SubmissionPublisher<>();
    }

    /**
     * Wenn eine Klasse einen Publisher implementiert, muss sie auch eine Möglichkeit bieten sich einzuschreiben.
     * Der Subscriber und Publisher Typ müssen dabei identisch sein.
     *
     * @param subscriber: Ist die Klasse, die über den Publisher benachrichtigt werden will.
     */
    public void addWuerfelSubscriber(Subscriber<Wuerfel> subscriber) {
        wuerfelPublisher.subscribe(subscriber); //Beim Publisher einschreiben
    }

    /**
     * Mit dieser Function soll der Thread gestartet werden.
     * Wenn noch kein Thread zugewiesen ist muss ein neuer Thread gestartet werden.
     * Falls der Thread nur im Wartemodus ist, muss er ein notify Signal aussenden um zu Signalisieren, dass er weiter läuft
     */
    public void start() {
        laufend = true;

        //Neuen Thread anlegen und zu diesem Thread zuweisen
        if (thd == null) {
            thd = new Thread(this); //Beim Initialisieren eines Threads muss angegeben werden welchem Thread-Objekt der neue Thread zugewiesen werden soll
            thd.start();    //Diese Methode startet die run Funktion
        }

        //Thread im Wartemodus aufwecken
        synchronized (thd) {
            //Diese Methode muss immer in einem synchronisieren Block abgehandelt werden.
            //Das sorgt dafür, dass der Thread nicht abstürzt, wenn er im Moment des aufrufs was anderes macht.
            thd.notify();
        }

    }

    /**
     * Die stop Funktion muss nur die Variable laufend umschalten.
     * Das sorgt dafür, dass der Thread weiter arbeitet bis er in der run Methode das nächste mal an die Abfrage zu laufend kommt.
     * Erst dann wird der Thread in den wartenden Zustand versetzt
     */
    public void stop() {
        laufend = false;
    }

    /**
     * Die einzige Methode der Klasse Runnable.
     * Diese Methode führt die parallelen Prozesse aus, wenn ein Thread gestartet wird.
     */
    @Override
    public void run() {
        //Der Hintergrundprozess ist hier eine dauerhafte Schleife
        while (true) {

            //In dieser Schleife arbeitet der Thread durchgehend und wird nur in den Wartezustand versetzt,
            //wenn die laufend Variable zwischenzeitlich umgestellt wurde.
            if (!laufend) {
                synchronized (thd) {
                    //Da die Funktion auch wieder direkt auf dem Thread zugreift, muss sie auch auf den Thread synchronisiert sein
                    try {
                        thd.wait(); //Damit wird die run Methode des Threads angehalten
                    } catch (InterruptedException e) {
                        //Das Anhalten eines Threads kann immer einen Laufzeitfehler verursachen, wenn der Thread fest hängt
                        throw new RuntimeException(e);
                    }
                }
            }

            //Für den Anwendungszweck soll der Thread in jedem Schritt kurz warten → einfach, um die Anzeige zu sehen
            try {
                Thread.sleep(50);   //Die Klasse Thread hat eine eigene sleep Funktion die innerhalb eines Thread verwendet werden sollte
            } catch (InterruptedException e) {
                //Beim Arbeiten mit Threads sollte immer ein Laufzeitfehler gefangen werden → selber Grund wie beim Anhalten
                throw new RuntimeException(e);
            }

            if (laufend){
                //Die eigentliche Funktion → Würfel
                wuerfel.setWert((int)(Math.random() * 6) + 1);

                //Den neuen Wert veröffentlichen
                wuerfelPublisher.submit(wuerfel);   //Damit wird der wuerfel veröffentlicht

            }

            /*
            Note:
            Die laufend Variable kann zu jeder Zeit geschaltet werden. Der Thread läuft aber in dem Moment noch und
            befindet sich in der Schleife, in der die Zahlen der Würfel generiert und geschrieben werden.
            Daher muss in dem Fall das Setzen und Publishen der Werte zusätzlich mit der laufen Variable abgefangen werden.
             */
        }


    }

    public int getWuerfelWert() {
        return wuerfel.getWert();
    }
}
