/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package Aufgabe6_Wuerfel.view_old;

/**
 *
 * @author ahren
 */
public class WuerfelView extends javax.swing.JFrame
{

  /**
   * @return the btnStart
   */
  public javax.swing.JButton getBtnStart()
  {
    return btnStart;
  }

  /**
   * @return the btnStop
   */
  public javax.swing.JButton getBtnStop()
  {
    return btnStop;
  }

  /**
   * @return the lblWuerfel
   */
  public javax.swing.JLabel getLblWuerfel()
  {
    return lblWuerfel;
  }

  /**
   * Creates new form WuerfelView
   */
  public WuerfelView()
  {
    initComponents();
  }

  /**
   * This method is called from within the constructor to initialize the form.
   * WARNING: Do NOT modify this code. The content of this method is always
   * regenerated by the Form Editor.
   */
  @SuppressWarnings("unchecked")
  // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
  private void initComponents()
  {

    lblWuerfel = new javax.swing.JLabel();
    jPanel1 = new javax.swing.JPanel();
    btnStart = new javax.swing.JButton();
    btnStop = new javax.swing.JButton();

    setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

    lblWuerfel.setFont(new java.awt.Font("Segoe UI", 0, 100)); // NOI18N
    lblWuerfel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
    lblWuerfel.setText("?");
    getContentPane().add(lblWuerfel, java.awt.BorderLayout.CENTER);

    btnStart.setText("Start");
    jPanel1.add(btnStart);

    btnStop.setText("Stop");
    jPanel1.add(btnStop);

    getContentPane().add(jPanel1, java.awt.BorderLayout.PAGE_END);

    pack();
  }// </editor-fold>//GEN-END:initComponents

  /**
   * @param args the command line arguments
   */
  public static void main(String args[])
  {
    /* Set the Nimbus look and feel */
    //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
    /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
     */
    try
    {
      for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels())
      {
        if ("Nimbus".equals(info.getName()))
        {
          javax.swing.UIManager.setLookAndFeel(info.getClassName());
          break;
        }
      }
    }
    catch (ClassNotFoundException ex)
    {
      java.util.logging.Logger.getLogger(WuerfelView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
    }
    catch (InstantiationException ex)
    {
      java.util.logging.Logger.getLogger(WuerfelView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
    }
    catch (IllegalAccessException ex)
    {
      java.util.logging.Logger.getLogger(WuerfelView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
    }
    catch (javax.swing.UnsupportedLookAndFeelException ex)
    {
      java.util.logging.Logger.getLogger(WuerfelView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
    }
    //</editor-fold>

    /* Create and display the form */
    java.awt.EventQueue.invokeLater(new Runnable()
    {
      public void run()
      {
        new WuerfelView().setVisible(true);
      }
    });
  }

  // Variables declaration - do not modify//GEN-BEGIN:variables
  private javax.swing.JButton btnStart;
  private javax.swing.JButton btnStop;
  private javax.swing.JPanel jPanel1;
  private javax.swing.JLabel lblWuerfel;
  // End of variables declaration//GEN-END:variables
}
