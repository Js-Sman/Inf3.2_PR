package Aufgabe6_Wuerfel.Model;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Flow.Subscriber;
import java.util.concurrent.SubmissionPublisher;

/**
 * Das Model soll in einem eigenen Thread im Hintergrund laufen.
 * Dadurch sind die Buttons in der UI bedienbar während der Würfel aktiv würfelt.
 * <p>
 * Um eine Klasse Thread fähig zu machen, muss sie die Klasse Runnable implementieren.
 * <p>
 * In dieser Klasse wird ein eService verwendet und mit wait bzw notify start und stop realisiert.
 */
public class WuerfelModel_eService implements Runnable {

    private int wuerfelWert;
    private boolean laufend;    //Eine Variable um den Thread ein- oder auszuschalten
    private Thread thd;     //Jeder Thread kann als KlassenAttribut betrachtet werden → Das Model hat genau einen Thread

    private ExecutorService eService;   //Ein eService ist wie ein Thread nur mit mehr inbuilt Möglichkeiten
    private SubmissionPublisher<Integer> wuerfelWertPublisher;  //Für das Subscriber DP muss das Model einen Publisher haben

    public WuerfelModel_eService() {
        wuerfelWert = (int) (Math.random() * 6) + 1;
        laufend = false;

        thd = null;     //Es ist besser einen Thread erst dann anzulegen, wenn er gebraucht wird.
        //thd = new Thread(this)
        //thd.wait()    //Alternative

        eService = null;

        wuerfelWertPublisher = new SubmissionPublisher<>();
    }

    /**
     * Wenn eine Klasse einen Publisher implementiert, muss sie auch eine Möglichkeit bieten sich einzuschreiben.
     * Der Subscriber und Publisher Typ müssen dabei identisch sein.
     *
     * @param subscriber: Ist die Klasse, die über den Publisher benachrichtigt werden will.
     */
    public void addWuerfelWertSubscriber(Subscriber<Integer> subscriber) {
        wuerfelWertPublisher.subscribe(subscriber); //Beim Publisher einschreiben
    }

    /**
     * Mit dieser Function soll der Thread gestartet werden.
     * Wenn noch kein Thread zugewiesen ist muss ein neuer Thread gestartet werden.
     * Falls der Thread nur im Wartemodus ist, muss er ein notify Signal aussenden um zu Signalisieren, dass er weiter läuft
     */
    public void start() {
        laufend = true;


        if (eService==null){
            //Um einen Single Thread zu realisieren, muss von der Klasse Executors ein neuer Single-Thread geholt werden
            eService = Executors.newSingleThreadExecutor();

            //Mit execute wird eine Funktion in einem Thread ausgeführt.
            //Ohne weitere angabe bezieht sich this immer auf die run Methode.
            //Es können aber alle Klassenmehtoden angegeben werden und der eService wird diese Methode als Thread ausführen
            eService.execute(this);
        }

        synchronized (eService){
            eService.notify();
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
                synchronized (eService) {
                    //Hier muss jetzt der eService Synchronisiert werden
                    try {
                        eService.wait(); //Damit wird die run Methode angehalten
                    } catch (InterruptedException e) {
                        //Das Anhalten eines Threads kann immer einen Laufzeitfehler verursachen, wenn der Thread fest hängt
                        throw new RuntimeException(e);
                    }
                }
            }

            //Für den Anwendungszweck soll der Thread in jedem Schritt kurz warten → einfach, um die Anzeige zu sehen
            try {
                Thread.sleep(1000);   //Die Klasse Thread kann auch für einen eService verwendet werden, weil ein eService auch nur einen Thread verwaltet
            } catch (InterruptedException e) {
                //Beim Arbeiten mit Threads sollte immer ein Laufzeitfehler gefangen werden → selber Grund wie beim Anhalten
                throw new RuntimeException(e);
            }

            //Die eigentliche Funktion → Würfel
            wuerfelWert = (int) (Math.random() * 6) + 1;

            //Den neuen Wert veröffentlichen
            wuerfelWertPublisher.submit(wuerfelWert);   //Damit liegt der neue Wert bereit, um von den Subscribern abgeholt zu werden.



            /*
            Note:
            Die Werte landen hier auf einem Stack und die Subscriber holen sich die Werte von diesem Stack.
            Deswegen muss der Subscriber auch immer sofort den nächsten Wert anfragen um immer den aktuellsten Wert zu holen.
             */
        }


    }

}
