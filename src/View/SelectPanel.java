package View;

import javax.swing.BoxLayout;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class SelectPanel extends JPanel {
	private String [] locationNames = {"������","�������","������","�����Ϸ�","���ϱ�","������","���״��","���Ǳ�","���ǻ�",
			"������","���α�","�õ�","��õ��","����","�����","������","������","���빮��","���۱�","���۴��","������","���ѻ�",
			"���빮��","���ʱ�","������","���ϱ�","����","���ı�","������","���̷�","��õ��","��������","��������","��걸",
			"����","������","����","���α�","�߱�","�߶���","õȣ���","û��õ��","�Ѱ����","����","ȫ����","ȭ����"};
	private String[] pollutionNames = {"�̻�ȭ����","����","�̻�ȭź��","��Ȳ�갡��","�̼�����","�ʹ̼�����"};
	static private JList locationList, pollutionList;
	
	public SelectPanel() {
		this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		
		
		this.locationList = new JList(locationNames);
		this.add(new JScrollPane(locationList));
		this.locationList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	
		this.pollutionList = new JList(pollutionNames);
		this.add(new JScrollPane(pollutionList));
		this.pollutionList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			
	 }

	public static String setLocation(){
		String location = (String)locationList.getSelectedValue();
		return location;
	}
	public static String setPollution() {
		String pollution = (String)pollutionList.getSelectedValue();
		return pollution;
	}
	
}