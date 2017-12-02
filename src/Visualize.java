import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

import javax.swing.Timer;

import javax.swing.JFrame;
import javax.swing.JLabel;

public class Visualize extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 
	 */
	public JFrame frame;
	private ArrayList<Process> input_pro;
	private ArrayList<Process> waiting_pro;
	private Timer timer;
	private int systemTime;
	private static final int waiting_pro_x = 50;
	private static final int waiting_pro_y = 400;
	public static final int pro_h = 50;
	public static final int pro_w = 100;
	public static final int processor_x = 500;
	public static final int processor_y = 200;
	private static Process serving;
	private static final Process empty_pro = new Process("Empty");
	JLabel processor;
	JLabel system_time_lbl;
	JLabel rem_time_lbl;
	JLabel final_res;
	private static final int delay = 1000; // 1 second delay for the system response
	private Boolean wait_for_view; // if we have something to view a little bit more
	private Boolean end_of_pro;
	private Boolean go_back;
	private int rem_time;
	private HashMap<String, Integer> waiting_time;
	private HashMap<String, Integer> turn_around;

	public Visualize(ArrayList<Process> in, int max_wait) {
		systemTime = 0;
		end_of_pro = go_back = wait_for_view = false; // let's just visualize the initial state of the system.
		rem_time = 5; // input as MX_TIME
		input_pro = in;
		rem_time = max_wait;

		waiting_time = new HashMap<String, Integer>();
		turn_around = new HashMap<String, Integer>();

		frame = new JFrame("CPU SCHADULING");
		frame.setBounds(20, 20, 1000, 600);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		processor = new JLabel("Processor    Remaining Time");
		processor.setBounds(processor_x + pro_w / 5, processor_y - pro_h, 400, 50);
		frame.getContentPane().add(processor);

		// initialize elements
		system_time_lbl = new JLabel("System Time :");
		rem_time_lbl = new JLabel("");
		rem_time_lbl.setBounds(processor_x + pro_w + 5, processor_y + 5, 400, 50);
		frame.getContentPane().add(rem_time_lbl);

		system_time_lbl.setBounds(100, 20, 500, 50);
		frame.getContentPane().add(system_time_lbl);

		waiting_pro = new ArrayList<Process>();

		serving = empty_pro;
		frame.add(serving.getDisplay());

		waiting_time = new HashMap<String, Integer>();

		Collections.sort(input_pro, new Comparator<Process>() {

			@Override
			public int compare(Process o1, Process o2) {
				// TODO Auto-generated method stub
				return o1.getArrivalTime() - o2.getArrivalTime();
			}

		});
		/// this array list is given as input and it must be sorted according to arrival
		/// time.

		ActionListener Action = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub

				if (!wait_for_view)
					systemTime++;
				else
					wait_for_view = false;
				system_time_lbl.setText("System Time : " + systemTime);
				rem_time_lbl.setText(String.valueOf(rem_time));
				if (waiting_pro.size() == 0 && input_pro.size() == 0 && serving == empty_pro) {
					timer.stop();
					show_res();
				}
				if (input_pro.size() > 0 && input_pro.get(0).getArrivalTime() <= systemTime) {
					waiting_pro.add(input_pro.get(0));
					frame.getContentPane().add(input_pro.get(0).getDisplay());
					input_pro.remove(0);
					wait_for_view = true;
				}

				// sort the waiting queue here according to burst time
				Collections.sort(waiting_pro, new Comparator<Process>() {
					@Override
					public int compare(Process o1, Process o2) {
						// TODO Auto-generated method stub
						return o1.getBurstTime() - o2.getBurstTime();
					}

				});
				for (int i = 0; i < waiting_pro.size(); ++i) {
					waiting_pro.get(i).getDisplay().setText(
							waiting_pro.get(i).getName() + " : " + String.valueOf(waiting_pro.get(i).getBurstTime()));
					waiting_pro.get(i).getDisplay().setBounds(waiting_pro_x, waiting_pro_y - (i * pro_h), pro_w, pro_h);
					if (!wait_for_view) {
						if (!waiting_time.containsKey(waiting_pro.get(i).getName())) {
							waiting_time.put(waiting_pro.get(i).getName(), 0);
						} else {
							waiting_time.put(waiting_pro.get(i).getName(),
									waiting_time.get(waiting_pro.get(i).getName()) + 1);
						}
					}
				}
				if (wait_for_view)
					return;
				if (waiting_pro.size() > 0 && serving == empty_pro) {
					serving = waiting_pro.get(0);
					waiting_pro.remove(0);
					frame.getContentPane().remove(empty_pro.getDisplay());
					frame.getContentPane().remove(serving.getDisplay());
					JLabel nxtDisplay = new JLabel(serving.getName() + " : " + serving.getBurstTime());
					nxtDisplay.setOpaque(true);
					nxtDisplay.setBackground(Color.blue);
					nxtDisplay.setBounds(processor_x, processor_y, pro_w, pro_h);
					serving.setDisplay(nxtDisplay);
					frame.getContentPane().add(serving.getDisplay());
				}
				if (serving != empty_pro) {
					serving.getDisplay().setText(serving.getName() + " : " + serving.getBurstTime());
					if (end_of_pro) {
						end_of_pro = false;
						frame.getContentPane().remove(serving.getDisplay());
						frame.getContentPane().add(empty_pro.getDisplay());
						serving = empty_pro;
						wait_for_view = true;
						rem_time = max_wait;
						return;
					} else if (go_back) {
						go_back = false;
						frame.getContentPane().add(empty_pro.getDisplay());
						waiting_pro.add(serving);
						serving = empty_pro;
						wait_for_view = true;
						rem_time = max_wait;
						return;
					} else {
						if (serving.getBurstTime() == 0) {
							turn_around.put(serving.getName(), systemTime - serving.getArrivalTime());
							end_of_pro = true;
							serving.getDisplay().setBackground(Color.GREEN);
							wait_for_view = true;
							return;
						} else if (rem_time == 0) {
							go_back = true;
							serving.getDisplay().setBackground(Color.red);
							wait_for_view = true;
							return;
						}
					}
					rem_time--;
					serving.setBurstTime(serving.getBurstTime() - 1);
				}
				frame.repaint();
			}
		};
		timer = new Timer(delay, Action);
		timer.setInitialDelay(0);
		timer.start();

	}

	private static final int top_y = 30;
	private static final int min_x = 20;
	private JLabel pro_name_lbl, pro_arr_lbl, pro_round_lbl;

	protected void show_res() {
		// TODO Auto-generated method stub
		final_res = new JLabel("FINAL RESULT !");
		final_res.setBounds(min_x, top_y, 500, 200);
		final_res.setFont(new Font(final_res.getFont().getName(), Font.PLAIN, 25));
		final_res.setForeground(Color.red);
		frame.getContentPane().add(final_res);

		pro_name_lbl = new JLabel("Process Name");
		pro_name_lbl.setBounds(min_x, top_y + 100, 100, 50);
		frame.getContentPane().add(pro_name_lbl);

		pro_arr_lbl = new JLabel("Process Waiting Time");
		pro_arr_lbl.setBounds(min_x + 120, top_y + 100, 200, 50);
		frame.getContentPane().add(pro_arr_lbl);

		pro_round_lbl = new JLabel("Process turnaround Time");
		pro_round_lbl.setBounds(min_x + 300, top_y + 100, 200, 50);
		frame.getContentPane().add(pro_round_lbl);

		Integer idx = 1;
		Integer sum_wait = 0, sum_turn = 0;
		for (String name : waiting_time.keySet()) {
			JLabel namei = new JLabel(name);
			namei.setBounds(min_x + 10, top_y + 100 + 50 * idx, 100, 50);
			frame.getContentPane().add(namei);

			JLabel waiti = new JLabel(String.valueOf(waiting_time.get(name)));
			sum_wait += waiting_time.get(name);
			waiti.setBounds(min_x + 150, top_y + 100 + 50 * idx, 100, 50);
			frame.getContentPane().add(waiti);

			JLabel turni = new JLabel(String.valueOf(turn_around.get(name)));
			sum_turn += turn_around.get(name);
			turni.setBounds(min_x + 320, top_y + 100 + 50 * idx, 100, 50);
			frame.getContentPane().add(turni);

			idx++;
		}
		JLabel avg_wait = new JLabel("Average Waiting Time :" + String.valueOf(1.0 * sum_wait / (idx - 1)));
		avg_wait.setBounds(min_x, top_y + 100 + 50 * idx, 300, 50);
		frame.getContentPane().add(avg_wait);

		JLabel avg_turn = new JLabel("Average turnaround Time :" + String.valueOf(1.0 * sum_turn / (idx - 1)));
		avg_turn.setBounds(min_x, top_y + 150 + 50 * idx, 300, 50);
		frame.getContentPane().add(avg_turn);

	}

}
