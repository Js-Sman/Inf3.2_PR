package Aufgabe_Init.Model;

import java.awt.*;
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
        this.color = new Color(random.nextInt(255), random.nextInt(255), random.nextInt(255));


}

public Color getColor() {
    return color;
}


public int getId() {
    return id;
}

    }
