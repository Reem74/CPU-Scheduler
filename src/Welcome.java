import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class Welcome {

	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Welcome window = new Welcome();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Welcome() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame("Let's start the journey!");
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		JLabel lbl = new JLabel("Enter nubmer of proccesses to be simulated");
		lbl.setBounds(10, 20, 400, 20);
		frame.getContentPane().add(lbl);

		JTextField number_of_pro = new JTextField();
		number_of_pro.setBounds(150, 100, 100, 50);
		frame.getContentPane().add(number_of_pro);

		JButton btn = new JButton("OK");
		btn.setBounds(150, 200, 100, 50);
		frame.getContentPane().add(btn);

		btn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				getData get = new getData(Integer.parseInt(number_of_pro.getText()));
				get.frame.setVisible(true);
				frame.setVisible(false);
			}

		});
	}

}
