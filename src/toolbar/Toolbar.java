package toolbar;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JToolBar;

import View.CheckPanel;
import View.DatePanel;
import View.ListPanel;
import dialog.DataDialog;
import dialog.GraphDialog;
import dialog.LoginDialog;
import dialog.MapDialog;

public class Toolbar extends JToolBar {
	private JButton mapBtn,graphBtn,dataBtn,clearBtn,manageBtn;
	static MapDialog mapDialog;
	static LoginDialog loginDialog;
	static DataDialog dataDialog;
	static GraphDialog graphDialog;
	private ActionHandler actionHandler;
	
	public Toolbar() {
		this.actionHandler = new ActionHandler();
		this.mapBtn = new JButton("지도");
		this.mapBtn.addActionListener(this.actionHandler);
		this.graphBtn = new JButton("그래프");
		this.graphBtn.addActionListener(this.actionHandler);
		this.dataBtn = new JButton("데이터");
		this.dataBtn.addActionListener(this.actionHandler);
		this.clearBtn = new JButton("초기화");
		this.clearBtn.addActionListener(this.actionHandler);
		this.manageBtn = new JButton("관리자");
		this.manageBtn.addActionListener(this.actionHandler);
		
		this.add(this.mapBtn);
		this.add(this.graphBtn);
		this.add(this.dataBtn);
		this.add(this.manageBtn);
		this.add(this.clearBtn);
	}

	private class ActionHandler implements ActionListener{

	      @Override
	      public void actionPerformed(ActionEvent e) {
	        String cmd = e.getActionCommand();
	        switch(cmd) {
	        case "지도":
	        	mapDialog = new MapDialog();
	        	break;
	        case "그래프":
	    		graphDialog = new GraphDialog();
	        	break;
	        case "데이터":
	    		dataDialog = new DataDialog();
	    		break;
	        case "초기화":
	        	reset();
	        	break;
	        case "관리자":
	        	loginDialog = new LoginDialog();
	        	break;
	        }
	      }
	}
	public void reset() {

		DatePanel.initialize();
		ListPanel.initialize();
		CheckPanel.initialize();
	}
	
}
