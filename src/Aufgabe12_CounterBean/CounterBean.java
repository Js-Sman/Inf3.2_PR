package Aufgabe12_CounterBean;

import Aufgabe12_CounterBean.Model.CounterAbgelaufenEvent;
import Aufgabe12_CounterBean.Model.CounterListener;
import Aufgabe12_CounterBean.Model.CounterModel;

import javax.swing.*;
import java.awt.*;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.Flow.*;

/**
 * Diese Klasse stellt das Bean dar. Es setzt das Fassaden DP um und initialisiert das Model, welches die Datenhaltung umsetzt.
 * Die Klasse muss für alle Eigenschaften die von einem GUI-Builder verwendet werden können soll getter bzw setter Methoden zur verfügung stellen.
 * <p>
 * Alle sichtbaren Eigenschaften und Events müssen in der BeanInfo gesetzt werden, um sie im Editor zu sehen.
 * <p>
 * Das CounterBean setzt ein Label um welches herunterzählt.
 * Der Initialwert soll einstellbar sein.
 * Das Bean muss mit einem Button verbunden werden können, um den Counter zu starten.
 * Der aktuelle Wert sollte sichtbar nach außen sein, sodass eine Progressbar angebunden werden kann.
 * Wenn der Wert 0 erreicht hat soll ein Event gefeuert werden.
 */
public class CounterBean extends JComponent implements Subscriber<Integer> {


    private int counterValue;

    private CounterModel model;
    private Subscription subscription;

    //Eine Liste erstellen inder sich alle einschreiben, die auf das CounterEvent hören wollen.
    private CopyOnWriteArrayList<CounterListener> listeners;    //Threadsichere ArrayListe

    /*
    Eine J Komponente braucht immer einen Standard-Konstruktor um von einem GUI Builder eingesetzt werden zu können.
     */
    public CounterBean() {
        counterValue = 10;  //Default Wert

        listeners = new CopyOnWriteArrayList<>();
        this.model = new CounterModel(this);
        model.addCounterWertSubscriber(this);   //Direkt mit dem Model verknüpfen
    }

    /**
     * Für jede Eigenschaft, die im Editor sichtbar sein soll, muss es einen Getter geben.
     */
    public int getCounterValue() {
        return counterValue;
    }

    /**
     * Für jede Eigenschaft, die im Editor beschreibbar sein soll, muss es einen Setter geben.
     */
    public void setCounterValue(int newCounterValue) {
        int alterWert = this.counterValue;
        int neuerWert = newCounterValue;

        //Diese firePropertyChange Methode muss für jeden Wert vorhanden sein der vom Editor aus geändert werden können soll!
        this.firePropertyChange("counterValue", alterWert, neuerWert);

        //Änderungen im Model aktualisieren
        model.setWert(newCounterValue);

        //Änderungen in der Komponente aktualisieren und repaint aufrufen, um die änderungen vom Editor direkt anzuzeigen
        this.counterValue = newCounterValue;
        this.repaint();

    }

    /**
     * Mit dieser Methode bietet das Bean eine Schnittstelle, um sich als Listener auf das CounterEvent einzutragen.
     */
    public void addCounterListener(CounterListener listener) {
        listeners.add(listener);
    }

    /**
     * Mit dieser Methode bietet das Bean eine Schnittstelle, um sich als Listener auszutragen.
     */
    public void removeCounterListener(CounterListener listener) {
        listeners.remove(listener);
    }

    /**
     * Hier werden alle die sich als Listener für das CounterEvent eingetragen haben benachrichtigt
     */
    public void fireCounterEvent(CounterAbgelaufenEvent evt) {
        listeners.forEach(listener -> listener.counterValueChanged(evt));
    }

    public void startCounter() {
        model.start();
    }

    public void stopCounter() {
        model.stop();
    }


    @Override
    public Dimension getPreferredSize() {
        //Wenn ein JComponent in einen anderen eingesetzt wird, wird diese Eigenschaft abgefragt, um die Dimensionierung zu ermöglichen
        return new Dimension(250, 250);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g; // neue Lib benutzen!!!!!!!

        // Setze die Schriftart und Größe
        Font font = new Font(Font.SANS_SERIF, Font.PLAIN, 48);
        setFont(font);

        // Berechne die Position, um den Text zu zentrieren
        FontMetrics fontMetrics = getFontMetrics(font);
        int textWidth = fontMetrics.stringWidth(String.valueOf(counterValue));
        int textHeight = fontMetrics.getHeight();

        // Zentriere den Text
        int x = (getWidth() - textWidth) / 2;
        int y = (getHeight() - textHeight) / 2;

        // Setze die Position des Textes
        setBounds(x, y, textWidth, textHeight);

        // Zeichne das Textlabel
        g2.drawString(String.valueOf(counterValue), getX(), getY() + getFontMetrics(getFont()).getAscent());
    }

    @Override
    public void onSubscribe(Subscription subscription) {
        this.subscription = subscription;
        subscription.request(1);
    }

    @Override
    public void onNext(Integer item) {
        //Mit dem Wert der von CounterModel kommt, wird nur der angezeigte Wert aktualisiert
        this.counterValue = item;

        if (counterValue <= 0) {
            this.fireCounterEvent(new CounterAbgelaufenEvent(this));
        }

        //Mit aktualisiertem Wert neu Zeichnen und nächsten anfragen
        this.repaint();
        this.subscription.request(1);
    }

    @Override
    public void onError(Throwable throwable) {

    }

    @Override
    public void onComplete() {

    }
}
