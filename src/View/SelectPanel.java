package View;

import javax.swing.BoxLayout;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class SelectPanel extends JPanel {
	private String [] locationNames = {"강남구","강남대로","강동구","강변북로","강북구","강서구","공항대로","관악구","관악산",
			"광진구","구로구","궁동","금천구","남산","노원구","도봉구","도산대로","동대문구","동작구","동작대로","마포구","북한산",
			"서대문구","서초구","성동구","성북구","세곡","송파구","시흥대로","신촌로","양천구","영등포구","영등포로","용산구",
			"은평구","정릉로","종로","종로구","중구","중랑구","천호대로","청계천로","한강대로","행주","홍릉로","화랑로"};
	private String[] pollutionNames = {"이산화질소","오존","이산화탄소","아황산가스","미세먼지","초미세먼지"};
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