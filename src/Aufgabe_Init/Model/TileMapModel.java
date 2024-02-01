package Aufgabe_Init.Model;

import Aufgabe_Init.Config.Config;

import java.io.IOException;
import java.util.Random;
import java.util.concurrent.Flow;
import java.util.concurrent.SubmissionPublisher;
import java.util.logging.Logger;

public class TileMapModel implements Flow.Subscriber<TileData> {
    Logger logger = Logger.getLogger("OhmLogger");


    private TileGenerator[] tileGenerators;
    private SubmissionPublisher<TileData> tileDataPublisher;

    public void addTileDataSubscriber(Flow.Subscriber<TileData> subscriber){
        tileDataPublisher.subscribe(subscriber);    //Adapter
    }

    public TileMapModel() throws IOException {
        this.tileDataPublisher = new SubmissionPublisher<>();
        this.tileGenerators = new TileGenerator[Config.getValue("SIZE")];
        Random random = new Random();

        for (int i = 0; i < Config.getValue("SIZE"); i++) {
            tileGenerators[i] = new TileGenerator(i);
//            tileGenerators[i] = new TileGenerator(i, random.nextInt(3,7));
            tileGenerators[i].addTileDataSubscriber(this);
        }
    }

    public void startTileGenerator(int index){
        //logger.info("Start Tile: " + index);
        tileGenerators[index].start();
    }

    public void stopTileGenerator(int index){
        tileGenerators[index].stop();
    }

    public boolean getAtomicBoolean(int index) {
        return tileGenerators[index].getIsGenerating();
    }
    @Override
    public void onSubscribe(Flow.Subscription subscription) {
        subscription.request(Long.MAX_VALUE);
    }

    @Override
    public void onNext(TileData item) {
        tileDataPublisher.submit(item);
    }

    @Override
    public void onError(Throwable throwable) {

    }

    @Override
    public void onComplete() {

    }

}
