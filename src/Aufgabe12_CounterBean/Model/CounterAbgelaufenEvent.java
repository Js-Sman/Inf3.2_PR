package Aufgabe12_CounterBean.Model;

import java.util.EventObject;

/**
 * Um ein eigenes Event zu erzeugen, muss dieses nur die EventObject Klasse erweitern.
 * Der Gui-Builder kann dann beim Anlegen des Events einfach ein EventObject initialisieren.
 */
public class CounterAbgelaufenEvent extends EventObject {
    public CounterAbgelaufenEvent(Object source) {
        super(source);
    }
}
