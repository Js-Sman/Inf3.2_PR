package Aufgabe_Init;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author JS
 */
import javax.swing.JOptionPane;
import javax.swing.UIManager;

/**
 * Builder Class
 * @author le
 */
public class Start
{
  public Start()
  {

  }

  /**
   * @param args the command line arguments
   */
  public static void main(String[] args) 
  {
      System.out.println("Init_IntelliJ");
    try
    {
      UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
    }
    catch (Exception ex)
    {
      JOptionPane.showMessageDialog(null, ex.toString());
    }
    new Start();
  }
}
