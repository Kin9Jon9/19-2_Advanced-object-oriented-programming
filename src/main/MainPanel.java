package main;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.BoxLayout;
import javax.swing.JPanel;

import View.LocationPanel;
import dialog.GraphDialog;

public class MainPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	private LocationPanel locationPanel;

	public MainPanel() {
		this.locationPanel = new LocationPanel();
		this.add(locationPanel);
	}


}
