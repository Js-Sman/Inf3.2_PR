package WiSe23_Klausur.Model;

import WiSe23_Klausur.Config.Config;

import java.io.IOException;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.logging.Logger;

public class TileGenerator implements Runnable {

    Logger logger = Logger.getLogger("TileLogger");


    private SubmissionPublisher<TileData> tileDataPublisher;
    private ExecutorService eService;
    private Thread thd;
    private final Object LOCK;
    private TileData tileData;
    private AtomicBoolean isGenerating;
    private boolean timeout;
    private long timeout_sekonds;

    private long sleepTime;

    public TileGenerator(int id) throws IOException {

        this.tileData = new TileData(id);
        this.tileDataPublisher = new SubmissionPublisher<>();
        this.LOCK = new Object();
        this.isGenerating = new AtomicBoolean(false);
        this.eService = null;
        this.thd = null;
        this.timeout_sekonds = (Config.getValue("TILE_GENERATOR_TIMEOUT") * 1000L);
        this.sleepTime = Config.getValue("TILE_BLINK_FREQUENCY");
    }


    public void addTileDataSubscriber(Flow.Subscriber<TileData> subscriber) {
        tileDataPublisher.subscribe(subscriber);    //TileMapModel
    }

    public void start() {

        isGenerating.set(true);

        if (thd == null) {
            logger.info("New Timeout");
            thd = new Thread(() -> {
                try {
                    timeout();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            });
            thd.start();
            thd = null;
        }


        synchronized (LOCK) {
            LOCK.notifyAll();
        }

        if (eService == null) {
            this.eService = Executors.newSingleThreadExecutor();
            eService.execute(this);
        }

    }

    public void stop() {
        isGenerating.set(false);
    }

    public synchronized void timeout() throws InterruptedException {
        logger.info("Start Timeout");
        Thread.sleep(timeout_sekonds);
        stop();
        logger.info("Stop Timeout");
    }

    @Override
    public void run() {

        while (true) {


            if (!isGenerating.get()) {
                tileData.resetColor();
                tileDataPublisher.submit(tileData);
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
                Thread.sleep(sleepTime);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }


        }


    }

    public boolean getIsGenerating() {
        return isGenerating.get();
    }
}
