package Aufgabe12_CounterBean.Model;

import Aufgabe12_CounterBean.CounterBean;

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
public class CounterModel implements Runnable {

    private int counterWert;
    private boolean laufend;    //Eine Variable um den Thread ein- oder auszuschalten
    private Thread thd;     //Jeder Thread kann als KlassenAttribut betrachtet werden → Das Model hat genau einen Thread
    private SubmissionPublisher<Integer> counterWertPublisher;  //Für das Subscriber DP muss das Model einen Publisher haben

    public CounterModel(CounterBean counterBean) {
        counterWert = counterBean.getCounterValue();
        laufend = false;

        thd = null;     //Es ist besser einen Thread erst dann anzulegen, wenn er gebraucht wird.
        //thd = new Thread(this)
        //thd.wait()    //Alternative

        counterWertPublisher = new SubmissionPublisher<>();
    }

    /**
     * Wenn eine Klasse einen Publisher implementiert, muss sie auch eine Möglichkeit bieten sich einzuschreiben.
     * Der Subscriber und Publisher Typ müssen dabei identisch sein.
     *
     * @param subscriber: Ist die Klasse, die über den Publisher benachrichtigt werden will.
     */
    public void addCounterWertSubscriber(Subscriber<Integer> subscriber) {
        counterWertPublisher.subscribe(subscriber); //Beim Publisher einschreiben
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
                Thread.sleep(1000);   //Die Klasse Thread hat eine eigene sleep Funktion die innerhalb eines Thread verwendet werden sollte
            } catch (InterruptedException e) {
                //Beim Arbeiten mit Threads sollte immer ein Laufzeitfehler gefangen werden → selber Grund wie beim Anhalten
                throw new RuntimeException(e);
            }

            //Die eigentliche Funktion → Runter zählen
            counterWert -= 1;

            //Den neuen Wert veröffentlichen
            counterWertPublisher.submit(counterWert);   //Damit liegt der neue Wert bereit, um von den Subscribern abgeholt zu werden.


            if(counterWert <= 0){
                stop();
            }
         


            /*
            Note:
            Die Werte landen hier auf einem Stack und die Subscriber holen sich die Werte von diesem Stack.
            Deswegen muss der Subscriber auch immer sofort den nächsten Wert anfragen um immer den aktuellsten Wert zu holen.
             */
        }


    }

    public void setWert(int newCounterValue) {
        this.counterWert = newCounterValue;
    }
}