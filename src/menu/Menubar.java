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
		this.menu = new JMenu("메뉴");
		this.add(menu);
		this.menuMap = new JMenuItem("지도");
		this.menuMap.addActionListener(this.actionHandler);
		this.menuGraph = new JMenuItem("그래프");
		this.menuGraph.addActionListener(this.actionHandler);
		this.menuData = new JMenuItem("데이터");
		this.menuData.addActionListener(this.actionHandler);
		this.menuClear = new JMenuItem("초기화");
		this.menuClear.addActionListener(this.actionHandler);
		this.menuManage = new JMenuItem("관리자");
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
