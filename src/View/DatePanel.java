package View;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

public class DatePanel extends JPanel {
	static JComboBox yearCombo, monthCombo, dayCombo;
	JPanel datePanel, comboPanel;
	private String [] yearList = {"","2018","2019" };
	private String [] monthList = {"","01","02","03","04","05","06","07","08","09","10","11","12"};
	private String [] dayList = {"","01","02","03","04","05","06","07","08","09","10","11","12","13","14","15",
								"16","17","18","19","20","21","22","23","24","25","26","27","28","29","30","31"};
	public DatePanel() {
		yearCombo = new JComboBox(yearList);
		monthCombo = new JComboBox(monthList);
		dayCombo = new JComboBox(dayList);
		JLabel yearLabel = new JLabel("년");
		JLabel monthLabel = new JLabel("월");
		JLabel dayLabel = new JLabel("일");
		datePanel = new JPanel();
		comboPanel = new JPanel();
		this.setBorder(new TitledBorder("날짜 선택"));
		this.setLayout(new GridLayout(2,3));
		
		this.add(comboPanel);
		comboPanel.add(yearCombo);
		comboPanel.add(yearLabel);
		
		comboPanel.add(monthCombo);
		comboPanel.add(monthLabel);
		
		comboPanel.add(dayCombo);
		comboPanel.add(dayLabel);
		
	}
	public static void initialize() {
		yearCombo.setSelectedIndex(0);
		monthCombo.setSelectedIndex(0);
		dayCombo.setSelectedIndex(0);
	}
	
	public static String getDateData() {
		String year = (String)yearCombo.getSelectedItem();
		String month = (String)monthCombo.getSelectedItem();
		String day = (String)dayCombo.getSelectedItem();
		String date = year+month+day;
		return date;
	}
}
