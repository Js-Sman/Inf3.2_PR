package AufgabeX_MultiThreading.Model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Ein Arbeiter hat zwei Listen die er mit 1000 zufälligen Zahlen befüllen soll.
 * Die Klasse soll das parallele Verhalten von Threads veranschaulichen.
 * In den beiden Methoden zur bearbeitung der Listen ist ein Switch Case eingebaut. Dieser soll nur dazu da sein um das
 * Verhalten leicht umzuschalten → jeder Case für die gleiche Operation durch nur mit verschiedener Synchronisation
 *
 */
public class Worker {
    /*
    Jede Klasse hat einen intrinsischen LOCK auf den sich ein Bereich mit synchronize(this) synchronisieren kann.
    Jeder Bereich der auf diesen LOCK synchronisiert ist, wird abgesperrt, wenn ein Thread einen dieser Bereiche betritt.

    Das ist sinnvoll, wenn alle diese Bereiche auf dieselbe Resource zugreifen → read/write Konflikte verhindern.

    Wenn verschiedene Bereiche jedoch verschiedene Resource verwenden, sollten diese Bereiche unabhängig voneinander zugänglich sein.
    In dem Fall braucht man mehrere LOCK Objekte → für jeden kritischen Bereich einen separate LOCK
     */

    Object LOCK_LIST1 = new Object();
    Object LOCK_LIST2 = new Object();
    private int OperatingOption = 1;
    private Random random = new Random();
    private List<Integer> list1 = new ArrayList<>();
    private List<Integer> list2 = new ArrayList<>();

    public int getSizeOfList1() {
        return list1.size();
    }

    public int getSizeOfList2() {
        return list2.size();
    }

    public void setOperatingOption(int operatingOption) {
        OperatingOption = operatingOption;
    }

    //In dieser Methode werden die verschiedenen Optionen erklärt → die andere funktioniert identisch
    public void operateOnList1() {

        switch (this.OperatingOption) {
            case 1:
                /*
                Hier wird ohne Synchronisation auf der Liste operiert.
                Hier kann es passieren, dass ein anderer Thread die Liste zur gleichen Zeit bearbeitet und damit die Werte
                von diesem Thread überschreibt → Die Liste wird zwar 1000-mal befüllt aber manchmal werden Werte überschrieben statt angehängt
                 */

                //1ms warten, um einen längeren Prozess zu simulieren
                try {
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

                list1.add(random.nextInt(100)); //Einen zufälligen Integer zwischen 0 und 99 der 1. Liste hinzufügen

                break;

            case 2:
                /*
                Hier wird mit Synchronisation auf den intrinsischen LOCK der Klasse auf der Liste operiert.
                Es kann immer nur ein Thread gleichzeitig diesen Bereich betreten und die Liste verändern. Dadurch ist sichergestellt,
                dass keine read/write Konflikte auftreten können.
                Jedoch ist die andere Liste auch mit dem intrinsischen LOCk der Klasse verriegelt und ein anderer Thread kann
                auch die andere Liste nicht bearbeiten so lange dieser Thread noch in diesem Bereich ist.
                Dadurch wechseln sich die Threads effektiv ab und der prozess läuft serial ab.
                 */

                synchronized (this) {
                    //1ms warten, um einen längeren Prozess zu simulieren
                    try {
                        Thread.sleep(1);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }

                    list1.add(random.nextInt(100)); //Einen zufälligen Integer zwischen 0 und 99 der 1. Liste hinzufügen
                }
                break;

            case 3:
                /*
                Hier wird mit Synchronisation auf einen separate LOCK auf der Liste operiert.
                Es kann immer nur ein Thread gleichzeitig diesen Bereich betreten und die Liste verändern.

                Die andere Liste ist jetzt aber mit einem anderen LOCK verriegelt und ein anderer Thread kann problemlos
                die andere Liste bearbeiten.

                Dadurch ist sichergestellt, dass für jede Liste keine read/write Konflikte auftreten können
                und dass auf beiden Listen unabhängig voneinander parallel operiert wird.
                 */

                synchronized (LOCK_LIST1) {
                    //1ms warten, um einen längeren Prozess zu simulieren
                    try {
                        Thread.sleep(1);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }

                    list1.add(random.nextInt(100)); //Einen zufälligen Integer zwischen 0 und 99 der 1. Liste hinzufügen
                }
                break;

        }

    }

    public void operateOnList2() {
        switch (this.OperatingOption) {
            case 1:
                //Hier wird keine synchronisation verwendet
                try {
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

                list2.add(random.nextInt(100));

                break;
            case 2:
                //Hier wird der intrinsische LOCK der Klasse verwendet
                synchronized (this) {

                    try {
                        Thread.sleep(1);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }

                    list2.add(random.nextInt(100));
                }
                break;
            case 3:
                //Hier wird ein separates LOCK Objekt verwendet
                synchronized (LOCK_LIST2) {

                    try {
                        Thread.sleep(1);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }

                    list2.add(random.nextInt(100));
                }
                break;

        }
    }

    public void process() {
        //Beide Methoden werden 1000-mal aufgerufen → jede Methode dauert ca. 1ms
        for (int i = 0; i < 1000; i++) {
            operateOnList1();
            operateOnList2();
        }
    }

}
