package menu;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import View.CheckPanel;
import View.DatePanel;
import View.ListPanel;
import dialog.DataDialog;
import dialog.GraphDialog;
import dialog.LoginDialog;
import dialog.MapDialog;

public class Menubar extends JMenuBar {
	private JMenu menu;
	private JMenuItem menuMap,menuGraph,menuData,menuClear,menuManage;
	static MapDialog mapDialog;
	static LoginDialog loginDialog;
	static DataDialog dataDialog;
	static GraphDialog graphDialog;
	private ActionHandler actionHandler;
	
	public Menubar() {
		this.actionHandler = new ActionHandler();
		this.menu = new JMenu("�޴�");
		this.add(menu);
		this.menuMap = new JMenuItem("����");
		this.menuMap.addActionListener(this.actionHandler);
		this.menuGraph = new JMenuItem("�׷���");
		this.menuGraph.addActionListener(this.actionHandler);
		this.menuData = new JMenuItem("������");
		this.menuData.addActionListener(this.actionHandler);
		this.menuClear = new JMenuItem("�ʱ�ȭ");
		this.menuClear.addActionListener(this.actionHandler);
		this.menuManage = new JMenuItem("������");
		this.menuManage.addActionListener(this.actionHandler);
		
		this.menu.add(menuMap);
		this.menu.add(menuGraph);
		this.menu.add(menuData);
		this.menu.add(menuClear);
		this.menu.add(menuManage);
	}
	
	private class ActionHandler implements ActionListener{
	      @Override
	      public void actionPerformed(ActionEvent e) {
	        String cmd = e.getActionCommand();
	        switch(cmd) {
	        case "����":
	        	mapDialog = new MapDialog();
	        	break;
	        case "�׷���":
	    		graphDialog = new GraphDialog();
	        	break;
	        case "������":
	    		dataDialog = new DataDialog();
	    		break;
	        case "�ʱ�ȭ":
	        	reset();
	        	break;
	        case "������":
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
