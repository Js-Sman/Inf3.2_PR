package Aufgabe7_Bandit.Controller;

import Aufgabe7_Bandit.Model.Bandit;
import Aufgabe7_Bandit.Model.Wuerfel;
import Aufgabe7_Bandit.Utils.OhmLogger;
import Aufgabe7_Bandit.View.WuerfelView;

import java.util.concurrent.Flow.*;
import java.util.logging.Logger;

/**
 * Der Adapter organisiert die Benachrichtigungen vom Model zur View.
 * <p>
 * Das Model bietet einen Publisher an, bei dem sich der Adapter einschreiben kann.
 * Immer wenn der Publisher einen neuen Wert veröffentlicht, erfährt das der Adapter
 * und gibt den Wert weiter an die View.
 * <p>
 * Jede Klasse, die ein Subscriber ist, muss das Interface Subscriber implementieren.
 * Da es sich um einen Wert Publisher handelt, muss auch der Subscriber Typ Integer sein.
 */
public class WuerfelAdapter implements Subscriber<Wuerfel> {

    Logger logger = OhmLogger.getLogger();
    private Bandit bandit;  //Der Adapter muss nur Werte vom Banditen abholen da dieser die Würfel ausliest
    private WuerfelView view;

    private Subscription subscription;

    /**
     * Der Adapter braucht nur eine Referenz zu Model und View um die daten auszutauschen
     */
    public WuerfelAdapter(WuerfelView view, Bandit bandit) {
        this.view = view;
        this.bandit = bandit;
    }


    /**
     * Diese Methode wird einmalig ausgeführt, wenn sich diese Klasse irgendwo einschreibt.
     * Wenn der Adapter sich also beim Model einschreibt über die Methode addWuerfelWertSubscriber,
     * wird diese Funktion ausgeführt.
     * Das ist ein automatischer Prozess von der Klasse Subscriber. Die Subscription ist automatisch die Klasse, bei der
     * sich der Adapter einschreibt → Das Model
     * @param subscription a new subscription
     */
    @Override
    public void onSubscribe(Subscription subscription) {
        this.subscription = subscription;   //Damit wird der Wert Publisher aus dem Model verlinkt
        this.subscription.request(1);   //Damit wird der nächste Wert des Publishers angefragt

        //Die Idee ist es direkt beim Einschreiben den ersten Wert anzufragen. Sobald der Publisher einen Wert hat,
        //sendet er ihn direkt los.
    }


    /**
     * Items die erfolgreich vom Publisher veröffentlicht werden, landen in dieser Methode.
     * Hier muss genau wie beim onSubscribe auch der nächste Wert direkt angefragt werden, da nur dann Werte abgeholt
     * werden, wenn eine Anfrage aussteht.
     *
     * Jeder Würfel hat eine Id, mit der er einem Label in der View zugeordnet werden kann. Diese Zuordnung übernimmt
     * der Adapter in dieser Methode
     */
    @Override
    public void onNext(Wuerfel item) {
        int wuerfelId = item.getId();
        String wuerfelWert = String.valueOf(item.getWert());
        logger.info("Würfel Id: " + wuerfelId + "; Würfel Wert: " + wuerfelWert);

        //Das Label in der View entsprechend der Id des Würfels anpassen
        switch(wuerfelId){
            case 1:
                view.getLblWuerfel1().setText(wuerfelWert);
                break;
            case 2:
                view.getLblWuerfel2().setText(wuerfelWert);
                break;
            case 3:
                view.getLblWuerfel3().setText(wuerfelWert);
                break;
        }

        //Nächsten wert vom Bandit abholen
        this.subscription.request(1);
    }

    @Override
    public void onError(Throwable throwable) {

    }

    @Override
    public void onComplete() {

    }
}
