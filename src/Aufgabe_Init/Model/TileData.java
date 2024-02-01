package Aufgabe_Init.Model;

import Aufgabe_Init.Config.Config;

import java.awt.*;
import java.io.IOException;
import java.util.Random;


public class TileData {

    private Color color;
    private int id;

    private Random random;

    public TileData(int id) {
        this.id = id;
        this.random = new Random();
        setRandomColor();
    }

    public void setRandomColor() {
        try {
            this.color = new Color(
                    random.nextInt(
                            Config.getValue("LOWER_BOUND_COLOR_VALUE")
                            , Config.getValue("UPPER_BOUND_COLOR_VALUE")),
                    Config.getValue("STATIC_COLOR_VALUE"),
                    Config.getValue("STATIC_COLOR_VALUE"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }

    public Color getColor() {
        return color;
    }


    public int getId() {
        return id;
    }

    public void resetColor() {
        color = Color.BLACK;


    }
}
