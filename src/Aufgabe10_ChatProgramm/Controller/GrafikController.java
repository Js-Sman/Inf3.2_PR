/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package Aufgabe10_ChatProgramm.Controller;


import Aufgabe10_ChatProgramm.Model.ChatModel;
import Aufgabe10_ChatProgramm.Model.Grafik.GrafikModel;
import Aufgabe10_ChatProgramm.View.MainWindow;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.util.logging.Logger;

/**
 *
 * @author le
 */
public class GrafikController extends MouseAdapter implements MouseMotionListener
{
  private MainWindow view;
  private GrafikModel model;

  public GrafikController(MainWindow view, GrafikModel model)
  {
    this.view = view;
    this.model = model;
  }


  public void registerEvents()
  {
    view.getGrafikFrame().addMouseMotionListener(this);
    view.getGrafikFrame().addMouseListener(this);
  }

  @Override
  public void mouseDragged(MouseEvent evt)
  {
    Point p = evt.getPoint();
    model.getGrafikFrame().addPointToCurrentFigure(p);
  }

  @Override
  public void mouseReleased(MouseEvent evt)
  {
      model.endFigure();

  }
}
