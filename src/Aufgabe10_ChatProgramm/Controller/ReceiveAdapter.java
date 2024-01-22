package Aufgabe10_ChatProgramm.Controller;

import Aufgabe10_ChatProgramm.Model.ChatModel;
import Aufgabe10_ChatProgramm.Model.Grafik.Figure;

import java.util.concurrent.Flow.Subscriber;
import java.util.concurrent.Flow.Subscription;

public class ReceiveAdapter implements Subscriber<Figure> {

    private final ChatModel chatModel;
    private Subscription subscription;

    public ReceiveAdapter(ChatModel chatModel) {
        this.chatModel = chatModel;
    }

    @Override
    public void onSubscribe(Subscription subscription) {
        this.subscription = subscription;
        subscription.request(1);
    }

    @Override
    public void onNext(Figure item) {
        chatModel.addReceivedFigure(item);
        subscription.request(1);
    }

    @Override
    public void onError(Throwable throwable) {

    }

    @Override
    public void onComplete() {

    }
}
