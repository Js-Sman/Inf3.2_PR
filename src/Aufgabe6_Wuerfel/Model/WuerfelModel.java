package Aufgabe6_Wuerfel.Model;

import java.util.concurrent.Flow.*;
import java.util.concurrent.SubmissionPublisher;

/**
 * Das Model soll in einem eigenen Thread im Hintergrund laufen.
 * Dadurch sind die Buttons in der UI bedienbar während der Würfel aktiv würfelt.
 *
 * Um eine Klasse Thread fähig zu machen, muss sie die Klasse Runnable implementieren.
 *
 */
public class WuerfelModel implements Runnable {

    private  int wuerfelWert;
    private boolean laufend;    //Eine Variable um den Thread ein- oder auszuschalten
    private Thread thd;     //Jeder Thread kann als KlassenAttribut betrachtet werden → Das Model hat genau einen Thread
    private SubmissionPublisher<Integer> wuerfelWertPublisher;  //Für das Subscriber DP muss das Model einen Publisher haben

    public WuerfelModel(){
        wuerfelWert = (int)(Math.random() * 6) + 1;
        laufend = false;

        thd = null;     //Es ist besser einen Thread erst dann anzulegen, wenn er gebraucht wird.
        //thd = new Thread(this)
        //thd.wait()    //Alternative

        wuerfelWertPublisher = new SubmissionPublisher<>();
    }

    /**
     * Wenn eine Klasse einen Publisher implementiert, muss sie auch eine Möglichkeit bieten sich einzuschreiben.
     * Der Subscriber und Publisher Typ müssen dabei identisch sein.
     * @param subscriber: Ist die Klasse, die über den Publisher benachrichtigt werden will.
     */
    public void addWuerfelWertSubscriber(Subscriber<Integer> subscriber){
        wuerfelWertPublisher.subscribe(subscriber); //Beim Publisher einschreiben
    }


    /**
     * Die einzige Methode der Klasse Runnable.
     * Diese Methode führt die parallelen Prozesse aus, wenn ein Thread gestartet wird.
     */
    @Override
    public void run() {


    }
}
