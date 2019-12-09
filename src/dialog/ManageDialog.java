package dialog;

import java.awt.BorderLayout;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Toolkit;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import View.InputPanel;
import View.SelectPanel;
import menu.Menubar;
import toolbar.Toolbar;

public class ManageDialog extends JDialog {
   private static final long serialVersionUID = 1L;
   private Menubar menuBar;
   private Toolbar toolBar;
   private SelectPanel selectPanel;
   private InputPanel inputPanel;
   
   
   public ManageDialog() {
      this.setTitle("°ü¸®ÀÚ");
      this.setSize(400, 350);
      this.screenSizeLocation();
      this.setVisible(true);
      this.setResizable(false);
      this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);      
      this.setLayout(new BoxLayout(this.getContentPane(), BoxLayout.Y_AXIS));
      
      this.selectPanel = new SelectPanel();
      this.add(selectPanel);
      this.inputPanel = new InputPanel();
      this.add(inputPanel);
      
      

   }
   public void screenSizeLocation() {
      Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
      this.setLocation(dim.width / 2 - this.getSize().width / 2, dim.height / 2 - this.getSize().height / 2);
   }
}
