package Aufgabe10_ChatProgram.Controller;

import Aufgabe10_ChatProgram.View.MainWindow;

import java.util.concurrent.Flow.*;

public class ReceiveAdapter implements Subscriber<String> {

    private MainWindow view;
    private Subscription subscription;

    public ReceiveAdapter(MainWindow view) {
        this.view = view;
    }

    @Override
    public void onSubscribe(Subscription subscription) {
        this.subscription = subscription;
        subscription.request(1);
    }

    @Override
    public void onNext(String item) {
        view.getTaChatView().append(item+"\n");
        subscription.request(1);
    }

    @Override
    public void onError(Throwable throwable) {

    }

    @Override
    public void onComplete() {

    }
}
