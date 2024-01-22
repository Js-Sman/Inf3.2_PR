package Aufgabe10_ChatProgramm.Model;

import Aufgabe10_ChatProgramm.Controller.ReceiveAdapter;
import Aufgabe10_ChatProgramm.Model.Com.Transmitter;
import Aufgabe10_ChatProgramm.Model.Grafik.Figure;
import Aufgabe10_ChatProgramm.Model.Grafik.GrafikModel;

import java.awt.*;
import java.io.IOException;
import java.util.concurrent.Flow.*;
import java.util.concurrent.SubmissionPublisher;

/**
 * Das ChatModel ist das übergeordnete Model in dieser Aufgabe. Es verwaltet das GrafikModel und den Transmitter.
 * Der Receive Adapter kommuniziert nur mit dem ChatModel. Die Verwaltung der Daten übernimmt das GrafikModel und wird vom ChatModel organisiert.
 *
 * Das ChatModel braucht eine Referenz auf einen Transmitter um Daten zu senden. Diese Referenz kommt vom Connect Kommando.
 *
 * Das ChatModel schreibt sich, als Subscriber beim Transmitter ein um neue Daten zu erhalten und veröffentlicht an den Receive Adapter um die Daten im eigenen GrafikFrame zu aktualisieren.
 */
public class ChatModel implements Subscriber<Figure> {

    private SubmissionPublisher<Figure> figurePublisher;    //Der Publisher für den Receive Adapter
    private Subscription subscription;  //Die eigenen Subscriptions → auf den Transmitter

    private ReceiveAdapter receiveAdapter;

    private GrafikModel grafikModel;
    private Transmitter transmitter;

    public ChatModel(GrafikModel model){
        this.grafikModel = model;
        this.transmitter = null;    //Der Transmitter kann erst nach dem Connect Kommando gesetzt werden


    }

    public void setTransmitter(Transmitter transmitter){
        this.transmitter = transmitter;

        //ReceiveAdapter für den Transmitter erzeugen und als Subscriber eintragen
        this.receiveAdapter = new ReceiveAdapter(this);
        transmitter.addFigurenSubscription(this.receiveAdapter);
    }

    @Override
    public void onSubscribe(Subscription subscription) {
        //Wenn sich das ChatModel einschreibt (Beim Transmitter) wird direkt der erste Wert angefragt
        this.subscription = subscription;
        subscription.request(1);
    }

    /**
     * Hier kommt eine Figur an, wenn sie vom Transmitter in der WebSocket empfangen wurde
     */
    @Override
    public void onNext(Figure item) {
        //Die erhaltene Figur im GrafikModel abspeichern
        grafikModel.addFigure(item);

        //Die erhaltene Figur an den Receive Adapter weiterleiten um sie im eigenen GrafikFrame anzuzeigen
        figurePublisher.submit(item);

        //Nächstes Item vom Transmitter anfragen
        subscription.request(1);
    }

    /**
     * Das ChatModel hat Zugriff auf alle daten des GrafikModels und wird angesprochen, um eine Figur zu versenden.
     * Das ChatModel holt sich die Daten aus dem GrafikModel und koordiniert das Senden der Daten im Transmitter
     */
    public void sendFigure() throws IOException {
        //GrafikDaten der letzten gezeichneten Figur abholen
        Figure rawFigure = grafikModel.getFiguren().getLast();   //Die Rohdaten entsprechen einer ArrayList

        //Dem Transmitter die zu sendende Figur übermitteln
        transmitter.send(rawFigure);

    }

    /**
     * Der ReceiveAdapter gibt die Figur, die er vom Transmitter erhält direkt an das ChatModel weiter.
     * Das ChatModel fügt die Figur dem GrafikModel hinzu.
     */
    public void addReceivedFigure(Figure item) {
        grafikModel.addFigure(item);
    }

    @Override
    public void onError(Throwable throwable) {

    }

    @Override
    public void onComplete() {

    }

}
