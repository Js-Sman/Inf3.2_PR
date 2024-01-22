package Aufgabe12_CounterBean.Model;

import java.util.EventListener;


/**
 * Es muss ein Interface für eigene Events geben → Der GUI Builder kann dann einen Listener aus diesem Interface Initialisieren
 */
public interface CounterListener extends EventListener {
    void counterValueChanged(CounterAbgelaufenEvent evt);
}
