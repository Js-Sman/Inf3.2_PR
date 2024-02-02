package WiSe23_Klausur.View;

import WiSe23_Klausur.Config.Config;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class GridFrame extends JComponent{

    private JComponent[] tiles;

    public GridFrame() throws IOException {
        tiles = new JComponent[Config.getValue("SIZE")];
        this.setLayout(new GridLayout(Config.getValue("ROWS"), Config.getValue("COLUMNS")));

        for (int i = 0; i < Config.getValue("SIZE"); i++) {
            tiles[i] = new JPanel();
            tiles[i].setBackground(Color.BLACK);

//            if (i % 2 == 0) {
//                tiles[i].setBackground(Color.BLACK);
//            }
//            else{
//                tiles[i].setBackground(Color.WHITE);
//            }

            this.add(tiles[i]);
        }

    }

    public void setBackgroundColor(int index, Color color){
        tiles[index].setBackground(color);
    }

    public int getIndex(JComponent tile) {

        for (int i = 0; i < tiles.length; i++) {
            if (tiles[i] == tile) {
                return i;
            }

        }

        return -1;

    }

    public JComponent[] getTiles(){
        return tiles;
    }






}
