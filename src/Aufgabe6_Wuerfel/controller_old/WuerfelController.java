/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package Aufgabe6_Wuerfel.controller_old;

import aufgabe6_wuerfel.model.WuerfelModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import aufgabe6_wuerfel.view.WuerfelView;

/**
 *
 * @author ahren
 */
public class WuerfelController implements ActionListener
{
  private WuerfelView view;
  private WuerfelModel model;
  
  public WuerfelController(WuerfelView view, WuerfelModel model)
  {
    this.view = view;
    this.model = model;
  }

  public void registerEvents()
  {
    view.getBtnStart().addActionListener(this);
    view.getBtnStop().addActionListener(this);
  }
  
  @Override
  public void actionPerformed(ActionEvent evt)
  {
    if (evt.getSource() == view.getBtnStart())
    {
      model.start();
    }
    else
    {
      model.stop();
    }
  }
}
