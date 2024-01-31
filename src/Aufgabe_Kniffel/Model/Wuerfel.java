package Aufgabe_Kniffel.Model;

import java.util.Random;

public class Wuerfel {

    private final int id;
    private int value;




    private final Random randomGenerator;

    public Wuerfel(int id){
        this.id = id;
        this.randomGenerator = new Random();
        this.value = randomGenerator.nextInt(6) + 1;
    }

    public void wuerfeln(){
        this.value = randomGenerator.nextInt(6) + 1;
    }

    public int getId(){
        return this.id;
    }

    public int getValue(){
        return this.value;
    }



}
