/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package Aufgabe6_Wuerfel.model_old;

import aufgabe6_wuerfel.view.WuerfelView;

import java.util.concurrent.Flow.Subscriber;
import java.util.concurrent.Flow.Subscription;


/**
 *
 * @author ahren
 */
public class WuerfelAdapter implements Subscriber<Integer> 
{
    private WuerfelView view;
    private WuerfelModel model;
    private Subscription subscription;

    
    public WuerfelAdapter(WuerfelView view, WuerfelModel model)
    {
        this.view = view;
        this.model = model;
    }

    @Override
    public void onSubscribe(Subscription subscription) {
     this.subscription = subscription;
     this.subscription.request(1);
    }

    @Override
    public void onNext(Integer item) {
        String strWert = String.valueOf(item);
        view.getLblWuerfel().setText(strWert);
        this.subscription.request(1);
    }

    @Override
    public void onError(Throwable throwable) {
        throw new UnsupportedOperationException("Not supported yet."); 
    }

    @Override
    public void onComplete() {
        throw new UnsupportedOperationException("Not supported yet."); 
    }
}
