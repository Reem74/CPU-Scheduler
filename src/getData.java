
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class getData {

	public JFrame frame;
	private int n;

	/**
	 * Launch the application.
	 */

	/**
	 * Create the application.
	 */
	public getData(int number_of_pro) {
		n = number_of_pro;
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private static final int name_x = 100;
	private static final int burst_x = 300;
	private static final int arrival_x = 500;
	private static final int max_x = 800;
	private static final int top_y = 50;
	private static final int w = 200;
	private static final int h = 50;
	private static final int submit_y = 500;
	private JLabel name;
	private JLabel burst;
	private JLabel arrival;
	private JTextField max;
	private JButton submit;

	private void initialize() {
		frame = new JFrame("Getting Data");
		frame.setBounds(100, 100, 1100, 600);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		name = new JLabel("Process Name");
		name.setBounds(name_x, top_y, w, h);
		frame.getContentPane().add(name);

		burst = new JLabel("Burst Time");
		burst.setBounds(burst_x, top_y, w, h);
		frame.getContentPane().add(burst);

		arrival = new JLabel("Arrival Time");
		arrival.setBounds(arrival_x, top_y, w, h);
		frame.getContentPane().add(arrival);

		max = new JTextField("Max waiting time");
		max.setBounds(max_x, top_y, w, h);
		frame.getContentPane().add(max);

		submit = new JButton("Simulate!");
		submit.setBounds(max_x, submit_y, w, h);
		frame.getContentPane().add(submit);

		ArrayList<JTextField> names = new ArrayList<JTextField>();
		ArrayList<JTextField> burst = new ArrayList<JTextField>();
		ArrayList<JTextField> arrival = new ArrayList<JTextField>();
		for (int i = 1; i <= n; ++i) {
			JTextField namei = new JTextField();
			JTextField bursti = new JTextField();
			JTextField arrivali = new JTextField();
			namei.setBounds(name_x, top_y + h * i, w, h);
			bursti.setBounds(burst_x, top_y + h * i, w, h);
			arrivali.setBounds(arrival_x, top_y + h * i, w, h);
			frame.getContentPane().add(namei);
			frame.getContentPane().add(bursti);
			frame.getContentPane().add(arrivali);
			names.add(namei);
			burst.add(bursti);
			arrival.add(arrivali);
		}

		submit.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				ArrayList<Process> pro = new ArrayList<Process>();
				for (int i = 0; i < n; ++i) {
					pro.add(new Process(names.get(i).getText(), Integer.parseInt(burst.get(i).getText()),
							Integer.parseInt(arrival.get(i).getText())));
				}
				Visualize vis = new Visualize(pro,Integer.parseInt(max.getText()));
				vis.frame.setVisible(true);
				frame.setVisible(false);
			}
		});

	}

}
