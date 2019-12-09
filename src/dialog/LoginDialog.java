package dialog;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class LoginDialog extends JDialog {
	private static final long serialVersionUID = 1L;
	private JPanel panel1, panel2, panel3;
	private JButton loginBtn;
	private JLabel idLbl, pwLbl;
	private JTextField idTf;
	private JPasswordField pwPf;
	private ActionHandler actionHandler;
	@SuppressWarnings("unused")
	private ManageDialog manageDialog;
	private boolean bLoginCheck;

	public LoginDialog() {
		this.setTitle("������ �α���");
		this.actionHandler = new ActionHandler();
	
		this.panel1 = new JPanel();
		this.idLbl = new JLabel("ID            ");
		this.panel1.add(idLbl);
		this.idTf = new JTextField(15);
		this.panel1.add(idTf);
		this.add(panel1); 
		
		this.panel2 = new JPanel();
		this.pwLbl = new JLabel("Password");
		this.panel2.add(pwLbl);
		this.pwPf = new JPasswordField(15);
		this.panel2.add(pwPf);
		this.add(panel2);

		
		this.panel3 = new JPanel();
		this.loginBtn = new JButton("Login");
		this.loginBtn.addActionListener(this.actionHandler);
		this.panel3.add(loginBtn);
		this.add(panel3);

		this.setLayout(new FlowLayout());//new BoxLayout(this.getContentPane(), BoxLayout.Y_AXIS));
		this.setSize(230, 160);
		this.setLocation(300, 300);
		this.setVisible(true);
		this.setResizable(false);
	}
	private void callManageDialog() {
		manageDialog = new ManageDialog();
		this.setVisible(false);
	}

	public class ActionHandler implements ActionListener{

	      @Override
	      public void actionPerformed(ActionEvent e) {
	         if(e.getActionCommand() == "Login") {
	        	 isLoginCheck();
	              
	         }
	      }

	}
	
	public void isLoginCheck() {
		//���� ������ �����ϴ°ŷ� �ٲ����
		if(idTf.getText().equals("admin")&&new String(pwPf.getPassword()).equals("123")) {
			JOptionPane.showMessageDialog(null, "�α��εǾ����ϴ�.");
			bLoginCheck = true;
			
			if(isLogin()) {
				//���� ȭ�� ���� ������ ȭ������ �����ؾ���
				callManageDialog();
			}
		}
		else {
				JOptionPane.showMessageDialog(null, "���̵�/��й�ȣ�� Ȯ�����ּ���.");
		}
	}
	public boolean isLogin() {
		return bLoginCheck;
	}
 
	
}
