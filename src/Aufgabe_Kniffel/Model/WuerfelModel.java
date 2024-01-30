package Aufgabe_Kniffel.Model;


import Aufgabe_Kniffel.Utils.KniffelLogger;

import java.util.concurrent.Flow.*;
import java.util.concurrent.SubmissionPublisher;
import java.util.logging.Logger;

public class WuerfelModel implements Runnable{

    Logger logger = KniffelLogger.getLogger();


    private final Wuerfel wuerfel;

    private boolean running;

    private Thread thd;

    private SubmissionPublisher<Wuerfel> wuerfelPublisher;

    public WuerfelModel(int id){
        logger.config("Neues Model anlegen -> id: " + id);
        this.wuerfel = new Wuerfel(id);
        wuerfelPublisher = new SubmissionPublisher<>();
        running = false;
    }

    public int getValue(){
        return wuerfel.getValue();
    }

    public void addWuerfelSubscriber(Subscriber<Wuerfel> subscriber){
        this.wuerfelPublisher.subscribe(subscriber);
    }

    public void start(){
        logger.config(""+wuerfel.getId());

        if ( thd == null) {
            thd = new Thread(this);
            running = true;
            thd.start();
        }

        synchronized (this) {
            running = true;
            notify();
        }
    }

    public synchronized void stop(){
        logger.config(""+wuerfel.getId());
        running = false;
    }

    @Override
    public void run() {

        while(true) {

            if (!running) {
                synchronized (this) {
                    try {
                        wait();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
            else {
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

                wuerfel.wuerfeln();
                wuerfelPublisher.submit(wuerfel);
            }
        }

    }
}
