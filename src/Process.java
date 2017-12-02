import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.JLabel;

public class Process  {
	private int burstTime;
	private int arrivalTime;
	private JLabel display;
	private String name;
	
	Process(String name){
		this.setName(name);
		setBurstTime(setArrivalTime(0));
		display = new JLabel(name);
		display.setBounds(Visualize.processor_x,Visualize.processor_y,Visualize.pro_w, Visualize.pro_h);
		display.setOpaque(true);
		display.setBackground(Color.LIGHT_GRAY);
		display.setBorder(BorderFactory.createLineBorder(Color.black));
	}
	Process() {
		setBurstTime(setArrivalTime(0));
		setName("");
		setDisplay(null);
	}

	Process(String name, int burstTime, int arrivalTime) {
		this.setName(name);
		this.setBurstTime(burstTime);
		this.setArrivalTime(arrivalTime);
		display = new JLabel(name + " : " + String.valueOf(burstTime));
		display.setSize(Visualize.pro_h, Visualize.pro_w);
		display.setOpaque(true);
		display.setBackground(Color.red);
		display.setBorder(BorderFactory.createLineBorder(Color.black));
	}

	public int getBurstTime() {
		return burstTime;
	}

	public void setBurstTime(int burstTime) {
		this.burstTime = burstTime;
	}

	public int getArrivalTime() {
		return arrivalTime;
	}

	public int setArrivalTime(int arrivalTime) {
		this.arrivalTime = arrivalTime;
		return arrivalTime;
	}

	/**
	 * @return the display
	 */
	public JLabel getDisplay() {
		return display;
	}

	/**
	 * @param display
	 *            the display to set
	 */
	public void setDisplay(JLabel display) {
		this.display = display;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

}
