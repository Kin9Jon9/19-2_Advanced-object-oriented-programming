package View;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

public class ListPanel extends JPanel {
	private String [] locationList = {"선택하세요","강남구","강남대로","강동구","강변북로","강북구","강서구","공항대로","관악구","관악산",
	         "광진구","구로구","궁동","금천구","남산","노원구","도봉구","도산대로","동대문구","동작구","동작대로","마포구","북한산",
	         "서대문구","서초구","성동구","성북구","세곡","송파구","시흥대로","신촌로","양천구","영등포구","영등포로","용산구",
	         "은평구","정릉로","종로","종로구","중구","중랑구","천호대로","청계천로","한강대로","행주","홍릉로","화랑로"};
	private DatePanel datePanel;
	static JComboBox locationCombo;
	
	public ListPanel() {
	locationCombo = new JComboBox(locationList);
	JPanel listPanel = new JPanel();
	JLabel listLabel = new JLabel("지역");
	DatePanel datePanel = new DatePanel();
	this.setLayout(new GridLayout(2,1));
	
	this.add(listPanel);
	listPanel.setBorder(new TitledBorder("측정소 선택"));
	listPanel.add(locationCombo);
	listPanel.add(listLabel);
	
	
	this.add(datePanel);
	
}
	public static void initialize() {
		locationCombo.setSelectedIndex(0);
	}
	
	public static String getLocationList() {
		String str = (String)locationCombo.getSelectedItem();
		return str;
	}
}
