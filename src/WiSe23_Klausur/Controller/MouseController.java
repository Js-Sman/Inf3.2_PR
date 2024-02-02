package WiSe23_Klausur.Controller;

import WiSe23_Klausur.Model.TileMapModel;
import WiSe23_Klausur.View.MainWindow;

import javax.swing.*;
import java.awt.event.*;
import java.util.logging.Logger;

public class MouseController implements MouseListener{

    Logger logger = Logger.getLogger("OhmLogger");

    private TileMapModel model;
    private MainWindow view;

    public MouseController(TileMapModel model, MainWindow view) {
        this.model = model;
        this.view = view;
    }

    public void registerEvents() {
        for (JComponent tile : view.getGridFrame().getTiles()) {
            tile.addMouseListener(this);
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {
        logger.info("mouseEntered");
        JComponent tile = (JComponent) e.getSource();

        int index = view.getGridFrame().getIndex(tile);

        if (!model.getAtomicBoolean(index)) {
            model.startTileGenerator(index);
        }
//
//        else {
//            model.stopTileGenerator(index);
//        }


    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

}
