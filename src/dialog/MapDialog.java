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
	//40개임
	private String [] locationList = {"선택하세요","강남구","강남대로","강동구","강변북로","강북구","강서구","공항대로","관악구","관악산","광진구","구로구","궁동","금천구","남산",
			"노원구","도봉구","도산대로","동대문구","동작구","동작대로","마포구","북한산","서대문구","서초구","성동구","성북구","세곡",
			"송파구","시흥대로","신촌로","양천구","영등포구","영등포로","용산구","은평구","정릉로","종로","종로구","중구","중랑구",
			"천호대로" ,"청계천로" ,"한강대로","행주" ,"홍릉로" ,"화랑로"};
	private int [] locationX = {0,500,478,675,603,475,128,79,378,352,578,205,114,257,376,
			535,460,472,507,343,392,276,387,322,446,489,435,593,
			617,241,300,161,250,250,374,277,396,380,370,404,585,
			654,386,346,71,481,502};
	private int [] locationY = {0,397,444,300,306,134,282,343,518,546,342,454,430,509,318,
			89,60,382,249,412,437,294,70,238,470,310,204,498,
			404,531,300,381,368,358,356,167,154,210,220,278,207,
			353,283,342,241,234,185};
	//지도(배경 이미지) 삽입을 위한 변수
	private JPanel mapPanel;
	private ImageIcon mapImg = new ImageIcon("resource/지도.png");
	//query문 결과값 받아와서 key,value로 저장
	HashMap<String , String> queryResult = new HashMap<String , String>();
	private String [] result= new String[47];
	public JLabel [] dot = new JLabel[47];
	//선택한 대기오염 지수
	private String location= ListPanel.getLocationList();
	private String date = DatePanel.getDateData();
	private String pollution = "";
	
	//배경 삽입
    JPanel background = new JPanel() {
 		public void paintComponent(Graphics g) {
             g.drawImage(mapImg.getImage(), 0, 0, null);
             setOpaque(false); //그림을 표시하게 설정,투명하게 조절
             super.paintComponent(g);
         }     
     };
     
	public MapDialog(){
		this.setTitle("지도");
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
        	/* 기본  점, ● 그리기
        	 * 오염지수가 null 이면 점을 그리지 않게 구현
        	 */
        	
	     	dot[i] = new JLabel("●");
	     	this.getContentPane().add(dot[i]).setBounds(locationX[i], locationY[i], 20, 20);
	     	result[i] = queryResult.get(locationList[i]);
	     	dot[i].setToolTipText(locationList[i]+" "+this.pollution+" : "+result[i]);
        	if(result[i]==null) this.getContentPane().remove(dot[i]);
     		//오염 지수에 따른 분기
        	if(pollution.equals("이산화질소")) {
        		drawPpm1(i);
        	}else if(pollution.equals("오존")) {
        		drawPpm2(i);
        	}else if(pollution.equals("이산화탄소")) {
        		drawPpm3(i);
        	}else if(pollution.equals("아황산가스")){
        		drawPpm4(i);
        	}else if(pollution.equals("미세먼지")) {
        		drawDust1(i);
        	}else if(pollution.equals("초미세먼지")) {
        		drawDust2(i);
        	}
         }
	}
    
    public void drawPpm1(int i) {

		//도형 색깔 지정
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
		//도형 색깔 지정
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
		//도형 색깔 지정
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
		//도형 색깔 지정
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
		//도형 색깔 지정
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
		//도형 색깔 지정
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
    	//인풋 날짜 예외처리
    		if((date.length() == 4) || date.length() == 0){
    			JOptionPane.showMessageDialog(null,"날짜를 명확히 선택해주세요.");
    			this.dispose();
    			this.setVisible(false);
    			return;
   			}
    		
    		if(CheckPanel.whatCheck() == null) {
    			JOptionPane.showMessageDialog(null,"하나의 오염지수를 선택해주세요.");
    			this.dispose();
    			this.setVisible(false);
    			return;
    		}
    		//월 평균인지, 일일 인지 분기 후 DB통신
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
			pollution = "이산화질소";
			sql = "SELECT ppm1, location FROM pollution WHERE date = ?";	
		}
		if(CheckPanel.whatCheck() == "ppm2") {
			pollution = "오존";
			sql = "SELECT ppm2, location FROM pollution WHERE date = ?";
		}
		if(CheckPanel.whatCheck() == "ppm3") {
			pollution = "이산화탄소";
			sql = "SELECT ppm3, location FROM pollution WHERE date = ?";
		}
		if(CheckPanel.whatCheck() == "ppm4") {
			pollution = "아황산가스";
			sql = "SELECT ppm4, location FROM pollution WHERE date = ?";
		}
		if(CheckPanel.whatCheck() == "dust1") {
			pollution = "미세먼지";
			sql = "SELECT dust1, location FROM pollution WHERE date = ?";
		}
		if(CheckPanel.whatCheck() == "dust2") {
			pollution = "초미세먼지";
			sql = "SELECT dust2, location FROM pollution WHERE date = ?";
		}
		PreparedStatement pstmt = con.prepareStatement(sql);
		
		pstmt.setString(1, date);
		ResultSet rs = pstmt.executeQuery();
		
		//SQL문 HashMap;
		while(rs.next()) {
			queryResult.put(rs.getString("location"),rs.getString(1));
		}
    }
    
    public void connectAvgMonth() throws SQLException {
    	String sql = null;
		Connection con = DBA.makeConnection();
		if(CheckPanel.whatCheck() == "ppm1") {
			pollution = "이산화질소";
			sql = "SELECT AVG(ppm1), location FROM pollution WHERE date LIKE ? GROUP BY location";	
		}
		if(CheckPanel.whatCheck() == "ppm2") {
			pollution = "오존";
			sql = "SELECT AVG(ppm2), location FROM pollution WHERE date LIKE ? GROUP BY location";
		}
		if(CheckPanel.whatCheck() == "ppm3") {
			pollution = "이산화탄소";
			sql = "SELECT AVG(ppm3), location FROM pollution WHERE date LIKE ? GROUP BY location";
		}
		if(CheckPanel.whatCheck() == "ppm4") {
			pollution = "아황산가스";
			sql = "SELECT AVG(ppm4), location FROM pollution WHERE date LIKE ? GROUP BY location";
		}
		if(CheckPanel.whatCheck() == "dust1") {
			pollution = "미세먼지";
			sql = "SELECT AVG(dust1), location FROM pollution WHERE date LIKE ? GROUP BY location";
		}
		if(CheckPanel.whatCheck() == "dust2") {
			pollution = "초미세먼지";
			sql = "SELECT AVG(dust2), location FROM pollution WHERE date LIKE ? GROUP BY location";
		}
		PreparedStatement pstmt = con.prepareStatement(sql);
		pstmt.setString(1, date+"%");
		ResultSet rs = pstmt.executeQuery();
		
		//SQL문 HashMap;
		while(rs.next()) {
			queryResult.put(rs.getString("location"), String.format("%.4f", Float.parseFloat(rs.getString(1))));
		}
    }
    
	public void initialize() {
		
	}
}


 
