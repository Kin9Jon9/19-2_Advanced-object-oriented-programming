package View;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import javax.swing.JPanel;


public class LocationPanel extends JPanel {
	private ListPanel listPanel;
	private CheckPanel checkPanel;
	
	public LocationPanel() {
		this.setLayout(new GridLayout(1,2));
		this.listPanel = new ListPanel();
		this.checkPanel = new CheckPanel();
		this.add(listPanel);
		this.add(checkPanel);
	}

}
