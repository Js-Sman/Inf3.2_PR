package WiSe23_Klausur.Controller;

import WiSe23_Klausur.Model.TileData;
import WiSe23_Klausur.View.MainWindow;

import java.awt.*;
import java.util.concurrent.Flow;

public class FrameAdapter implements Flow.Subscriber<TileData> {

    private MainWindow view;
    private Flow.Subscription subscription;

    public FrameAdapter(MainWindow view){
        this.view = view;
    }


    @Override
    public void onSubscribe(Flow.Subscription subscription) {
        this.subscription = subscription;
        subscription.request(1);

    }

    @Override
    public void onNext(TileData item) {
        int index = item.getId();
        Color color = item.getColor();

        view.getGridFrame().setBackgroundColor(index,color);
        subscription.request(1);
    }

    @Override
    public void onError(Throwable throwable) {

    }

    @Override
    public void onComplete() {

    }
}
