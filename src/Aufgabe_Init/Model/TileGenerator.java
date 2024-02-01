package Aufgabe_Init.Model;

import jdk.jshell.JShell;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicBoolean;

public class TileGenerator implements Runnable{


    private SubmissionPublisher<TileData> tileDataPublisher;
    private ExecutorService eService;
    private final Object LOCK;
    private TileData tileData;
    private AtomicBoolean isGenerating;

    public TileGenerator(int id) {

        this.tileData = new TileData(id);
        this.tileDataPublisher = new SubmissionPublisher<>();
        this.LOCK = new Object();
        this.isGenerating = new AtomicBoolean(false);
        this.eService = null;
    }

    public void addTileDataSubscriber(Flow.Subscriber<TileData> subscriber){
        tileDataPublisher.subscribe(subscriber);    //TileMapModel
    }

    public void start(){

        isGenerating.set(true);


        synchronized (LOCK) {
            LOCK.notifyAll();
        }

        if (eService == null) {
            this.eService = Executors.newSingleThreadExecutor();
            eService.execute(this);
        }

    }

    public void stop(){
        isGenerating.set(false);
    }
    @Override
    public void run() {

        while (true) {

            if (!isGenerating.get()) {
                synchronized (LOCK) {
                    try {
                        LOCK.wait();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }

                }
            }

            tileData.setRandomColor();
            tileDataPublisher.submit(tileData);

            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

    }

    public boolean getIsGenerating() {
        return isGenerating.get();
    }
}
