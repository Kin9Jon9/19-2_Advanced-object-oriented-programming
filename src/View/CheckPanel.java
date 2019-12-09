package View;
import java.awt.GridLayout;

import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

public class CheckPanel extends JPanel {
	static JCheckBox ppmChk1, ppmChk2, ppmChk3, ppmChk4, microChk1, microChk2;
	
	public CheckPanel(){
	ppmChk1 = new JCheckBox("�̻�ȭ����");
	ppmChk2 = new JCheckBox("����");
	ppmChk3 = new JCheckBox("�̻�ȭź��");
	ppmChk4 = new JCheckBox("��Ȳ�갡��");
	microChk1 = new JCheckBox("�̼�����");
	microChk2 = new JCheckBox("�ʹ̼�����");
	JLabel ppmLabel = new JLabel("PPM����");
	JLabel ppmSelect = new JLabel("�����ϼ���");
	JLabel microLabel = new JLabel("Micro����");
	JLabel microSelect = new JLabel("�����ϼ���");
	JPanel ppmPanel = new JPanel();
	JPanel microPanel = new JPanel();
	
	this.setLayout(new GridLayout(2,1));
	this.add(ppmPanel);
	ppmPanel.setLayout(new GridLayout(1,4));
	ppmPanel.setBorder(new TitledBorder("PPM����"));
	ppmPanel.add(ppmChk1);
	ppmPanel.add(ppmChk2);
	ppmPanel.add(ppmChk3);
	ppmPanel.add(ppmChk4);
	
	this.add(microPanel);
	microPanel.setLayout(new GridLayout(1,2));
	microPanel.setBorder(new TitledBorder("Micro����"));
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
		 * isCheck �޼ҵ�
		 *  ppm�� ���� : 0
		 *	�̼������� ���� : 1
		 *	�ߺ����� : 2
		 *  �ƹ��͵� ���� �� �Ǿ����� �� 3
		 */
		//ppm�� �� 
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