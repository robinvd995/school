package visualGraph;

import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class OptionsManager implements ActionListener{

	private static final int OPTIONS_WIDTH = 200;
	private static final int OPTIONS_HEIGHT = 225;
	
	private VisualGraph vg;
	
	private JDialog window;
	
	private JTextField text;
	private JTextField text1;
	private JTextField text2;
	private JTextField text3;
	
	public OptionsManager(VisualGraph visualGraph){
		vg = visualGraph;
	}
	
	public void initOptionsWindow(){
		window = new JDialog(vg.getFrame(), true);
		window.setDefaultCloseOperation(JDialog.HIDE_ON_CLOSE);
		window.setTitle("Options");
		window.setLayout(null);
		Rectangle rect = vg.getFrame().getBounds();
		int posX = (int)(rect.x + (rect.getWidth() - OPTIONS_WIDTH) / 2);
		int posY = (int)(rect.y + (rect.getHeight() - OPTIONS_HEIGHT) / 2);
		window.setBounds(posX, posY, OPTIONS_WIDTH, OPTIONS_HEIGHT);
		
		//Stuff
		JLabel xMin = new JLabel("Min X");
		xMin.setLocation(20, 20);
		xMin.setSize(60, 20);
		window.add(xMin);
		
		text = new JTextField("");
		text.setLocation(80, 20);
		text.setSize(60, 20);
		window.add(text);
		
		JLabel xMax = new JLabel("Max X");
		xMax.setLocation(20, 40);
		xMax.setSize(60, 20);
		window.add(xMax);
		
		text1 = new JTextField("");
		text1.setLocation(80, 40);
		text1.setSize(60, 20);
		window.add(text1);
		
		JLabel yMin = new JLabel("Min Y");
		yMin.setLocation(20, 60);
		yMin.setSize(60, 20);
		window.add(yMin);
		
		text2 = new JTextField("");
		text2.setLocation(80, 60);
		text2.setSize(60, 20);
		window.add(text2);
		
		JLabel yMax = new JLabel("Max Y");
		yMax.setLocation(20, 80);
		yMax.setSize(60, 20);
		window.add(yMax);
		
		text3 = new JTextField("");
		text3.setLocation(80, 80);
		text3.setSize(60, 20);
		window.add(text3);
		
		JButton apply = new JButton("Apply");
		apply.setLocation(20, 120);
		apply.setSize(80, 20);
		apply.addActionListener(this);
		apply.setActionCommand("apply");
		window.add(apply);
		
		JButton cancel = new JButton("Cancel");
		cancel.setLocation(20, 140);
		cancel.setSize(80, 20);
		cancel.addActionListener(this);
		cancel.setActionCommand("cancel");
		window.add(cancel);
	}
	
	public void openOptionsWindow(){
		window.setVisible(true);
	}
	
	public void closeOptionsWindow(){
		window.setVisible(false);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		System.out.println(e.getActionCommand());
		if(e.getActionCommand().equals("cancel")){
			closeOptionsWindow();	
		}
		if(e.getActionCommand().equals("apply")){
			//
			String s = text.getText();
			int a = Integer.parseInt(s);
			System.out.println(a);
			s = text1.getText();
			int b = Integer.parseInt(s);
			System.out.println(b);
			s = text2.getText();
			int c = Integer.parseInt(s);
			System.out.println(c);
			s = text3.getText();
			int d = Integer.parseInt(s);
			System.out.println(d);
			closeOptionsWindow();
		}
	}
}
