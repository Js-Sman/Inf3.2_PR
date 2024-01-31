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


    public Kniffel(MainWindow view) {
        logger.info("Init Kniffel");
        this.view = view;

        score = 0;

        subscriptions = new ArrayList<>();
        wuerfel = new WuerfelModel[6];
        for (int i = 0; i < 6; i++) {
            wuerfel[i] = new WuerfelModel(i);
            wuerfel[i].addWuerfelSubscriber(this);

        }
    }

    public void start() {
        for (WuerfelModel model : wuerfel) {
            if (!model.isLocked()) {
                model.start();
            }
        }
    }

    public void stop() {
        for (WuerfelModel model : wuerfel) {
            if (!model.isLocked()) {
                model.stop();
            }
        }
    }

    public void lock(int lblNumber) {
        if(!wuerfel[lblNumber].isRunning()) {
            wuerfel[lblNumber].toggelLock();
        }

        if (wuerfel[lblNumber].isLocked()) {
            view.getLblWuerfelArray()[lblNumber].setBorder(new LineBorder(Color.GREEN, 3));
        } else {
            view.getLblWuerfelArray()[lblNumber].setBorder(null);
        }
    }

    public void takeLocked() {
        for (WuerfelModel model : wuerfel) {
            if (model.isLocked()) {
                model.stop();
                score = model.getValue();

                view.getLblWuerfelArray()[model.getId()].setBorder(new LineBorder(Color.RED, 3));
                view.getLblScoreArray()[model.getId()].setText(String.valueOf(score));

                model.endThread();
            }
        }
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

    public void restart() {
        subscriptions.clear();
        for (int i = 0; i < 6; i++) {
            view.getLblWuerfelArray()[i].setBorder(null);
            view.getLblWuerfelArray()[i].setText("?");
            view.getLblScoreArray()[i].setText(" ");
            wuerfel[i] = new WuerfelModel(i);
            wuerfel[i].addWuerfelSubscriber(this);
        }
    }
}
