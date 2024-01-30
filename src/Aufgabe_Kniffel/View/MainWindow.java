package Aufgabe_Kniffel.View;

import javax.swing.*;

import java.awt.*;

public class MainWindow extends JFrame{

   private JButton btnStart;
   private JButton btnStop;
   private JButton btnTake;

   private JLabel[] lblArray;
    private JLabel lblScore;

    public MainWindow(String name){
        super(name);    //Setzt den Fensternamen
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);    //Beendet das Fenster mit Exit

        InitComponents();
        setSize(560,450);
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
        Dimension buttonSize = new Dimension(120,30);
        btnStart = new JButton("Start");
        btnStop = new JButton("Stop");
        btnTake = new JButton("Take");

        btnStart.setPreferredSize(buttonSize);
        btnStop.setPreferredSize(buttonSize);
        btnTake.setPreferredSize(buttonSize);

        buttonContainer.add(btnStart);
        buttonContainer.add(btnStop);
        buttonContainer.add(btnTake);

        lblScore = new JLabel("Score:       ");
        lblScore.setFont(new Font("Arial", Font.BOLD, 16));
        JSeparator spacer = new JSeparator(JSeparator.HORIZONTAL);

        southContainer.add(buttonContainer, BorderLayout.CENTER);
        southContainer.add(spacer, BorderLayout.PAGE_END);
        southContainer.add(lblScore, BorderLayout.LINE_END);

        //CenterContainer
        Container centerContainer = new Container();
        centerContainer.setLayout(new GridLayout(2,3));
        lblArray = new JLabel[6];
        Font labelFont = new Font("Arial", Font.PLAIN, 28);
        for (int i = 0; i < 6; i++) {
            lblArray[i] = new JLabel("?", SwingConstants.CENTER);
        }

        for (JLabel label : lblArray) {
            label.setFont(labelFont);
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

    public JLabel[] getLblArray() {
        return lblArray;
    }

    public JLabel getLblScore() {
        return lblScore;
    }
}
