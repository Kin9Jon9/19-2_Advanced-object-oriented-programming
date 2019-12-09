package dialog;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import View.CheckPanel;
import View.DatePanel;
import View.ListPanel;
import connectDB.DBA;

public class GraphDialog extends JDialog{
	private graphPanel panel = new graphPanel();
    String location= ListPanel.getLocationList();
    String date = DatePanel.getDateData();
    
    //막대그래프와 곡선그래프를 구별하기 위한 변수
    private int style;
    
    //막대 그래프 변수
    double data[]= new double [4];
    
    //년도 
    double YearAvgData[] = new double [13]; //첫번째 분기
    int YearAvgDataI[] = new int[13];//두번째 분기
    double DayDateD[] = new double [32]; //세번째 분기
    int DayDataI[] = new int [32]; //네번째 분기
    
    int tempdata [] = new int [31];
    int xData[];
    int yData[];
    
    String monthIndex[] = {"01","02","03","04","05","06","07","08","09","10","11","12"};
    String dayIndex[] = {"01","02","03","04","05","06","07","08","09","10",
    					"11","12","13","14","15","16","17","18","19","20",
    					"21","22","23","24","25","26","27","28","29","30","31"};
    
    //곡선 그래프 DBgetData
    public void getDBData2() throws SQLException {
    	
    	// 연도만 선택 (월 평균), PPM선택 "첫번째 분기"
    	if(date.length() == 4 && CheckPanel.isCheck() == 0) {
    		Connection con = DBA.makeConnection();
    		for(String index : monthIndex) {
	    		String sql = "SELECT AVG("+CheckPanel.whatCheck()+") FROM pollution WHERE location = ? AND date LIKE ? GROUP BY location";
	    		PreparedStatement pstmt = con.prepareStatement(sql);
	            pstmt.setString(1, location);
	            pstmt.setString(2, date+index+"%");
	            ResultSet rs = pstmt.executeQuery();
	            
	            while(rs.next()){
	            	YearAvgData[Integer.parseInt(index)] = Double.parseDouble(rs.getString(1));
		        }
            }
    		
         // 연도만 선택 (월 평균), DUST 선택 "두번째 분기"
    	}else if(date.length() == 4 && CheckPanel.isCheck() == 1) {
    		Connection con = DBA.makeConnection();
    		for(String index : monthIndex) {
	    		String sql = "SELECT AVG("+CheckPanel.whatCheck()+") FROM pollution WHERE location = ? AND date LIKE ? GROUP BY location";
	    		PreparedStatement pstmt = con.prepareStatement(sql);
	            pstmt.setString(1, location);
	            pstmt.setString(2, date+index+"%");
	            ResultSet rs = pstmt.executeQuery();
	            
	            while(rs.next()){
	            	YearAvgDataI[Integer.parseInt(index)] = (int)Double.parseDouble(rs.getString(1));
	            }
            }
    	}

    	//연도, 월 선택 (일 평균), PPM 선택 "세번째 분기"
    	if(date.length()==6 && CheckPanel.isCheck()==0) {
    		Connection con = DBA.makeConnection();
    		for(String index : dayIndex) {
	            String sql = "SELECT "+CheckPanel.whatCheck()+" FROM pollution WHERE location = ? AND date = ? ";
	            PreparedStatement pstmt = con.prepareStatement(sql);
	            pstmt.setString(1, location);
	            pstmt.setString(2, date+index);
	            ResultSet rs = pstmt.executeQuery();
	
	            while(rs.next()) {
	            	DayDateD[Integer.parseInt(index)] = Double.parseDouble(rs.getString(1));
	            }
    		}
    		
    	//연도, 월 선택 (일 평균), DUST 선택 "네번째 분기"
    	}else if(date.length() == 6 && CheckPanel.isCheck() == 1) {
    		Connection con = DBA.makeConnection();
    		for(String index : dayIndex) {
	            String sql = "SELECT "+CheckPanel.whatCheck()+" FROM pollution WHERE location = ? AND date = ? ";
	            PreparedStatement pstmt = con.prepareStatement(sql);
	            pstmt.setString(1, location);
	            pstmt.setString(2, date+index);
	            ResultSet rs = pstmt.executeQuery();
	
	            while(rs.next()) {
	            	DayDataI[Integer.parseInt(index)] =  Integer.parseInt(rs.getString(1));
	            }
    		}
    	}
    	
    }
    
    //막대그래프 DBgetData
    public void getDBData() throws SQLException{
        if(CheckPanel.isCheck()==0) {
            Connection con = DBA.makeConnection();
            String sql = "SELECT ppm1, ppm2, ppm3, ppm4 FROM pollution WHERE location = ? AND date = ?";
            PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.setString(1, location);
            pstmt.setString(2, date);
            ResultSet rs = pstmt.executeQuery();

            while(rs.next()) {
         	   data[0] = Double.parseDouble(rs.getString(1));
         	   data[1] = Double.parseDouble(rs.getString(2));
         	   data[2] = Double.parseDouble(rs.getString(3));
         	   data[3] = Double.parseDouble(rs.getString(4));
            }
        }
        	
        else if(CheckPanel.isCheck()==1) {
        	Connection con = DBA.makeConnection();
            String sql = "SELECT dust1, dust2 FROM pollution WHERE location = ? AND date = ?";
            PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.setString(1, location);
            pstmt.setString(2, date);
            ResultSet rs = pstmt.executeQuery();
            
            while(rs.next()) {
                data[0] = Double.parseDouble(rs.getString(1));
                data[1] = Double.parseDouble(rs.getString(2));
            }
        }
    }
     
    //곡선 그래프 그리기
    public void setDrawCurve(Graphics g) {
    	/*
    	 *     double YearAvgData[] = new double [13]; //첫번째 분기
    	 *     int YearAvgDataI[] = new int[13];//두번째 분기
    	 *     double DayDateD[] = new double [32]; //세번째 분기
    	 *     int DayDataI[] = new int [32]; //네번째 분기
    	 */
    	if(date.length() == 4 && CheckPanel.isCheck() == 0) {
    		xData = new int [YearAvgData.length];
        	yData = new int [YearAvgData.length];

       	 //x좌표
       	 for(int i=1; i< YearAvgData.length; i++) {
      		  xData[0]=100;
      		  xData[i]=xData[i-1]+65;
      	  }
          
          //y좌표
      	  for(int i = 0 ; i<YearAvgData.length ; i++){
      			YearAvgData[i] *= 1500;
      			  yData[i] = 400-(int)(YearAvgData[i]);
          }
      	  //곡선 그래프 그리기
      	  g.drawPolyline(xData, yData, yData.length);
      	  
          int temp=0;
          for(int i=0; i<yData.length; i++) {
             //PPM 일때
             if(CheckPanel.isCheck()==0) {
                g.setFont(new Font("돋움", Font.PLAIN, 14));
                g.drawString(""+Math.round((YearAvgData[i]/1500)*1000)/1000.0, 80+temp, yData[i]-10); //똑바로 결과 안나옴
                g.drawString("ppm", 10, 65);
                temp=temp+65;
             }
          }
    	}else if(date.length() == 4 && CheckPanel.isCheck() == 1) {
    		xData = new int [YearAvgDataI.length];
        	yData = new int [YearAvgDataI.length];
        	//x좌표
          	 for(int i=1; i< YearAvgDataI.length; i++) {
         		  xData[0]=100;
         		  xData[i]=xData[i-1]+65;
         	  }
             
             //y좌표
         	  for(int i = 0 ; i<YearAvgDataI.length ; i++){
         			 YearAvgDataI[i] *=2.5;
         			  yData[i] = 400-(int)(YearAvgDataI[i]);
             }
         	  //곡선 그래프 그리기
         	  g.drawPolyline(xData, yData, yData.length);
         	  
             int temp=0;
             for(int i=0; i<yData.length; i++) {
                   g.setFont(new Font("돋움", Font.PLAIN, 14));
                   g.drawString(""+YearAvgDataI[i]/2.5, 80+temp, yData[i]-10); //똑바로 결과 안나옴
                   g.drawString("㎍/㎥", 10, 65);
                   temp=temp+65;
             }
    	}
    	
    	if(date.length() == 6 && CheckPanel.isCheck() == 0) {
    		xData = new int [DayDateD.length];
        	yData = new int [DayDateD.length];
        	//x좌표
          	 for(int i=1; i< DayDateD.length; i++) {
         		  xData[0]=100;
         		  xData[i]=xData[i-1]+30;
         	  }
             
             //y좌표
         	  for(int i = 0 ; i<DayDateD.length ; i++){
         			 DayDateD[i] *= 1500;
         			  yData[i] = 400-(int)(DayDateD[i]);
             }
         	  //곡선 그래프 그리기
         	  g.drawPolyline(xData, yData, yData.length);
         	  
             int temp=0;
             for(int i=0; i<yData.length; i++) {
                   g.setFont(new Font("돋움", Font.PLAIN, 14));
                   
                   g.drawString(""+Math.round((DayDateD[i]/1500)*1000)/1000.0, 80+temp, yData[i]-10); //똑바로 결과 안나옴
                   g.drawString("ppm", 10, 65);
                   temp=temp+30;
             }
    	}else if(date.length() == 6 && CheckPanel.isCheck() == 1) {
    		xData = new int [DayDataI.length];
        	yData = new int [DayDataI.length];
        	//x좌표
          	 for(int i=1; i< DayDataI.length; i++) {
         		  xData[0]=100;
         		  xData[i]=xData[i-1]+30;
         	  }
             
             //y좌표
         	  for(int i = 0 ; i<DayDataI.length ; i++){
         		  DayDataI[i] *=2.5;
         		  yData[i] = 400-(int)(DayDataI[i]);
             }
         	  //곡선 그래프 그리기
         	  g.drawPolyline(xData, yData, yData.length);
         	  
             int temp=0;
             for(int i=0; i<yData.length; i++) {
            	 g.setFont(new Font("돋움", Font.PLAIN, 14));
            	 g.drawString(""+DayDataI[i]/2.5, 80+temp, yData[i]-10); //똑바로 결과 안나옴
            	 g.drawString("㎍/㎥", 10, 65);
            	 temp=temp+30;
             }
    	}
    	
    }
	//어떤 PPM들이 선택 됬는지 확인.(열지 말 것)
     public void ppmCheck() {
    	 if(CheckPanel.getppm1() == 0 && CheckPanel.getppm2() == 0 && CheckPanel.getppm3() == 0 && CheckPanel.getppm4() == 1) {
    		 data[0] = 0;
    		 data[1] = 0;
    		 data[2] = 0;
    	 }else if(CheckPanel.getppm1() == 0 && CheckPanel.getppm2() == 0 && CheckPanel.getppm3() == 1 && CheckPanel.getppm4() == 0) {
    		 data[0] = 0;
    		 data[1] = 0;
    		 data[3] = 0;
    	 }else if(CheckPanel.getppm1() == 0 && CheckPanel.getppm2() == 0 && CheckPanel.getppm3() == 1 && CheckPanel.getppm4() == 1) {
    		 data[0] = 0;
    		 data[1] = 0;
    	 }else if(CheckPanel.getppm1() == 0 && CheckPanel.getppm2() == 1 && CheckPanel.getppm3() == 0 && CheckPanel.getppm4() == 0) {
    		 data[0] = 0;
    		 data[2] = 0;
    		 data[3] = 0;
    	 }else if(CheckPanel.getppm1() == 0 && CheckPanel.getppm2() == 1 && CheckPanel.getppm3() == 0 && CheckPanel.getppm4() == 1) {
    		 data[0] = 0;
    		 data[2] = 0;
    	 }else if(CheckPanel.getppm1() == 0 && CheckPanel.getppm2() == 1 && CheckPanel.getppm3() == 1 && CheckPanel.getppm4() == 0) {
    		 data[0] = 0;
    		 data[3] = 0;
    	 }else if(CheckPanel.getppm1() == 0 && CheckPanel.getppm2() == 1 && CheckPanel.getppm3() == 1 && CheckPanel.getppm4() == 1) {
    		 data[0] = 0;	 
    	 }else if(CheckPanel.getppm1() == 1 && CheckPanel.getppm2() == 0 && CheckPanel.getppm3() == 0 && CheckPanel.getppm4() == 0) {
    		 data[1] = 0;
    		 data[2] = 0;
    		 data[3] = 0;
    	 }else if(CheckPanel.getppm1() == 1 && CheckPanel.getppm2() == 0 && CheckPanel.getppm3() == 0 && CheckPanel.getppm4() == 1) {
    		 data[1] = 0;
    		 data[2] = 0;
    	 }else if(CheckPanel.getppm1() == 1 && CheckPanel.getppm2() == 0 && CheckPanel.getppm3() == 1 && CheckPanel.getppm4() == 0) {
    		 data[1] = 0;
    		 data[3] = 0;
    	 }else if(CheckPanel.getppm1() == 1 && CheckPanel.getppm2() == 0 && CheckPanel.getppm3() == 1 && CheckPanel.getppm4() == 1) {
    		 data[1] = 0;
    	 }else if(CheckPanel.getppm1() == 1 && CheckPanel.getppm2() == 1 && CheckPanel.getppm3() == 0 && CheckPanel.getppm4() == 0) {
    		 data[2] = 0;
    		 data[3] = 0;
    	 }else if(CheckPanel.getppm1() == 1 && CheckPanel.getppm2() == 1 && CheckPanel.getppm3() == 0 && CheckPanel.getppm4() == 1) {
    		 data[2] = 0;
    	 }else if(CheckPanel.getppm1() == 1 && CheckPanel.getppm2() == 1 && CheckPanel.getppm3() == 1 && CheckPanel.getppm4() == 0) {
    		 data[3] = 0;
    	 }
     }
     
     //어떤 dust들이 선택 됬는지 확인.(열지 말것)
     public void dustCheck() {
    	 if(CheckPanel.getdust1() == 0 && CheckPanel.getdust2() == 1) {
    		 data[0] = 0;
    	 }else if(CheckPanel.getdust1() == 1 && CheckPanel.getdust2() == 0) {
    		 data[1] = 0;
    	 }
     }
     //막대 그래프 그림
     public void drawBar(Graphics g) {
            if(CheckPanel.isCheck()==0) drawBarppm(g);
             else if(CheckPanel.isCheck()==1)drawBardust(g);
     }
     
     //막대 그래프 - ppm
     public void drawBarppm(Graphics g) {
    	  try {
 			getDBData();
 		} catch (SQLException e) {
 			// TODO Auto-generated catch block
 			e.printStackTrace();
 		}
    	 int temp = 0;
    	 //선택 안된 값 '0' 처리
    	 ppmCheck();
    	 for(int i = 0 ; i < 4 ;  i++) {
	            g.setFont(new Font("돋움", Font.PLAIN, 15));
	            g.drawString("이산화질소 농도 보통 범위 : 0.031 ~ 0.06 ppm", 120, 500);
	            g.drawString("오존 농도 보통 범위 : 0.031 ~ 0.09 ppm", 120, 520);
	            g.drawString("일산화탄소 농도 보통 범위 : 2 ~ 9 ppm", 120, 540);
	            g.drawString("아황산 가스 보통 범위 : 0.021 ~ 0.05 ppm", 120, 560);
	            g.drawRect(95, 475, 360, 100);
	            g.drawRect(100, 480, 350, 90);
	            
	         
	             g.drawString("이산화질소",100,420);
	             g.drawString("오존농도",200,420);
	             g.drawString("일산화탄소",300,420);
	             g.drawString("아황산가스", 400, 420);
	             g.drawString("ppm", 20, 65);
	
	              g.setColor(Color.BLUE);
	              g.fillRect(120+temp, (int)(400-data[i]*300), 20, (int)(data[i]*300));
	              
	              g.setColor(Color.BLACK);
	              g.setFont(new Font("돋움", Font.PLAIN, 15));
	              
	              //막대 위에 수치 표현
	              g.drawString(""+data[i], 110+temp, (int)((400-data[i]*300)-10));
	              temp = temp+100;
    	 }
           
     }
     //막대 그래프 - dust
     public void drawBardust(Graphics g) {
    	 try {
    		 getDBData();
    		 } catch (SQLException e) {
    			 e.printStackTrace();
    		}
    	 int temp = 0;
    	 dustCheck();
    	 for(int i = 0 ; i < 2 ; i++) {
	       	g.setFont(new Font("돋움", Font.PLAIN, 15));
	      	g.drawString("미세먼지  보통 범위 : 31 ~ 80 ㎍/㎥", 120, 500);
	      	g.drawString("초미세먼지  보통 범위 : 16 ~ 35 ㎍/㎥", 120, 520);
	      	g.drawRect(95, 475, 300, 70);
	      	g.drawRect(100, 480, 290, 60);
	     
	      
	        g.drawString("미세먼지",100,420);
	        g.drawString("초미세먼지",200,420);
	        g.drawString("㎍/㎥", 10, 65);
	        
	        //막대그리기
	        g.setColor(Color.BLUE);
	        g.fillRect(120+temp, (int)(400-data[i]*1.5), 20, (int) (data[i]*1.5));
	     
	        g.setColor(Color.BLACK);
	        g.setFont(new Font("돋움",Font.PLAIN, 15));
	     
	        //막대위에 수치 표현
	        g.drawString(""+data[i],110+temp, (int)((400-data[i]*1.5)-10));
	        temp = temp+100;
    	 }
     }
     
     //곡선 그래프 그림.
     public void drawCurve(Graphics g) {
    	 
    	 try {
			getDBData2();
		} catch (SQLException e) {
			e.printStackTrace();
		}
    	 
    	 //그래프를 그릴 수 있게 데이터를 설정 해줌.
         setDrawCurve(g);
     }
     
     //style을 지정해줌 (곡선 : 0 / 막대 : 1)     
     public void conditionCheck() {
    	 if(location.equals("선택하세요")) {
    		 JOptionPane.showMessageDialog(null,"측정소 또는 날짜를 선택해주세요.");
    		 this.dispose();
    		 this.setVisible(false);
    		 return;
    	 }
    	 if(CheckPanel.isCheck() == 2 || CheckPanel.isCheck() == 3) {
    		 JOptionPane.showMessageDialog(null,"하나의 오염지수만 선택해주세요.");
    		 this.dispose();
    		 this.setVisible(false);
    		 return;
    	 }
    	 // 년월or년만 선택시 곡선
    	 if((date.length()==4)||(date.length()==6)) {
    		 style=0;
    	 }
    	 // 년월일 모두 선택시 막대
    	 else if(date.length()==8) {
    		 style=1;
    	 }
     }
    
     public GraphDialog() {
    	 this.setTitle("그래프");
    	 this.setSize(1000, 620);
    	 this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    	 this.setVisible(true);
    	 this.setContentPane(panel);
    	 conditionCheck();
	}
    
    class graphPanel extends JPanel{
       public void paint(Graphics g) {
          
          g.setFont(new Font("돋움", Font.BOLD, 20));
          g.drawString("측정소: "+location+" / 날짜: "+date, 150, 30);

          g.drawLine(50,400,950,400); //가로
          g.drawLine(50, 50 ,50, 400); //세로

          //막대그래프
          if(style==1) drawBar(g);
          //곡선그래프 
          else if(style==0) drawCurve(g);
       }
    }
}


