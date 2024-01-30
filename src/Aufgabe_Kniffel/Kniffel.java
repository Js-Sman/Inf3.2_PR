package Aufgabe_Kniffel;

import Aufgabe_Kniffel.Model.Wuerfel;
import Aufgabe_Kniffel.Model.WuerfelModel;
import Aufgabe_Kniffel.Utils.KniffelLogger;
import Aufgabe_Kniffel.View.MainWindow;

import javax.swing.*;
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
    private boolean[] locked;


    public Kniffel(MainWindow view) {
        logger.info("Init Kniffel");
        this.view = view;

        score = 0;
        subscriptions = new ArrayList<>();

        taken = new boolean[6];
        locked = new boolean[6];
        wuerfel = new WuerfelModel[6];
        for (int i = 0; i < 6; i++) {
            wuerfel[i] = new WuerfelModel(i);
            wuerfel[i].addWuerfelSubscriber(this);
        }
    }

    public void start() {
        int i = 0;
        for (WuerfelModel model : wuerfel) {
            if (!(taken[i] || locked[i])) {
                model.start();
            }
            i++;
        }
    }

    public void stop() {
        int i = 0;
        for (WuerfelModel model : wuerfel) {
            if (!(taken[i] || locked[i])) {
                model.stop();
            }
            i++;
        }
    }

    public void take(int lblNumber) {
        taken[lblNumber] = !taken[lblNumber];

        if (taken[lblNumber]) {
            view.getLblWuerfelArray()[lblNumber].setBorder(new LineBorder(Color.GREEN, 3));
        } else {
            view.getLblWuerfelArray()[lblNumber].setBorder(null);
        }
    }

    public void lock(int lblNumber) {
        locked[lblNumber] = taken[lblNumber];
        view.getLblWuerfelArray()[lblNumber].setBorder(new LineBorder(Color.RED, 3));
    }

    public void updateScore() {
        score = 0;
        boolean[] tempTakenArray = taken;
        for (JLabel label : view.getLblScoreArray()) {
            for (int i = 0; i < 6; i++) {
                if ((taken[i] || locked[i])) {
                    score = wuerfel[i].getValue();
                    label.setText(String.valueOf(score));
                    lock(i);
                    taken[i] = false;
                    break;

                }else {
                    label.setText("?");
                }
            }
        }
        taken = tempTakenArray;


        //view.getLblScore().setText("Score: " + score);
    }

    private JLabel[] resetScoreArray(JLabel[] lblScoreArray) {
        for (JLabel label : lblScoreArray) {
            label.setText("?");
        }
        return lblScoreArray;
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

        view.getLblWuerfelArray()[id].setText(String.valueOf(value));

        subscriptions.get(id).request(1);
    }

    @Override
    public void onError(Throwable throwable) {

    }

    @Override
    public void onComplete() {

    }
}
