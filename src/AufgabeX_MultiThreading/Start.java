package AufgabeX_MultiThreading;

import AufgabeX_MultiThreading.Model.Monitor;
import AufgabeX_MultiThreading.Model.Worker;

public class Start {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws InterruptedException {

        //Das Monitorbeispiel soll verdeutlichen wie die Synchronization von Threads abläuft
        execute_Monitor_example();

        //Das Workerbeispiel soll veranschaulichen wie die Synchronization auf verschiedene Objekte abläuft und welche auswirkungen es hat.
        //execute_Worker_example();


    }

    private static void execute_Worker_example() throws InterruptedException {
        Worker worker = new Worker();
        worker.setOperatingOption(3);

        Thread thd1 = new Thread(new Runnable() {
            @Override
            public void run() {
                worker.process();
            }
        });

        Thread thd2 = new Thread(new Runnable() {
            @Override
            public void run() {
                worker.process();
            }
        });

        System.out.println("Worker starting...");

        long start = System.currentTimeMillis();

        thd1.start();
        thd2.start();


        thd1.join();
        thd2.join();


        long end = System.currentTimeMillis();
        long duration = end - start;
        System.out.println("Worker finished after " + duration + "ms");
        System.out.println("List1 size: " + worker.getSizeOfList1());
        System.out.println("List2 size: " + worker.getSizeOfList2());
    }

    private static void execute_Monitor_example() throws InterruptedException {
        Monitor monitor = new Monitor();

      /*
      Die Klasse Monitor soll das Verhalten von Threads veranschaulichen.
      Dazu hat die Klasse zwei Methoden die immer eine gewisse Zeit warten und dann eine Konsolenausgabe erzeugen.

      Es werden hier 3 Threads erzeugt und gestartet. Diese Threads laufen prinzipiell parallel.
      Der Monitor verwendet jedoch ein LOCK objekt um das Verhalten der Threads zu ..."monitoren".

      Thd1 & Thd2 sollen beide die Operation 1 ausführen.
      Wenn der erste Thread den Synchronen Bereich betritt wird das LOCK Objekt für diesen Bereich gesperrt.
      Der zweite Thread muss dann warten.
      Wenn im Synchronen Bereich wait() auf dem LOCK aufgerufen wird, wird der LOCK freigegeben und der Thread wartet an dieser Stelle mit der weiteren ausführung.
      Da der LOCk wieder frei ist, kann der zweite Thread jetzt in den Synchronen Bereich bis dieser auch wait() aufruft.
      Beide Threads warten jetzt an der Stelle im Programm an der LOCK.wait() aufgerufen wurde. Der LOCk selbst ist frei.

      Ein 3. Thread Thd3 fürt parallel zu Thd1 & 2 die Operation Zwei aus. In dieser Methode gibt es auch einen Synchronen Block der mit demselben LOCk Objekt versiegelt ist.
      Thd3 muss also warten bis der LOCK frei ist → bis die anderen beiden Threads im wartezustand sind.

      Sobald der LOCk frei ist, betritt thd3 den Synchronen Block und wartet hier auf eine Benutzereingabe.
      Mit der Benutzereingabe wird auf dem LOCK notifyAll() aufgerufen. Mit diesem aufruf werden thd1 & 2 benachrichtigt den Wartezustand zu verlassen.

      notify() oder notifyAll() gibt den LOCk jedoch nicht wieder frei! → erst wenn der Synchrone Block verlassen wird ist der LOCK wieder frei!

      Einer der beiden mittlerweile wieder aufgewachten Threads nimmt sich das gerade frei gewordene LOCK objekt und macht an der Stelle weiter wo er gewartet hat.
      Der andere Thread muss dann wieder warten bis der erste Thread den LOCK wieder freigibt, indem er den Synchronen Block verlässt.

      Note:
      -Die Auswahl welcher der wartenden Threads den LOCk zuerst bekommt ist zufällig → steuerbar mit eService oder reentrend Lock Objekten.
      -notify() benachrichtigt zufällig nur einen der wartenden Threads.
       */

        Thread thd0 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    monitor.operationOne();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        Thread thd1 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    monitor.operationOne();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        Thread thd2 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    monitor.operationTwo();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        thd0.start();
        thd1.start();
        thd2.start();

        //Wenn ein Thread den aufrufenden Thread beitritt, dann wartet der aufrufende Thread so lange bis der beigetretene Thread fertig ist
        thd0.join();
        thd1.join();
        thd2.join();
    }
}
