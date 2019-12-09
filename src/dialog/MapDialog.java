package dialog;

import java.awt.Color;
import java.awt.Graphics;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import View.CheckPanel;
import View.DatePanel;
import View.ListPanel;
import connectDB.DBA;

public class MapDialog extends JDialog {
	//40����
	private String [] locationList = {"�����ϼ���","������","�������","������","�����Ϸ�","���ϱ�","������","���״��","���Ǳ�","���ǻ�","������","���α�","�õ�","��õ��","����",
			"�����","������","������","���빮��","���۱�","���۴��","������","���ѻ�","���빮��","���ʱ�","������","���ϱ�","����",
			"���ı�","������","���̷�","��õ��","��������","��������","��걸","����","������","����","���α�","�߱�","�߶���",
			"õȣ���" ,"û��õ��" ,"�Ѱ����","����" ,"ȫ����" ,"ȭ����"};
	private int [] locationX = {0,500,478,675,603,475,128,79,378,352,578,205,114,257,376,
			535,460,472,507,343,392,276,387,322,446,489,435,593,
			617,241,300,161,250,250,374,277,396,380,370,404,585,
			654,386,346,71,481,502};
	private int [] locationY = {0,397,444,300,306,134,282,343,518,546,342,454,430,509,318,
			89,60,382,249,412,437,294,70,238,470,310,204,498,
			404,531,300,381,368,358,356,167,154,210,220,278,207,
			353,283,342,241,234,185};
	//����(��� �̹���) ������ ���� ����
	private JPanel mapPanel;
	private ImageIcon mapImg = new ImageIcon("resource/����.png");
	//query�� ����� �޾ƿͼ� key,value�� ����
	HashMap<String , String> queryResult = new HashMap<String , String>();
	private String [] result= new String[47];
	public JLabel [] dot = new JLabel[47];
	//������ ������ ����
	private String location= ListPanel.getLocationList();
	private String date = DatePanel.getDateData();
	private String pollution = "";
	
	//��� ����
    JPanel background = new JPanel() {
 		public void paintComponent(Graphics g) {
             g.drawImage(mapImg.getImage(), 0, 0, null);
             setOpaque(false); //�׸��� ǥ���ϰ� ����,�����ϰ� ����
             super.paintComponent(g);
         }     
     };
     
	public MapDialog(){
		this.setTitle("����");
		this.setSize(760, 650);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setVisible(true);
		this.setResizable(false);

		getInfo();

		this.mapPanel = new JPanel();
		this.add(mapPanel);
		this.setContentPane(background);
		this.getContentPane().setLayout(null);
        for(int i = 1 ; i <= locationList.length-1 ; i++) {
        	/* �⺻  ��, �� �׸���
        	 * ���������� null �̸� ���� �׸��� �ʰ� ����
        	 */
        	
	     	dot[i] = new JLabel("��");
	     	this.getContentPane().add(dot[i]).setBounds(locationX[i], locationY[i], 20, 20);
	     	result[i] = queryResult.get(locationList[i]);
	     	dot[i].setToolTipText(locationList[i]+" "+this.pollution+" : "+result[i]);
        	if(result[i]==null) this.getContentPane().remove(dot[i]);
     		//���� ������ ���� �б�
        	if(pollution.equals("�̻�ȭ����")) {
        		drawPpm1(i);
        	}else if(pollution.equals("����")) {
        		drawPpm2(i);
        	}else if(pollution.equals("�̻�ȭź��")) {
        		drawPpm3(i);
        	}else if(pollution.equals("��Ȳ�갡��")){
        		drawPpm4(i);
        	}else if(pollution.equals("�̼�����")) {
        		drawDust1(i);
        	}else if(pollution.equals("�ʹ̼�����")) {
        		drawDust2(i);
        	}
         }
	}
    
    public void drawPpm1(int i) {

		//���� ���� ����
		float temp;
		if(result[i] == null || result[i].equals("")) return;
		if((temp = Float.parseFloat(result[i])) <= 0.03) {
			dot[i].setForeground(Color.BLUE);
		}else if(temp <= 0.06) {
			dot[i].setForeground(Color.GREEN);
		}else if(temp <= 0.2) {
			dot[i].setForeground(Color.ORANGE);
		}else if(temp > 0.2){
			dot[i].setForeground(Color.RED);
		}
    }
    public void drawPpm2(int i) {
		//���� ���� ����
		float temp;
		if(result[i] == null || result[i].equals("")) return;
		if((temp = Float.parseFloat(result[i])) <= 0.03) {
			dot[i].setForeground(Color.BLUE);
		}else if(temp <= 0.09) {
			dot[i].setForeground(Color.GREEN);
		}else if(temp <= 0.15) {
			dot[i].setForeground(Color.ORANGE);
		}else if(temp > 0.15){
			dot[i].setForeground(Color.RED);
		}
    }
    public void drawPpm3(int i) {
		//���� ���� ����
		float temp;
		if(result[i] == null || result[i].equals("")) return;
		if((temp = Float.parseFloat(result[i])) <= 2) {
			dot[i].setForeground(Color.BLUE);
		}else if(temp <= 9) {
			dot[i].setForeground(Color.GREEN);
		}else if(temp <= 15) {
			dot[i].setForeground(Color.ORANGE);
		}else if(temp > 15){
			dot[i].setForeground(Color.RED);
		}
    }
    public void drawPpm4(int i) {
		//���� ���� ����
		float temp;
		if(result[i] == null || result[i].equals("")) return;
		if((temp = Float.parseFloat(result[i])) <= 0.02) {
			dot[i].setForeground(Color.BLUE);
		}else if(temp <= 0.05) {
			dot[i].setForeground(Color.GREEN);
		}else if(temp <= 0.15) {
			dot[i].setForeground(Color.ORANGE);
		}else if(temp > 0.15){
			dot[i].setForeground(Color.RED);
		}
    }
    public void drawDust1(int i) {
		//���� ���� ����
		float temp;
		if(result[i] == null || result[i].equals("")) return;
		if((temp = Float.parseFloat(result[i])) <= 30) {
			dot[i].setForeground(Color.BLUE);
		}else if(temp <= 80) {
			dot[i].setForeground(Color.GREEN);
		}else if(temp <= 150) {
			dot[i].setForeground(Color.ORANGE);
		}else if(temp > 150){
			dot[i].setForeground(Color.RED);
		}
    }
    public void drawDust2(int i) {
		//���� ���� ����
		float temp;
		if(result[i] == null || result[i].equals("")) return;
		if((temp = Float.parseFloat(result[i])) <= 15) {
			dot[i].setForeground(Color.BLUE);
		}else if(temp <= 35) {
			dot[i].setForeground(Color.GREEN);
		}else if(temp <= 75) {
			dot[i].setForeground(Color.ORANGE);
		}else if(temp > 75){
			dot[i].setForeground(Color.RED);
		}
    }
    
    public void getInfo(){
    	//��ǲ ��¥ ����ó��
    		if((date.length() == 4) || date.length() == 0){
    			JOptionPane.showMessageDialog(null,"��¥�� ��Ȯ�� �������ּ���.");
    			this.dispose();
    			this.setVisible(false);
    			return;
   			}
    		
    		if(CheckPanel.whatCheck() == null) {
    			JOptionPane.showMessageDialog(null,"�ϳ��� ���������� �������ּ���.");
    			this.dispose();
    			this.setVisible(false);
    			return;
    		}
    		//�� �������, ���� ���� �б� �� DB���
    		if(date.length() == 8) {
				try {
					connectDay();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
    		}
			else if(date.length() == 6){
				try {
					connectAvgMonth();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
    }
    
    public void connectDay() throws SQLException {
    	String sql = null;
		Connection con = DBA.makeConnection();
		if(CheckPanel.whatCheck() == "ppm1") {
			pollution = "�̻�ȭ����";
			sql = "SELECT ppm1, location FROM pollution WHERE date = ?";	
		}
		if(CheckPanel.whatCheck() == "ppm2") {
			pollution = "����";
			sql = "SELECT ppm2, location FROM pollution WHERE date = ?";
		}
		if(CheckPanel.whatCheck() == "ppm3") {
			pollution = "�̻�ȭź��";
			sql = "SELECT ppm3, location FROM pollution WHERE date = ?";
		}
		if(CheckPanel.whatCheck() == "ppm4") {
			pollution = "��Ȳ�갡��";
			sql = "SELECT ppm4, location FROM pollution WHERE date = ?";
		}
		if(CheckPanel.whatCheck() == "dust1") {
			pollution = "�̼�����";
			sql = "SELECT dust1, location FROM pollution WHERE date = ?";
		}
		if(CheckPanel.whatCheck() == "dust2") {
			pollution = "�ʹ̼�����";
			sql = "SELECT dust2, location FROM pollution WHERE date = ?";
		}
		PreparedStatement pstmt = con.prepareStatement(sql);
		
		pstmt.setString(1, date);
		ResultSet rs = pstmt.executeQuery();
		
		//SQL�� HashMap;
		while(rs.next()) {
			queryResult.put(rs.getString("location"),rs.getString(1));
		}
    }
    
    public void connectAvgMonth() throws SQLException {
    	String sql = null;
		Connection con = DBA.makeConnection();
		if(CheckPanel.whatCheck() == "ppm1") {
			pollution = "�̻�ȭ����";
			sql = "SELECT AVG(ppm1), location FROM pollution WHERE date LIKE ? GROUP BY location";	
		}
		if(CheckPanel.whatCheck() == "ppm2") {
			pollution = "����";
			sql = "SELECT AVG(ppm2), location FROM pollution WHERE date LIKE ? GROUP BY location";
		}
		if(CheckPanel.whatCheck() == "ppm3") {
			pollution = "�̻�ȭź��";
			sql = "SELECT AVG(ppm3), location FROM pollution WHERE date LIKE ? GROUP BY location";
		}
		if(CheckPanel.whatCheck() == "ppm4") {
			pollution = "��Ȳ�갡��";
			sql = "SELECT AVG(ppm4), location FROM pollution WHERE date LIKE ? GROUP BY location";
		}
		if(CheckPanel.whatCheck() == "dust1") {
			pollution = "�̼�����";
			sql = "SELECT AVG(dust1), location FROM pollution WHERE date LIKE ? GROUP BY location";
		}
		if(CheckPanel.whatCheck() == "dust2") {
			pollution = "�ʹ̼�����";
			sql = "SELECT AVG(dust2), location FROM pollution WHERE date LIKE ? GROUP BY location";
		}
		PreparedStatement pstmt = con.prepareStatement(sql);
		pstmt.setString(1, date+"%");
		ResultSet rs = pstmt.executeQuery();
		
		//SQL�� HashMap;
		while(rs.next()) {
			queryResult.put(rs.getString("location"), String.format("%.4f", Float.parseFloat(rs.getString(1))));
		}
    }
    
	public void initialize() {
		
	}
}


 
