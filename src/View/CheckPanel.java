package View;
import java.awt.GridLayout;

import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

public class CheckPanel extends JPanel {
	static JCheckBox ppmChk1, ppmChk2, ppmChk3, ppmChk4, microChk1, microChk2;
	
	public CheckPanel(){
	ppmChk1 = new JCheckBox("이산화질소");
	ppmChk2 = new JCheckBox("오존");
	ppmChk3 = new JCheckBox("이산화탄소");
	ppmChk4 = new JCheckBox("아황산가스");
	microChk1 = new JCheckBox("미세먼지");
	microChk2 = new JCheckBox("초미세먼지");
	JLabel ppmLabel = new JLabel("PPM단위");
	JLabel ppmSelect = new JLabel("선택하세요");
	JLabel microLabel = new JLabel("Micro단위");
	JLabel microSelect = new JLabel("선택하세요");
	JPanel ppmPanel = new JPanel();
	JPanel microPanel = new JPanel();
	
	this.setLayout(new GridLayout(2,1));
	this.add(ppmPanel);
	ppmPanel.setLayout(new GridLayout(1,4));
	ppmPanel.setBorder(new TitledBorder("PPM단위"));
	ppmPanel.add(ppmChk1);
	ppmPanel.add(ppmChk2);
	ppmPanel.add(ppmChk3);
	ppmPanel.add(ppmChk4);
	
	this.add(microPanel);
	microPanel.setLayout(new GridLayout(1,2));
	microPanel.setBorder(new TitledBorder("Micro단위"));
	microPanel.add(microChk1);
	microPanel.add(microChk2);
	}
	
	
	public static void initialize() {
		
		ppmChk1.setSelected(false);
		ppmChk2.setSelected(false);
		ppmChk3.setSelected(false);
		ppmChk4.setSelected(false);	
		microChk1.setSelected(false);
		microChk2.setSelected(false);
		
		
	}
	
	public static String getChecked() {
		return "";
	} 

	public static int isCheck() {
		/*
		 * isCheck 메소드
		 *  ppm만 선택 : 0
		 *	미세먼지만 선택 : 1
		 *	중복선택 : 2
		 *  아무것도 선택 안 되어있을 때 3
		 */
		//ppm일 떄 
		if(ppmChk1.isSelected() || ppmChk2.isSelected() ||ppmChk3.isSelected() || ppmChk4.isSelected()) {
			if((microChk1.isSelected() || microChk2.isSelected())){
				return 2;
			}
			return 0;
		}else if(microChk1.isSelected() || microChk2.isSelected()) {
			return 1;
		}
		else {
			return 3;
		}
	}
	
	public static String whatCheck() {
		
		if(isCheck() == 2 || isCheck() == 3) {
			return null;
		}
		
		if(ppmChk1.isSelected()) {
			return "ppm1";
		}
		else if(ppmChk2.isSelected()) {
			return "ppm2";
		}
		else if(ppmChk3.isSelected()) {
			return "ppm3";
		}
		else if(ppmChk4.isSelected()) {
			return "ppm4";
		}
		else if(microChk1.isSelected()) {
			return "dust1";
		}
		else if(microChk2.isSelected()) {
			return "dust2";
		}
		
		return null;
	}
	
	public static String ppmCheckType() {
		String result = "";
		
		if(ppmChk1.isSelected())
			result +="ppm1,";
		if(ppmChk2.isSelected())
			result +="ppm2,";
		if(ppmChk3.isSelected())
			result +="ppm3,";
		if(ppmChk4.isSelected())
			result +="ppm4,";
		result=result.substring(0, result.length()-1);
		return result;
	}
	
	public static String dustCheckType(){
		if(microChk1.isSelected()) {
			return "dust1";
		}
	    else if(microChk2.isSelected()) {
	        return "dust2";
	    }
	    else if(microChk1.isSelected()&&microChk2.isSelected()) {
	        return "dust1,dust2";
	    }
		return null;
	}
	
	public static int getppm1() {
		if(ppmChk1.isSelected()) return 1;
		return 0;
	}
	public static int getppm2() {
		if(ppmChk2.isSelected()) return 1;
		return 0;
	}
	public static int getppm3() {
		if(ppmChk3.isSelected()) return 1;
		return 0;
	}
	public static int getppm4() {
		if(ppmChk4.isSelected()) return 1;
		return 0;
	}
	public static int getdust1() {
		if(microChk1.isSelected()) return 1;
		return 0;
	}
	public static int getdust2() {
		if(microChk2.isSelected()) return 1;
		return 0;
	}
}