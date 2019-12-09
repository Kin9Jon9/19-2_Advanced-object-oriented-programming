package View;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import connectDB.DBA;

public class InputPanel extends JPanel{
	private String [] monthList = {" ","01","02","03","04","05","06","07","08","09","10","11","12"};
	private String [] dayList = {" ","01","02","03","04","05","06","07","08","09","10","11","12","13","14","15",
			"16","17","18","19","20","21","22","23","24","25","26","27","28","29","30","31"};
	private JLabel yearLb, monthLb,dayLb,laBel;
	private JButton addBtn, chBtn, delBtn;
	private JTextField yearTF, pollutionTF;
	private JComboBox<String> monthCB;
	private JComboBox<String> dayCB;
	private ActionHandler actionHandler;
	private String result;
	
	public InputPanel() {
		this.setLayout(new FlowLayout());
		this.setVisible(true);
		this.actionHandler = new ActionHandler();
		
		this.yearLb = new JLabel("년");
		this.monthLb = new JLabel("월");
		this.dayLb = new JLabel("일");
		this.laBel = new JLabel("PPM/Micro");
		//button
		this.addBtn = new JButton("추가");
		this.chBtn = new JButton("변경");
		this.delBtn = new JButton("삭제");
		//textField
		this.yearTF = new JTextField(5);
		this.pollutionTF = new JTextField(5);
		//comboBox
		this.monthCB = new JComboBox<String>(monthList);
		this.dayCB = new JComboBox<String>(dayList);
		
		this.add(yearTF);
		this.add(yearLb);
		this.add(monthCB);
		this.add(monthLb);
		this.add(dayCB);
		this.add(dayLb);
		this.add(pollutionTF);
		this.add(laBel);
		
		this.add(addBtn);
		this.addBtn.addActionListener(this.actionHandler);
		this.add(chBtn);
		this.chBtn.addActionListener(this.actionHandler);
		this.add(delBtn);
		this.delBtn.addActionListener(this.actionHandler);
	}
	
	public void addData() throws SQLException{

		String date = yearTF.getText() + monthCB.getSelectedItem() + dayCB.getSelectedItem();
		String pollution = pollutionTF.getText();
		String location = SelectPanel.setLocation();
		String sql = "" ;
		    if(SelectPanel.setPollution() == "이산화질소")
		    	sql = "INSERT INTO pollution (ppm1, date, location) VALUES (?, ?, ?)";
		    else if(SelectPanel.setPollution() == "오존")
		    	sql = "INSERT INTO pollution (ppm2, date, location) VALUES (?, ?, ?)";
		    else if(SelectPanel.setPollution() == "이산화탄소")
		    	sql = "INSERT INTO pollution (ppm3, date, location) VALUES (?, ?, ?)";
		    else if(SelectPanel.setPollution() == "아황산가스")
		    	sql = "INSERT INTO pollution (ppm4, date, location) VALUES (?, ?, ?)";
		    else if(SelectPanel.setPollution() == "미세먼지")
		    	sql = "INSERT INTO pollution (dust1, date, location) VALUES (?, ?, ?)";
		    else if(SelectPanel.setPollution() == "초미세먼지")
		    	sql = "INSERT INTO pollution (dust2, date, location) VALUES (?, ?, ?)";
		
		Connection con = DBA.makeConnection();
	    PreparedStatement pstmt = con.prepareStatement(sql);

	    pstmt.setString(1, pollution);
	    pstmt.setString(2, date);
	    pstmt.setString(3, location);
	    
	    pstmt.executeUpdate();
	    
	}
	
	public void changeData() throws SQLException{
		String date = yearTF.getText() + monthCB.getSelectedItem() + dayCB.getSelectedItem();
		String pollution = pollutionTF.getText();
		String location = SelectPanel.setLocation();
		String sql = "";
		if(SelectPanel.setPollution() == "이산화질소")
	    	sql = "UPDATE pollution set ppm1 = ? WHERE date = ? AND location = ?";
	    else if(SelectPanel.setPollution() == "오존")
	    	sql = "UPDATE pollution set ppm2 = ? WHERE date = ? AND location = ?";
    	else if(SelectPanel.setPollution() == "이산화탄소")
    		sql = "UPDATE pollution set ppm3 = ? WHERE date = ? AND location = ?";
    	else if(SelectPanel.setPollution() == "아황산가스")
    		sql = "UPDATE pollution set ppm4 = ? WHERE date = ? AND location = ?";
    	else if(SelectPanel.setPollution() == "미세먼지")
    		sql = "UPDATE pollution set dust1 = ? WHERE date = ? AND location = ?";
    	else if(SelectPanel.setPollution() == "초미세먼지")
    		sql = "UPDATE pollution set dust2 = ? WHERE date = ? AND location = ?";
		
		Connection con = DBA.makeConnection();
	    PreparedStatement pstmt = con.prepareStatement(sql);
	    
	    pstmt.setString(1, pollution);
	    pstmt.setString(2, date);
	    pstmt.setString(3, location);

	    
	    pstmt.executeUpdate();
	    try {
	         con.close();
	      } catch (SQLException e) {
	         // TODO Auto-generated catch block
	         e.printStackTrace();
	      }
	}
	
	public void delData() throws SQLException{
		String date = yearTF.getText() + monthCB.getSelectedItem() + dayCB.getSelectedItem();
		String location = SelectPanel.setLocation();
		String sql = "";
		if(SelectPanel.setPollution() == "이산화질소")
	    	sql = "UPDATE pollution set ppm1 = 0 WHERE date = ? AND location = ?";
	    else if(SelectPanel.setPollution() == "오존")
	    	sql = "UPDATE pollution set ppm2 = 0 WHERE date = ? AND location = ?";
    	else if(SelectPanel.setPollution() == "이산화탄소")
    		sql = "UPDATE pollution set ppm3 = 0 WHERE date = ? AND location = ?";
    	else if(SelectPanel.setPollution() == "아황산가스")
    		sql = "UPDATE pollution set ppm4 = 0 WHERE date = ? AND location = ?";
    	else if(SelectPanel.setPollution() == "미세먼지")
    		sql = "UPDATE pollution set dust1 = 0 WHERE date = ? AND location = ?";
    	else if(SelectPanel.setPollution() == "초미세먼지")
    		sql = "UPDATE pollution set dust2 = 0 WHERE date = ? AND location = ?";

		Connection con = DBA.makeConnection();
	    PreparedStatement pstmt = con.prepareStatement(sql);
	    
	    pstmt.setString(1, date);
	    pstmt.setString(2, location);

	    
	    pstmt.execute();
	    try {
	         con.close();
	      } catch (SQLException e) {
	         // TODO Auto-generated catch block
	         e.printStackTrace();
	      }
	}

	private class ActionHandler implements ActionListener {
		  
		@Override
		public void actionPerformed(ActionEvent e) {
			if(e.getSource() == addBtn) {
				if(yearTF.getText().isEmpty() || monthCB.getSelectedIndex()==0 || dayCB.getSelectedIndex()==0 || pollutionTF.getText().isEmpty())
					JOptionPane.showMessageDialog(null, "값을 입력하세요.");
				else 
					try {
						addData();
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					JOptionPane.showMessageDialog(null, "추가되었습니다.");
			}
			else if(e.getSource() == chBtn) {
				if(yearTF.getText().isEmpty() || monthCB.getSelectedIndex()==0 || dayCB.getSelectedIndex()==0 || pollutionTF.getText().isEmpty())
					JOptionPane.showMessageDialog(null, "값을 입력하세요.");
				else
					try {
						changeData();
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					JOptionPane.showMessageDialog(null, "변경되었습니다.");
			}
			else if(e.getSource() == delBtn) {
				if(yearTF.getText().isEmpty() || monthCB.getSelectedIndex()==0 || dayCB.getSelectedIndex()==0)
					JOptionPane.showMessageDialog(null, "값을 입력하세요.");
				else
					try {
						delData();
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					JOptionPane.showMessageDialog(null, "삭제되었습니다.");
			}
		}
		
	}
}
