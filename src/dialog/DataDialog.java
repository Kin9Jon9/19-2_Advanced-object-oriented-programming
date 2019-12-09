package dialog;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import View.DatePanel;
import View.ListPanel;
import connectDB.DBA;

public class DataDialog extends JDialog{
	
	JLabel label1, label2, label3, label4, label5, label6, labelLocation, labelDate,
		label1Info, label2Info, label3Info, label4Info, label5Info, label6Info,
		labelLocationInfo, labelDateInfo,
		labelCircle1, labelCircle2, labelCircle3, labelCircle4, labelCircle5, labelCircle6;
	JButton saveBtn;
	   private ActionHandler actionHandler;
	   private String saveDate, saveLocation, savePpm1, savePpm2, savePpm3, savePpm4, saveDust1, saveDust2;
	public void setLayout() {
		
		this.setLayout(new BorderLayout());
		
		label1 = new JLabel("���� ����");
		label2 = new JLabel("���� ����");
		label3 = new JLabel("���� ����");
		label4 = new JLabel("���� ����");
		label5 = new JLabel("���� ����");
		label6 = new JLabel("���� ����");
		labelLocation = new JLabel("���� ����");
		labelDate = new JLabel("���� ����");
		label1Info = new JLabel("�̻�ȭ ���� ��");
		label2Info = new JLabel("���� ��");
		label3Info = new JLabel("�̻�ȭ ź�� ��");
		label4Info = new JLabel("��Ȳ�갡��");
		label5Info = new JLabel("�̼�����");
		label6Info = new JLabel("�ʹ̼�����");
		labelCircle1 = new JLabel("��");
		labelCircle2 = new JLabel("��");
		labelCircle3 = new JLabel("��");
		labelCircle4 = new JLabel("��");
		labelCircle5 = new JLabel("��");
		labelCircle6 = new JLabel("��");
		labelLocationInfo = new JLabel("������ �� : ");
		labelDateInfo = new JLabel("��¥ : ");
	    saveBtn = new JButton("��������");
	    this.actionHandler = new ActionHandler();
		
		JPanel panel1 = new JPanel();
		JPanel panel2 = new JPanel();
	    JPanel panel3 = new JPanel();
		panel2.setLayout(new GridLayout(6,3));
		
		panel1.add(labelLocationInfo);
		panel1.add(labelLocation);
		panel1.add(labelDateInfo);
		panel1.add(labelDate);
		
		panel2.add(label1Info);
		panel2.add(label1);
		panel2.add(labelCircle1);
		panel2.add(label2Info);
		panel2.add(label2);
		panel2.add(labelCircle2);
		panel2.add(label3Info);
		panel2.add(label3);
		panel2.add(labelCircle3);
		panel2.add(label4Info);
		panel2.add(label4);
		panel2.add(labelCircle4);
		panel2.add(label5Info);
		panel2.add(label5);
		panel2.add(labelCircle5);
		panel2.add(label6Info);
		panel2.add(label6);
		panel2.add(labelCircle6);
		
	    panel3.add(saveBtn);
	    this.saveBtn.addActionListener(this.actionHandler);
	      
		this.add(panel1, "North");
		this.add(panel2, "Center");
	    this.add(panel3,"East");
	      
	}
	
	public void showData() throws SQLException {
		
		// �������� �ӽ� ����
		// �̻�ȭ ���� , ����, �̻�ȭź��, ��Ȳ�갡��
		String ppm1 = "", ppm2 = "", ppm3 = "", ppm4 = "", dust1 = "", dust2 = "";
		String location= ListPanel.getLocationList();
		String date = DatePanel.getDateData();
		saveLocation = location;
		saveDate = date;
		//��ǲ (������, ��¥) ����ó��
		if(location.equals("�����ϼ���") || (date.length() != 8)){
			JOptionPane.showMessageDialog(null,"������ �Ǵ� ��¥�� �������ּ���.");
			this.dispose();
			this.setVisible(false);
			return;
		}
		
		//Query�� �ۼ�
		Connection con = DBA.makeConnection();
		String sql = "SELECT ppm1, ppm2, ppm3, ppm4, dust1, dust2 FROM pollution WHERE date = ? AND location = ?";
		PreparedStatement pstmt = con.prepareStatement(sql);
		pstmt.setString(1, date);
		pstmt.setString(2, location);
		ResultSet rs = pstmt.executeQuery();
		
		//SQL�� ��� �� �ӽ� ����.
		while(rs.next()) {
	         ppm1 = rs.getString("ppm1");
	         savePpm1 = ppm1;
	         ppm2 = rs.getString("ppm2");
	         savePpm2 = ppm2;
	         ppm3 = rs.getString("ppm3");
	         savePpm3 = ppm3;
	         ppm4 = rs.getString("ppm4");
	         savePpm4 = ppm4;
	         dust1 = rs.getString("dust1");
	         saveDust1 = dust1;
	         dust2 = rs.getString("dust2");   
	         saveDust2 = dust2;	
		}
		
		/* ���� �����ͺ��̽��� ������ ��� ���� ����
		*  ���� ���� ���� ���� ��쿡�� "���� ����" �״�� ����.
		*  DB���� ���� ���� ���� ���� �� ����
		*/
		if(!(ppm1.equals(""))){
			label1.setText(ppm1);
			//���� ���� ����
			float temp;
			if((temp = Float.parseFloat(ppm1)) <= 0.03) {
				labelCircle1.setForeground(Color.BLUE);
			}else if(temp <= 0.06) {
				labelCircle1.setForeground(Color.GREEN);
			}else if(temp <= 0.2) {
				labelCircle1.setForeground(Color.ORANGE);
			}else if(temp > 0.2){
				labelCircle1.setForeground(Color.RED);
			}
		}
		//����
		if(!(ppm2.equals(""))) {
			label2.setText(ppm2);
			//���� ���� ����
			float temp;
			if((temp = Float.parseFloat(ppm2)) <= 0.03) {
				labelCircle2.setForeground(Color.BLUE);
			}else if(temp <= 0.09) {
				labelCircle2.setForeground(Color.GREEN);
			}else if(temp <= 0.15) {
				labelCircle2.setForeground(Color.ORANGE);
			}else if(temp > 0.15){
				labelCircle2.setForeground(Color.RED);
			}
		}
		if(!(ppm3.equals(""))) {
			label3.setText(ppm3);
			//���� ���� ����
			float temp;
			if((temp = Float.parseFloat(ppm3)) <= 2) {
				labelCircle3.setForeground(Color.BLUE);
			}else if(temp <= 9) {
				labelCircle3.setForeground(Color.GREEN);
			}else if(temp <= 15) {
				labelCircle3.setForeground(Color.ORANGE);
			}else if(temp > 15){
				labelCircle3.setForeground(Color.RED);
			}
		}
		if(!(ppm4.equals(""))) {
			label4.setText(ppm4);
			//���� ���� ����
			float temp;
			if((temp = Float.parseFloat(ppm4)) <= 0.02) {
				labelCircle4.setForeground(Color.BLUE);
			}else if(temp <= 0.05) {
				labelCircle4.setForeground(Color.GREEN);
			}else if(temp <= 0.15) {
				labelCircle4.setForeground(Color.ORANGE);
			}else if(temp > 0.15){
				labelCircle4.setForeground(Color.RED);
			}
		}
		if(!(dust1.equals(""))) {
			label5.setText(dust1);
			//���� ���� ����
			int temp;
			if((temp = Integer.parseInt(dust1)) <= 30) {
				labelCircle5.setForeground(Color.BLUE);
			}else if(temp <= 80) {
				labelCircle5.setForeground(Color.GREEN);
			}else if(temp <= 150) {
				labelCircle5.setForeground(Color.ORANGE);
			}else if(temp > 150){
				labelCircle5.setForeground(Color.RED);
			}
		}
		if(!(dust2.equals(""))) {
			label6.setText(dust2);
			//���� ���� ����
			int temp;
			if((temp = Integer.parseInt(dust2)) <= 15) {
				labelCircle6.setForeground(Color.BLUE);
			}else if(temp <= 35) {
				labelCircle6.setForeground(Color.GREEN);
			}else if(temp <= 75) {
				labelCircle6.setForeground(Color.ORANGE);
			}else if(temp > 75){
				labelCircle6.setForeground(Color.RED);
			}
		}
		
		labelLocation.setText(location);
		labelDate.setText(date);
		
		try {
			con.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	private class ActionHandler implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			if(e.getSource() == saveBtn) {
				BufferedOutputStream bs = null;
				File f1 = new File("����������.csv");
				if(f1.exists()) {
					JOptionPane.showMessageDialog(null, "���� �̸��� ������ �ֽ��ϴ�.");
				}else {
					try {
						bs = new BufferedOutputStream(new FileOutputStream("����������.csv")); 
						String str = "��¥,������,�̻�ȭ����(ppm),����(ppm),�̻�ȭź��(ppm),��Ȳ�갡��(ppm),�̼�����(��/��),�ʹ̼�����(��/��)\n"+ saveDate + "," + saveLocation + "," 
									  + savePpm1 + "," + savePpm2 + "," + savePpm3 + "," + savePpm4 + "," + saveDust1 + "," + saveDust2;  
						bs.write(str.getBytes());
					} catch (Exception ee) {
						ee.getStackTrace();
					}finally {
						try {
							bs.close();
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}
				}
			}	
		}
	 }
	
	public DataDialog() {
		this.setTitle("������");
		this.setLayout();
		this.setSize(600, 320);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setVisible(true);
		try {
			this.showData();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (NullPointerException e) {
			e.printStackTrace();
		}
	}
}
