package main;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.Toolkit;

import javax.swing.JFrame;

import menu.Menubar;
import toolbar.Toolbar;

public class MainFrame extends JFrame {
	private static final long serialVersionUID = 1L;
	private Menubar menuBar;
	private Toolbar toolBar;
	private MainPanel mainPanel;

	public MainFrame() {
		this.setSize(720, 320);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.screenSizeLocation();
		this.setVisible(true);
		this.setTitle("대기오염관리 프로그램");
		
		this.menuBar = new Menubar();
		this.setJMenuBar(menuBar);

		this.toolBar = new Toolbar();
		this.add(toolBar, BorderLayout.NORTH);
		
		this.mainPanel = new MainPanel();
		this.add(mainPanel);

	}
	public void screenSizeLocation() {
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation(dim.width / 2 - this.getSize().width / 2, dim.height / 2 - this.getSize().height / 2);
	}

}