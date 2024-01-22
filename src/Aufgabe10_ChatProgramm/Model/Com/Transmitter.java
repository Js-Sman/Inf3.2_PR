package Aufgabe10_ChatProgramm.Model.Com;

import Aufgabe10_ChatProgramm.Controller.ReceiveAdapter;
import Aufgabe10_ChatProgramm.Model.ChatModel;
import Aufgabe10_ChatProgramm.Model.Grafik.Figure;
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
    protected ObjectInputStream reader;
    protected ObjectOutputStream writer;

    private Figure receivedFigure;
    private SubmissionPublisher<Figure> figurePublisher;

    private ChatModel chatModel;

    public Transmitter() {
        //Einen neuen Thread aufbauen
        executorService = Executors.newSingleThreadExecutor();


        //Neue ReceiveAdapter erzeugen
        this.figurePublisher = new SubmissionPublisher<>();
    }

    public void addFigurenSubscription(Subscriber<Figure> subscriber) {
        figurePublisher.subscribe(subscriber);
    }

    public void initIO() {
        try {
            lg.info("Initialisiere reader und writer");
            InputStream is = socket.getInputStream();
            OutputStream os = socket.getOutputStream();

            /*
             Object reader/writer sind immer gepuffert daher kann man sie
             direkt aus den SocketStreams erzeugen
             */
            writer = new ObjectOutputStream(os);    //Immer der Writer vor dem Reader initialisieren!
            reader = new ObjectInputStream(is);

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
        while (true) {
            //Die Websocket auslesen
            receivedFigure = receive();
            //Erhaltene Figur für den ReceiveAdapter veröffentlichen
            figurePublisher.submit(receivedFigure);


        }

    }

    /**
     * Hier wird eine Textnachricht an eine WebSocket gesendet und gleichzeitig im eigenen View angezeigt.
     * Dazu muss die Nachricht über den writer an die Socket geschrieben werden und vom nachrichtenPublisher veröffentlicht werden.
     */
    public void send(Figure figure) throws IOException {

        //Nachricht an WebSocket übermitteln
        writer.writeObject(figure);
        writer.flush();     //Jeder StreamWriter muss geflushed werden um die Daten, die auf ihn geschrieben wurden tatsächlich weiterzuleiten

    }

    public synchronized Figure receive() {

        Object receivedObject;
        Figure figure = new Figure();
        try {
            receivedObject = reader.readObject();

            if (receivedObject instanceof Figure) {
                lg.info("Figur erhalten");
                figure = (Figure) receivedObject;
            }
        } catch (IOException | ClassNotFoundException ex) {
            lg.info(ex.getMessage());
        }

        return figure;

    }

}
