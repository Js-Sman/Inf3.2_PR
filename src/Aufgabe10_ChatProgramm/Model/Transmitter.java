package Aufgabe10_ChatProgramm.Model;

import Aufgabe10_ChatProgramm.Controller.ReceiveAdapter;
import Aufgabe10_ChatProgramm.View.MainWindow;

import java.io.*;
import java.net.Socket;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Flow.*;
import java.util.concurrent.SubmissionPublisher;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Transmitter implements Runnable {

    Logger lg = Logger.getLogger("Netz");

    protected static final int timeout = 30000;
    protected static final int PORT = 35000;
    protected static final String IP = "127.0.0.1";

    private final ExecutorService executorService;


    protected Socket socket;
    protected BufferedReader reader;
    protected PrintWriter writer;

    private String nachricht;
    private SubmissionPublisher<String> nachrichtenPublisher;

    private MainWindow view;

    public Transmitter(MainWindow view) {
        this.view = view;

        //Neue ReceiveAdapter erzeugen und direkt als Subscriber in den nachrichtenPublisher eintragen
        nachrichtenPublisher = new SubmissionPublisher<>();
        addNachrichtenSubscrpition(new ReceiveAdapter(view));

        //Einen neuen Thread aufbauen
        executorService = Executors.newSingleThreadExecutor();
    }

    public void addNachrichtenSubscrpition(Subscriber<String> subscriber){
        nachrichtenPublisher.subscribe(subscriber);
    }

    public void initIO() {
        try {
            lg.info("Initialisiere reader und writer");
            InputStream is = socket.getInputStream();
            OutputStream os = socket.getOutputStream();

            InputStreamReader isr = new InputStreamReader(is, "UTF-8");
            OutputStreamWriter osr = new OutputStreamWriter(os, "UTF-8");

            reader = new BufferedReader(isr);
            writer = new PrintWriter(osr);
            lg.info("Reader / Writer Initialisierung abgeschlossen");

            //Die run Methode starten
            executorService.execute(this);

        } catch (IOException ex) {
            Logger.getLogger(Transmitter.class.getName()).log(Level.SEVERE, null, ex);
        }
    }


    @Override
    public void run() {
        lg.info("Starte Chat");

        //Dieser Thread soll durchgehend laufen und die WebSocket auslesen
        while (true){
            try {
                //Die Websocket auslesen
                nachricht = reader.readLine();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            //Wenn eine Nachricht ausgelesen wurde, wird diese Nachricht veröffentlicht → an den ReceiveAdapter
            if(!nachricht.isEmpty()){
                nachricht = "Erhalten: " + nachricht;
                nachrichtenPublisher.submit(nachricht);
            }

        }

    }

    /**
     * Hier wird eine Textnachricht an eine WebSocket gesendet und gleichzeitig im eigenen View angezeigt.
     * Dazu muss die Nachricht über den writer an die Socket geschrieben werden und vom nachrichtenPublisher veröffentlicht werden.
     */
    public void send(String nachricht){

        //Nachricht an WebSocket übermitteln
        writer.println(nachricht);
        writer.flush();     //Jeder StreamWriter muss geflushed werden um die Daten, die auf ihn geschrieben wurden tatsächlich weiterzuleiten

        //Nachricht in den eigenen View Schreiben
        this.nachricht = "Gesendet: " + nachricht;
        nachrichtenPublisher.submit(this.nachricht);

    }
}
