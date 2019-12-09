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
    
    //����׷����� ��׷����� �����ϱ� ���� ����
    private int style;
    
    //���� �׷��� ����
    double data[]= new double [4];
    
    //�⵵ 
    double YearAvgData[] = new double [13]; //ù��° �б�
    int YearAvgDataI[] = new int[13];//�ι�° �б�
    double DayDateD[] = new double [32]; //����° �б�
    int DayDataI[] = new int [32]; //�׹�° �б�
    
    int tempdata [] = new int [31];
    int xData[];
    int yData[];
    
    String monthIndex[] = {"01","02","03","04","05","06","07","08","09","10","11","12"};
    String dayIndex[] = {"01","02","03","04","05","06","07","08","09","10",
    					"11","12","13","14","15","16","17","18","19","20",
    					"21","22","23","24","25","26","27","28","29","30","31"};
    
    //� �׷��� DBgetData
    public void getDBData2() throws SQLException {
    	
    	// ������ ���� (�� ���), PPM���� "ù��° �б�"
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
    		
         // ������ ���� (�� ���), DUST ���� "�ι�° �б�"
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

    	//����, �� ���� (�� ���), PPM ���� "����° �б�"
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
    		
    	//����, �� ���� (�� ���), DUST ���� "�׹�° �б�"
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
    
    //����׷��� DBgetData
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
     
    //� �׷��� �׸���
    public void setDrawCurve(Graphics g) {
    	/*
    	 *     double YearAvgData[] = new double [13]; //ù��° �б�
    	 *     int YearAvgDataI[] = new int[13];//�ι�° �б�
    	 *     double DayDateD[] = new double [32]; //����° �б�
    	 *     int DayDataI[] = new int [32]; //�׹�° �б�
    	 */
    	if(date.length() == 4 && CheckPanel.isCheck() == 0) {
    		xData = new int [YearAvgData.length];
        	yData = new int [YearAvgData.length];

       	 //x��ǥ
       	 for(int i=1; i< YearAvgData.length; i++) {
      		  xData[0]=100;
      		  xData[i]=xData[i-1]+65;
      	  }
          
          //y��ǥ
      	  for(int i = 0 ; i<YearAvgData.length ; i++){
      			YearAvgData[i] *= 1500;
      			  yData[i] = 400-(int)(YearAvgData[i]);
          }
      	  //� �׷��� �׸���
      	  g.drawPolyline(xData, yData, yData.length);
      	  
          int temp=0;
          for(int i=0; i<yData.length; i++) {
             //PPM �϶�
             if(CheckPanel.isCheck()==0) {
                g.setFont(new Font("����", Font.PLAIN, 14));
                g.drawString(""+Math.round((YearAvgData[i]/1500)*1000)/1000.0, 80+temp, yData[i]-10); //�ȹٷ� ��� �ȳ���
                g.drawString("ppm", 10, 65);
                temp=temp+65;
             }
          }
    	}else if(date.length() == 4 && CheckPanel.isCheck() == 1) {
    		xData = new int [YearAvgDataI.length];
        	yData = new int [YearAvgDataI.length];
        	//x��ǥ
          	 for(int i=1; i< YearAvgDataI.length; i++) {
         		  xData[0]=100;
         		  xData[i]=xData[i-1]+65;
         	  }
             
             //y��ǥ
         	  for(int i = 0 ; i<YearAvgDataI.length ; i++){
         			 YearAvgDataI[i] *=2.5;
         			  yData[i] = 400-(int)(YearAvgDataI[i]);
             }
         	  //� �׷��� �׸���
         	  g.drawPolyline(xData, yData, yData.length);
         	  
             int temp=0;
             for(int i=0; i<yData.length; i++) {
                   g.setFont(new Font("����", Font.PLAIN, 14));
                   g.drawString(""+YearAvgDataI[i]/2.5, 80+temp, yData[i]-10); //�ȹٷ� ��� �ȳ���
                   g.drawString("��/��", 10, 65);
                   temp=temp+65;
             }
    	}
    	
    	if(date.length() == 6 && CheckPanel.isCheck() == 0) {
    		xData = new int [DayDateD.length];
        	yData = new int [DayDateD.length];
        	//x��ǥ
          	 for(int i=1; i< DayDateD.length; i++) {
         		  xData[0]=100;
         		  xData[i]=xData[i-1]+30;
         	  }
             
             //y��ǥ
         	  for(int i = 0 ; i<DayDateD.length ; i++){
         			 DayDateD[i] *= 1500;
         			  yData[i] = 400-(int)(DayDateD[i]);
             }
         	  //� �׷��� �׸���
         	  g.drawPolyline(xData, yData, yData.length);
         	  
             int temp=0;
             for(int i=0; i<yData.length; i++) {
                   g.setFont(new Font("����", Font.PLAIN, 14));
                   
                   g.drawString(""+Math.round((DayDateD[i]/1500)*1000)/1000.0, 80+temp, yData[i]-10); //�ȹٷ� ��� �ȳ���
                   g.drawString("ppm", 10, 65);
                   temp=temp+30;
             }
    	}else if(date.length() == 6 && CheckPanel.isCheck() == 1) {
    		xData = new int [DayDataI.length];
        	yData = new int [DayDataI.length];
        	//x��ǥ
          	 for(int i=1; i< DayDataI.length; i++) {
         		  xData[0]=100;
         		  xData[i]=xData[i-1]+30;
         	  }
             
             //y��ǥ
         	  for(int i = 0 ; i<DayDataI.length ; i++){
         		  DayDataI[i] *=2.5;
         		  yData[i] = 400-(int)(DayDataI[i]);
             }
         	  //� �׷��� �׸���
         	  g.drawPolyline(xData, yData, yData.length);
         	  
             int temp=0;
             for(int i=0; i<yData.length; i++) {
            	 g.setFont(new Font("����", Font.PLAIN, 14));
            	 g.drawString(""+DayDataI[i]/2.5, 80+temp, yData[i]-10); //�ȹٷ� ��� �ȳ���
            	 g.drawString("��/��", 10, 65);
            	 temp=temp+30;
             }
    	}
    	
    }
	//� PPM���� ���� ����� Ȯ��.(���� �� ��)
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
     
     //� dust���� ���� ����� Ȯ��.(���� ����)
     public void dustCheck() {
    	 if(CheckPanel.getdust1() == 0 && CheckPanel.getdust2() == 1) {
    		 data[0] = 0;
    	 }else if(CheckPanel.getdust1() == 1 && CheckPanel.getdust2() == 0) {
    		 data[1] = 0;
    	 }
     }
     //���� �׷��� �׸�
     public void drawBar(Graphics g) {
            if(CheckPanel.isCheck()==0) drawBarppm(g);
             else if(CheckPanel.isCheck()==1)drawBardust(g);
     }
     
     //���� �׷��� - ppm
     public void drawBarppm(Graphics g) {
    	  try {
 			getDBData();
 		} catch (SQLException e) {
 			// TODO Auto-generated catch block
 			e.printStackTrace();
 		}
    	 int temp = 0;
    	 //���� �ȵ� �� '0' ó��
    	 ppmCheck();
    	 for(int i = 0 ; i < 4 ;  i++) {
	            g.setFont(new Font("����", Font.PLAIN, 15));
	            g.drawString("�̻�ȭ���� �� ���� ���� : 0.031 ~ 0.06 ppm", 120, 500);
	            g.drawString("���� �� ���� ���� : 0.031 ~ 0.09 ppm", 120, 520);
	            g.drawString("�ϻ�ȭź�� �� ���� ���� : 2 ~ 9 ppm", 120, 540);
	            g.drawString("��Ȳ�� ���� ���� ���� : 0.021 ~ 0.05 ppm", 120, 560);
	            g.drawRect(95, 475, 360, 100);
	            g.drawRect(100, 480, 350, 90);
	            
	         
	             g.drawString("�̻�ȭ����",100,420);
	             g.drawString("������",200,420);
	             g.drawString("�ϻ�ȭź��",300,420);
	             g.drawString("��Ȳ�갡��", 400, 420);
	             g.drawString("ppm", 20, 65);
	
	              g.setColor(Color.BLUE);
	              g.fillRect(120+temp, (int)(400-data[i]*300), 20, (int)(data[i]*300));
	              
	              g.setColor(Color.BLACK);
	              g.setFont(new Font("����", Font.PLAIN, 15));
	              
	              //���� ���� ��ġ ǥ��
	              g.drawString(""+data[i], 110+temp, (int)((400-data[i]*300)-10));
	              temp = temp+100;
    	 }
           
     }
     //���� �׷��� - dust
     public void drawBardust(Graphics g) {
    	 try {
    		 getDBData();
    		 } catch (SQLException e) {
    			 e.printStackTrace();
    		}
    	 int temp = 0;
    	 dustCheck();
    	 for(int i = 0 ; i < 2 ; i++) {
	       	g.setFont(new Font("����", Font.PLAIN, 15));
	      	g.drawString("�̼�����  ���� ���� : 31 ~ 80 ��/��", 120, 500);
	      	g.drawString("�ʹ̼�����  ���� ���� : 16 ~ 35 ��/��", 120, 520);
	      	g.drawRect(95, 475, 300, 70);
	      	g.drawRect(100, 480, 290, 60);
	     
	      
	        g.drawString("�̼�����",100,420);
	        g.drawString("�ʹ̼�����",200,420);
	        g.drawString("��/��", 10, 65);
	        
	        //����׸���
	        g.setColor(Color.BLUE);
	        g.fillRect(120+temp, (int)(400-data[i]*1.5), 20, (int) (data[i]*1.5));
	     
	        g.setColor(Color.BLACK);
	        g.setFont(new Font("����",Font.PLAIN, 15));
	     
	        //�������� ��ġ ǥ��
	        g.drawString(""+data[i],110+temp, (int)((400-data[i]*1.5)-10));
	        temp = temp+100;
    	 }
     }
     
     //� �׷��� �׸�.
     public void drawCurve(Graphics g) {
    	 
    	 try {
			getDBData2();
		} catch (SQLException e) {
			e.printStackTrace();
		}
    	 
    	 //�׷����� �׸� �� �ְ� �����͸� ���� ����.
         setDrawCurve(g);
     }
     
     //style�� �������� (� : 0 / ���� : 1)     
     public void conditionCheck() {
    	 if(location.equals("�����ϼ���")) {
    		 JOptionPane.showMessageDialog(null,"������ �Ǵ� ��¥�� �������ּ���.");
    		 this.dispose();
    		 this.setVisible(false);
    		 return;
    	 }
    	 if(CheckPanel.isCheck() == 2 || CheckPanel.isCheck() == 3) {
    		 JOptionPane.showMessageDialog(null,"�ϳ��� ���������� �������ּ���.");
    		 this.dispose();
    		 this.setVisible(false);
    		 return;
    	 }
    	 // ���or�⸸ ���ý� �
    	 if((date.length()==4)||(date.length()==6)) {
    		 style=0;
    	 }
    	 // ����� ��� ���ý� ����
    	 else if(date.length()==8) {
    		 style=1;
    	 }
     }
    
     public GraphDialog() {
    	 this.setTitle("�׷���");
    	 this.setSize(1000, 620);
    	 this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    	 this.setVisible(true);
    	 this.setContentPane(panel);
    	 conditionCheck();
	}
    
    class graphPanel extends JPanel{
       public void paint(Graphics g) {
          
          g.setFont(new Font("����", Font.BOLD, 20));
          g.drawString("������: "+location+" / ��¥: "+date, 150, 30);

          g.drawLine(50,400,950,400); //����
          g.drawLine(50, 50 ,50, 400); //����

          //����׷���
          if(style==1) drawBar(g);
          //��׷��� 
          else if(style==0) drawCurve(g);
       }
    }
}


