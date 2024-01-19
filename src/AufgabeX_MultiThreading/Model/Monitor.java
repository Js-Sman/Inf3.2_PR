package AufgabeX_MultiThreading.Model;

import java.util.Scanner;

/**
 * Jedes Objekt hat einen intrinsischen LOCK.
 * Innerhalb einer Klasse kann mit "this" auf den LOCK der Klasse Referenziert werden.
 * Man kann aber auch ein Objekt anlegen und dieses Objekt als LOCK verwenden.
 */
public class Monitor{

    /*
    Wenn nur ein einziges LOCK Objekt verwendet wird, kann auch this als LOCK verwendet werden → ist effektiv equivalent.

    Sobald jedoch verschiedene Bereiche parallel ablaufen sollen, braucht es mehrere LOCKs.
    Ein Synchronisierter Bereich kann immer nur von einem Thread gleichzeitig betreten werden.

    Wenn sich der Block auf LOCK1 synchronisiert dann muss LOCK1 frei sein, um den Bereich zu betreten.
     */
    Object LOCK = new Object();

    public void operationOne() throws InterruptedException {
        synchronized (LOCK){
            System.out.println(Thread.currentThread().getName() + " is working on operation One and takes a while....");
            Thread.sleep(2000);
            /*
            Der erste Thread der diese Funktion ausführt, brauch 5s.
            In diesen 5s kann kein anderer Thread diesen Bereich betreten.
             */

            /*
            Damit wird der LOCK für diesen Bereich wieder freigegeben und der Thread wartet.
            Ein anderer Thread kann das freie LOCK objekt jetzt verwenden, um in diesen Bereich zu operieren.
             */
            System.out.println(Thread.currentThread().getName() + " is waiting for Operation Two....\n");
            LOCK.wait();

            /*
            Wenn auf diesem LOCk notify aufgerufen wird, dann wacht der Thread wieder auf und würde hier weiter machen.
            Der Thread muss aber immer noch auf den LOCK warten, weil innerhalb eines Synchronen Block ausschließlich mit
            aktivem LOCK operiert werden darf.
             */
            System.out.println(Thread.currentThread().getName() + " is resuming...");
            System.out.println(Thread.currentThread().getName() +" is done -> leaving synchronized Block\n");
        }

    }

    public void operationTwo() throws InterruptedException {
        Thread.sleep(5000); //Nur um Sicherzustellen, dass diese Methode nach operationOne ausgeführt wird
        Scanner scanner = new Scanner(System.in);   //Eine Klasse die den System-Input stream scannt

        //Benutzt dasselbe LOCK Objekt → erst, wenn der Synchrone Bereich der Operation Eins verlassen wird oder der Thread in den Wartezustand geht, kann dieser Block betreten werden
        synchronized (LOCK){
            System.out.println("Operation Two: Waiting for Return");
            scanner.nextLine(); //Wartet auf Return

            System.out.println("Operation Two notify all waiting Threads");

            LOCK.notifyAll();   //SO werden alle Threads die auf diesen LOCK warten benachrichtigt aufzuwachen

            LOCK.notify();  //So wird nur der erste wartende Thread benachrichtigt. (Ein notify darf auch ins Leere gehen)


            /*
            Nach einem notify() sollte der Synchrone Block schnellst Möglich verlassen werden!

            Das LOCK Objekt wird mit notify nicht freigegeben!

            Solange der Thread also noch in diesem Synchronen Block operiert warten die anderen Threads noch auf den LOCK
             */
            System.out.print("Operation Two still operating for ");
            for (int i = 5; i>0; i--){
                System.out.print(i+"s ... ");
                Thread.sleep(1000);

            }
            System.out.println("\nOperation Two done -> leaving synchronized Block\n");
        }

    }
}
