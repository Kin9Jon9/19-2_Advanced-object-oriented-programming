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
	private String [] locationList = {"�����ϼ���","������","�������","������","�����Ϸ�","���ϱ�","������","���״��","���Ǳ�","���ǻ�",
	         "������","���α�","�õ�","��õ��","����","�����","������","������","���빮��","���۱�","���۴��","������","���ѻ�",
	         "���빮��","���ʱ�","������","���ϱ�","����","���ı�","������","���̷�","��õ��","��������","��������","��걸",
	         "����","������","����","���α�","�߱�","�߶���","õȣ���","û��õ��","�Ѱ����","����","ȫ����","ȭ����"};
	private DatePanel datePanel;
	static JComboBox locationCombo;
	
	public ListPanel() {
	locationCombo = new JComboBox(locationList);
	JPanel listPanel = new JPanel();
	JLabel listLabel = new JLabel("����");
	DatePanel datePanel = new DatePanel();
	this.setLayout(new GridLayout(2,1));
	
	this.add(listPanel);
	listPanel.setBorder(new TitledBorder("������ ����"));
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
