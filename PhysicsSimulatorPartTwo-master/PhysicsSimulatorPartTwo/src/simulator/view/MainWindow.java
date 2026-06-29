package simulator.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.*;


import simulator.control.Controller;

public class MainWindow extends JFrame {

	private Controller _ctrl;
	public MainWindow(Controller ctrl) {
		super("Physics Simulator");
		_ctrl = ctrl;
		initGUI();
	}
	
private void initGUI() {
	
	JPanel mainPanel = new JPanel(new BorderLayout());
	setContentPane(mainPanel);
	ControlPanel ctrlPan = new ControlPanel(_ctrl);
	ctrlPan.setBackground(Color.lightGray);
	mainPanel.add(ctrlPan, BorderLayout.PAGE_START);
	Viewer v = new Viewer(_ctrl);
	v.setMinimumSize(new Dimension(200,100));
	v.setPreferredSize(new Dimension(400,600));
	BodiesTable bodTable = new BodiesTable(_ctrl);
	bodTable.setBackground(Color.LIGHT_GRAY);
	StatusBar statBar = new StatusBar(_ctrl);
	mainPanel.add(statBar, BorderLayout.PAGE_END);
	JPanel boxPanel = new JPanel();
	BoxLayout boxLayout = new BoxLayout(boxPanel, BoxLayout.Y_AXIS);
	boxPanel.setLayout(boxLayout);
	mainPanel.add(boxPanel, BorderLayout.CENTER);
	boxPanel.add(bodTable);
	boxPanel.add(v);
	boxPanel.setBackground(Color.WHITE);
	
	this.setResizable(false);
	this.setSize(750, 750);
//	this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
	this.setVisible(true);
	this.setDefaultCloseOperation(EXIT_ON_CLOSE);
	v.autoScale();
	
}

}