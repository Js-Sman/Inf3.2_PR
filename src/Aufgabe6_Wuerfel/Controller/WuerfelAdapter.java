package Aufgabe6_Wuerfel.Controller;

import Aufgabe6_Wuerfel.Model.WuerfelModel;
import Aufgabe6_Wuerfel.View.WuerfelView;

import java.util.concurrent.Flow.*;

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
public class WuerfelAdapter implements Subscriber<Integer> {

    private WuerfelModel model;
    private WuerfelView view;

    private Subscription subscription;

    /**
     * Der Adapter braucht nur eine Referenz zu Model und View um die daten auszutauschen
     */
    public WuerfelAdapter(WuerfelView view, WuerfelModel model) {
        this.view = view;
        this.model = model;
    }


    /**
     * Diese Methode wird einmalig ausgeführt, wenn sich diese Klasse irgendwo einschreibt.
     * Wenn der Adapter sich also beim Model einschreibt über die Methode addWuerfelWertSubscriber,
     * wird diese Funktion ausgeführt.
     * Das ist ein automatischer Prozess von der Klasse Subscriber. Die Subscription ist automatisch die Klasse bei der
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

    @Override
    public void onSubscribe(Subscription subscription) {

    }

    @Override
    public void onNext(Integer item) {

    }

    @Override
    public void onError(Throwable throwable) {

    }

    @Override
    public void onComplete() {

    }
}
