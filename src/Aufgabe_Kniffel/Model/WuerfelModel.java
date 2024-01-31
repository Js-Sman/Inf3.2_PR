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

    private boolean isLocked;

    private SubmissionPublisher<Wuerfel> wuerfelPublisher;
    private boolean aktive;

    public WuerfelModel(int id){
        logger.config("Neues Model anlegen -> id: " + id);

        wuerfel = new Wuerfel(id);
        isLocked = false;
        aktive = true;
        wuerfelPublisher = new SubmissionPublisher<>();
        running = false;
    }

    public int getValue(){
        return wuerfel.getValue();
    }

    public int getId(){
        return wuerfel.getId();
    }


    public boolean isLocked() {
        return isLocked;
    }

    public boolean isRunning() {
        return running;
    }

    public synchronized void toggelLock() {
        isLocked = !isLocked;
    }

    public void addWuerfelSubscriber(Subscriber<Wuerfel> subscriber){
        this.wuerfelPublisher.subscribe(subscriber);
    }

    public void removeWuerfelSubscriber(){
        this.wuerfelPublisher.close();
    }

    public void start(){
        logger.config(""+wuerfel.getId() +" thd ==" + thd);

        if ( thd == null && aktive) {
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

        while(aktive) {

            if (!running) {
                synchronized (this) {
                    try {
                        wait();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
            else if (!isLocked) {
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

    public void endThread() {
        aktive = false;
        removeWuerfelSubscriber();
    }
}
