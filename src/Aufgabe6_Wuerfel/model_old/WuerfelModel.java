/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package Aufgabe6_Wuerfel.model_old;

import java.util.concurrent.Flow.Subscriber;
import java.util.concurrent.SubmissionPublisher;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Option 3
 * @author ahren
 */
public class WuerfelModel implements Runnable
{
  private int wert;
  private boolean laufend;
  private Thread thd;
  private SubmissionPublisher<Integer> wertPublisher;
  
  public WuerfelModel()
  {
    wert = 1;
    laufend = false;
    thd = null;
    wertPublisher = new SubmissionPublisher<>();
  }

  public void addWertSubscription(Subscriber<Integer> subscriber)
  {
    wertPublisher.subscribe(subscriber);
  }

  public void start()
  {
    laufend = true;
    if (thd == null)
    {
      thd = new Thread(this);
      thd.start();
    }
    
    synchronized (thd) {
        thd.notify();
      }
    
    
  }

  public void stop()
  {
    laufend = false;
  }

  @Override
  public void run()
  {
    while (true){
    if (!laufend){
       try
      {
      synchronized (thd) {
        thd.wait();
      }
      }
      catch (InterruptedException ex)
      {
        Logger.getLogger(WuerfelModel.class.getName()).log(Level.SEVERE, null, ex);
      }
    }
    else{
      try
      {
        Thread.sleep(50);
      }
      catch (Exception ex)
      {
        System.err.println(ex);
      }
      if(wert != 6)
        wert = wert + 1;
      else
        wert = 1;
      
      wertPublisher.submit(wert);
    }
   }
  
  }
}


