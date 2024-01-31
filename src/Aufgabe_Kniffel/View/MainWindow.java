package Aufgabe_Kniffel.View;

import javax.swing.*;

import java.awt.*;

public class MainWindow extends JFrame {

    private JButton btnStart;
    private JButton btnStop;
    private JButton btnTake;
    private JButton btnRestart;
    private JLabel lblScore;
    private JLabel[] lblWuerfelArray;

    private JLabel[] lblScoreArray;

    public MainWindow(String name) {
        super(name);    //Setzt den Fensternamen
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);    //Beendet das Fenster mit Exit

        lblScoreArray = new JLabel[6];
        lblWuerfelArray = new JLabel[6];
        InitComponents();
        setSize(560, 450);
        setVisible(true);
    }

    private void InitComponents() {

        Container mainView = this.getContentPane();
        mainView.setLayout(new BorderLayout());

        //SouthContainer
        Container southContainer = new Container();
        southContainer.setLayout(new BorderLayout());

        Container buttonContainer = new Container();
        buttonContainer.setLayout(new FlowLayout());
        Dimension buttonSize = new Dimension(120, 30);
        btnStart = new JButton("Start");
        btnStop = new JButton("Stop");
        btnTake = new JButton("Take");
        btnRestart = new JButton("Restart");

        btnStart.setPreferredSize(buttonSize);
        btnStop.setPreferredSize(buttonSize);
        btnTake.setPreferredSize(buttonSize);
        btnRestart.setPreferredSize(buttonSize);

        buttonContainer.add(btnRestart);
        buttonContainer.add(btnStart);
        buttonContainer.add(btnStop);
        buttonContainer.add(btnTake);

        Container uiContainer = new Container();
        uiContainer.setLayout(new FlowLayout());
        Container scoreContainer = new Container();
        scoreContainer.setLayout(new BoxLayout(scoreContainer, BoxLayout.X_AXIS));
        Font scoreLabelFont = new Font("Arial", Font.BOLD, 16);
        for (int i = 0; i < 6; i++) {
            lblScoreArray[i] = new JLabel("", SwingConstants.CENTER);
            lblScoreArray[i].setFont(scoreLabelFont);
            scoreContainer.add(new JLabel(" "));
            scoreContainer.add(lblScoreArray[i]);
        }

        lblScore = new JLabel("Kniffel: ");
        lblScore.setFont(new Font("Arial", Font.BOLD, 16));

        uiContainer.add(lblScore);
        uiContainer.add(scoreContainer);

        southContainer.add(buttonContainer, BorderLayout.CENTER);
        southContainer.add(uiContainer, BorderLayout.PAGE_END);

        //CenterContainer
        Container centerContainer = new Container();
        centerContainer.setLayout(new GridLayout(2, 3));

        Font wuerfelLabelFont = new Font("Arial", Font.PLAIN, 28);
        for (int i = 0; i < 6; i++) {
            lblWuerfelArray[i] = new JLabel("?", SwingConstants.CENTER);
        }

        for (JLabel label : lblWuerfelArray) {
            label.setFont(wuerfelLabelFont);
            centerContainer.add(label);
        }

        mainView.add(southContainer, BorderLayout.SOUTH);
        mainView.add(centerContainer, BorderLayout.CENTER);

    }

    public JButton getBtnStart() {
        return btnStart;
    }

    public JButton getBtnStop() {
        return btnStop;
    }

    public JButton getBtnTake() {
        return btnTake;
    }

    public JLabel[] getLblWuerfelArray() {
        return lblWuerfelArray;
    }

    public JLabel getLblScore() {
        return lblScore;
    }

    public JLabel[] getLblScoreArray() {
        return lblScoreArray;
    }

    public JButton getBtnRestart() {
        return btnRestart;
    }
}
