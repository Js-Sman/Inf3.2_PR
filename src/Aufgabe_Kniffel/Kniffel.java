package Aufgabe_Kniffel;

import Aufgabe_Kniffel.Model.Wuerfel;
import Aufgabe_Kniffel.Model.WuerfelModel;
import Aufgabe_Kniffel.Utils.KniffelLogger;
import Aufgabe_Kniffel.View.MainWindow;

import javax.swing.border.LineBorder;
import java.awt.*;
import java.util.ArrayList;
import java.util.concurrent.Flow.*;
import java.util.logging.Logger;

public class Kniffel implements Subscriber<Wuerfel> {

    Logger logger = KniffelLogger.getLogger();


    private ArrayList<Subscription> subscriptions;

    private WuerfelModel[] wuerfel;

    private MainWindow view;
    private int score;

    private boolean[] taken;


    public Kniffel(MainWindow view){
        logger.info("Init Kniffel");
        this.view = view;

        score = 0;
        subscriptions = new ArrayList<>();

        taken = new boolean[6];
        wuerfel = new WuerfelModel[6];
        for (int i = 0; i < 6; i++) {
            wuerfel[i] = new WuerfelModel(i);
            wuerfel[i].addWuerfelSubscriber(this);
        }
    }

    public void start() {
        int i = 0;
        for (WuerfelModel model : wuerfel) {
            if (!taken[i++]) {
                model.start();
            }
        }
    }

    public void stop() {
        int i = 0;
        for (WuerfelModel model : wuerfel) {
            if (!taken[i++]) {
                model.stop();
            }
        }
    }

    public void take(int lblNumber){
        taken[lblNumber] = !taken[lblNumber];

        if (taken[lblNumber]) {
            view.getLblArray()[lblNumber].setBorder(new LineBorder(Color.GREEN, 3));
        }
        else {
            view.getLblArray()[lblNumber].setBorder(null);
        }
    }

    public void updateScore(){
        score = 0;
        for ( int i = 0; i < 6; i++) {
            if (taken[i]) {
                score += wuerfel[i].getValue();
            }
        }
        view.getLblScore().setText("Score: " + score);
    }


    @Override
    public void onSubscribe(Subscription subscription) {
        this.subscriptions.add(subscription);
        subscription.request(1);
    }

    @Override
    public void onNext(Wuerfel item) {
        int id = item.getId();
        int value = item.getValue();

        view.getLblArray()[id].setText(String.valueOf(value));

        subscriptions.get(id).request(1);
    }

    @Override
    public void onError(Throwable throwable) {

    }

    @Override
    public void onComplete() {

    }
}
