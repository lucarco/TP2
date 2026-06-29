package simulator.view;

import java.awt.FlowLayout;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

import simulator.control.Controller;
import simulator.model.Body;
import simulator.model.SimulatorObserver;

public class StatusBar extends JPanel implements SimulatorObserver {
	
	private JLabel _currTime = new JLabel(); // for current time
	private JLabel _currLaws = new JLabel();// for force laws
	private JLabel _numOfBodies = new JLabel(); // for number of bodies


	
	JPanel timePanel = new JPanel();
	JPanel numOfBodiesPanel = new JPanel();
	JPanel lawsPanel = new JPanel();
	
	StatusBar(Controller ctrl) {
	initGUI();
	ctrl.addObserver(this);
	}
	
	private void initGUI() {
	this.setLayout( new FlowLayout( FlowLayout.LEFT ));
	this.setBorder( BorderFactory.createBevelBorder( 1 ));
	
	_currTime.setText("Time: ");
	_currLaws.setText("Force Laws: ");
	_numOfBodies.setText("Number of bodies: ");
	
	update();
	
	}
	
	public void update() {
		
		timePanel.add(_currTime);
		this.add(timePanel);
		
		numOfBodiesPanel.add(_numOfBodies);
		this.add(numOfBodiesPanel);
		
		lawsPanel.add(_currLaws);
		this.add(lawsPanel);
		
	}
	


	@Override
	public void onRegister(List<Body> bodies, double time, double dt, String fLawsDesc) {
		_currTime.setText("Time: " + Double.toString(time));
		_currLaws.setText("Force Laws: " + fLawsDesc);
		_numOfBodies.setText("Number of bodies: "+ Integer.toString(bodies.size()));
		update();
	}

	@Override
	public void onReset(List<Body> bodies, double time, double dt, String fLawsDesc) {
		_currTime.setText("Time: " + Double.toString(time));
		_currLaws.setText("Force Laws: " + fLawsDesc);
		_numOfBodies.setText("Number of bodies: "+ Integer.toString(bodies.size()));
		update();
	}

	@Override
	public void onBodyAdded(List<Body> bodies, Body b) {
		_numOfBodies.setText("Number of bodies: "+ Integer.toString(bodies.size()));
		update();
	}

	@Override
	public void onAdvance(List<Body> bodies, double time) {
		_currTime.setText("Time: " + Double.toString(time));
		update();
	}

	@Override
	public void onDeltaTimeChanged(double dt) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onForceLawsChanged(String fLawsDesc) {
		_currLaws.setText("Force Laws: " + fLawsDesc);
		update();
	}

}
