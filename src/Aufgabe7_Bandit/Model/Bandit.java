package Aufgabe7_Bandit.Model;

import Aufgabe7_Bandit.Utils.OhmLogger;

import java.util.ArrayList;
import java.util.concurrent.Flow.*;
import java.util.concurrent.SubmissionPublisher;
import java.util.logging.Logger;

/**
 * Der Bandit ist die Hauptklasse. Hier wird für jede Anzeige in der View ein WuerfelModel erzeugt.
 * Der Bandit kommuniziert in beide Richtungen mit den Models und der View.
 * <p>
 * Der Adapter muss sich jetzt also beim Bandit einschreiben. Der Bandit schreibt sich bei den Models ein und veröffentlicht
 * an für den adapter.
 */
public class Bandit implements Subscriber<Wuerfel> {

    Logger logger = OhmLogger.getLogger();
    //Referenz auf jedes Model
    private WuerfelModel wuerfelModel1;
    private WuerfelModel wuerfelModel2;
    private WuerfelModel wuerfelModel3;

    //Der Bandit muss sich bei jedem Model einschreiben → eine ArrayListe an subscriptions
    private ArrayList<Subscription> subscriptions;
    private SubmissionPublisher<Wuerfel> wuerfelWertPublisher;  //Das ist der Publisher der vom Adapter subscribed wird

    public Bandit() {
        subscriptions = new ArrayList<>();
        wuerfelWertPublisher = new SubmissionPublisher<>();

        //Der Bandit legt die Würfel Modelle an und legt die Id der Würfel fest
        wuerfelModel1 = new WuerfelModel(1);
        wuerfelModel2 = new WuerfelModel(2);
        wuerfelModel3 = new WuerfelModel(3);

        //Direkt bei jedem Model einschreiben
        wuerfelModel1.addWuerfelSubscriber(this);
        wuerfelModel2.addWuerfelSubscriber(this);
        wuerfelModel3.addWuerfelSubscriber(this);
    }

    public void addWuerfelWertSubscriber(Subscriber<Wuerfel> subscriber) {
        wuerfelWertPublisher.subscribe(subscriber);
    }

    @Override
    public void onSubscribe(Subscription subscription) {
        //Jede neue subscription in die ArrayList eintragen
        subscriptions.add(subscription);

        //Von jeder Subscription direkt den ersten Wert anfragen
        subscription.request(1);

    }

    /**
     * Hier kommen die Würfel aus allen 3 Modellen an.
     * Um den nächsten Wert abzuholen, muss in der subscription Liste die richtige subscription angefragt werden
     *
     * @param item the item
     */
    @Override
    public void onNext(Wuerfel item) {
        int subscriptionId = item.getId() - 1;  //Die Model Id entspricht der Würfel Id des Models und das Model ist die Subscription → 0 Index ArrayListe deswegen -1

        wuerfelWertPublisher.submit(item);  //Hier wird der Würfel einfach zum Adapter weiter geleitet, weil dieser die zuordnung an die Labels übernimmt

        subscriptions.get(subscriptionId).request(1);   //Von dem Model wo der Würfel geholt wurde den nächsten anfragen
    }

    @Override
    public void onError(Throwable throwable) {

    }

    @Override
    public void onComplete() {

    }

    public void start() {
        //Jedes Model starten
        wuerfelModel1.start();
        wuerfelModel2.start();
        wuerfelModel3.start();
    }

    public void stop() {
        //Jedes Model stoppen
        wuerfelModel1.stop();
        wuerfelModel2.stop();
        wuerfelModel3.stop();


        int w1_value = wuerfelModel1.getWuerfelWert();
        int w2_value = wuerfelModel2.getWuerfelWert();
        int w3_value = wuerfelModel3.getWuerfelWert();


        if (w1_value == w2_value && w2_value == w3_value) {
            logger.warning("This user has to much luck, he/she should go to a casino");
        } else
            logger.warning("unlucky -> " + w1_value + w2_value + w3_value);

    }
}
