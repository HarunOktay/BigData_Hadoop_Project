package com.mapreduce;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.mapreduce.dialog.CreateDirectoryDialog;
import com.mapreduce.dialog.ReadDialog;
import com.mapreduce.dialog.RemoveDialog;
import com.mapreduce.dialog.RemoveDirectoryDialog;
import com.mapreduce.dialog.WriteDialog;
import com.mapreduce.dialog.jobs.AverageFlightDelayDialog;
import com.mapreduce.dialog.jobs.MinMaxFlightDelayDialog;
import com.mapreduce.dialog.jobs.StdDevFlightDelayDialog;
import com.mapreduce.dialog.jobs.TotalFlightsByAirlineDialog;
import com.mapreduce.dialog.jobs.DailyCountOfDiffTailNumberDialog;
import com.mapreduce.util.PrintStreamCapturer;
import com.mapreduce.util.ProcessHandler;
import com.mapreduce.util.ServiceStatus;

import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.awt.event.ActionEvent;
import javax.swing.JComboBox;

public class Main extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txtOn;
	private JTextField txtSlave1State;
	private JTextField txtSlave2State;
	private JTextField textFieldHadoop;
	private JTextField textFieldYarn;

	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Main window = new Main();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Main() {
		initialize();
	}

	// initialize the window
	private void initialize(){
		frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setBounds(100, 100, 1082, 606);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		frame.setContentPane(contentPane);
		contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.X_AXIS));
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		contentPane.add(tabbedPane);
		
		JPanel panel = new JPanel();
		tabbedPane.addTab("Hadoop", null, panel, null);
		panel.setLayout(null);
		

		JButton btnCreateDirectory = new JButton("Create Directory");
		btnCreateDirectory.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new CreateDirectoryDialog();
			}
		});
		JButton btnRemoveFile = new JButton("Remove File");
		btnRemoveFile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new RemoveDialog();
			}
		});
		JButton btnUploadFile = new JButton("Upload File");
		btnUploadFile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new WriteDialog();
			}
		});
		JButton btnListFile = new JButton("List Files");
		btnListFile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					ReadWrite.listFiles("/");
				} catch (IllegalArgumentException | IOException e1) {
					e1.printStackTrace();
				}
			}
		});
		JButton btnReadFile = new JButton("Read File");
		JButton btnDeleteDirectory = new JButton("Delete Directory");
		
		JTextArea textArea = new JTextArea();
		textArea.setEditable(false);
		textArea.setLineWrap(true);
		// panel_3.add(textArea);
		System.setOut(new PrintStreamCapturer(textArea, System.out));
		System.setErr(new PrintStreamCapturer(textArea, System.err, "[ERROR] "));
		
		JScrollPane scrollPane = new JScrollPane(textArea);
		scrollPane.setBounds(22, 12, 792, 508);
		panel.add(scrollPane);
		
		JLabel lblHadoop = new JLabel("Hadoop:");
		lblHadoop.setBounds(832, 39, 70, 15);
		panel.add(lblHadoop);
		
		textFieldHadoop = new JTextField();
		textFieldHadoop.setBounds(927, 37, 114, 19);
		panel.add(textFieldHadoop);
		textFieldHadoop.setColumns(10);
		textFieldHadoop.setText(ServiceStatus.hadoop() ? "on" : "off");
		
		JLabel lblYarn = new JLabel("Yarn:");
		lblYarn.setBounds(832, 66, 70, 15);
		panel.add(lblYarn);
		
		textFieldYarn = new JTextField();
		textFieldYarn.setColumns(10);
		textFieldYarn.setBounds(927, 64, 114, 19);
		panel.add(textFieldYarn);
		textFieldYarn.setText(ServiceStatus.yarn() ? "on" : "off");
		
		JComboBox<Job> comboBox = new JComboBox<Job>(Job.values());
		comboBox.setBounds(826, 115, 215, 24);
		panel.add(comboBox);
		
		JButton btnRun = new JButton("Run");
		btnRun.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e){
				Job job = (Job) comboBox.getSelectedItem();
				switch (job) {
					case AverageFlightDelay:
						System.out.println("Average Flight Delay clicked");
						new AverageFlightDelayDialog();
						break;
					case MinMaxFlightDelay:
						System.out.println("Min/Max Flight Delay clicked");
						new MinMaxFlightDelayDialog();
						break;
					case StdDevFlightDelay:
						System.out.println("Std Dev Flight Delay clicked");
						new StdDevFlightDelayDialog();
						break;
					case TotalFlightsByAirline:
						System.out.println("Total Flights By Airline clicked");
						new TotalFlightsByAirlineDialog();
						break;
					case DailyCountOfDiffTailNumber:
						System.out.println("Daily Count Of Different Tail Numbers clicked");
						new DailyCountOfDiffTailNumberDialog();
						break;
				}
			}
		});
		btnRun.setBounds(877, 151, 117, 25);
		panel.add(btnRun);
		

		btnReadFile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new ReadDialog();
			}
		});
		btnReadFile.setBounds(826, 202, 215, 25);
		panel.add(btnReadFile);
		

		btnListFile.setBounds(826, 239, 215, 25);
		panel.add(btnListFile);
		

		btnUploadFile.setBounds(826, 276, 215, 25);
		panel.add(btnUploadFile);

		btnRemoveFile.setBounds(826, 313, 215, 25);
		panel.add(btnRemoveFile);
		

		btnCreateDirectory.setBounds(826, 350, 215, 25);
		panel.add(btnCreateDirectory);
		

		btnDeleteDirectory.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new RemoveDirectoryDialog();
				
			}
		});
		btnDeleteDirectory.setBounds(826, 387, 215, 25);
		panel.add(btnDeleteDirectory);
		
		JPanel panel_1 = new JPanel();
		tabbedPane.addTab("VM MAchines", null, panel_1, null);
		panel_1.setLayout(null);
		
		JLabel lblMasterNode = new JLabel("Master Node");
		lblMasterNode.setBounds(234, 53, 107, 15);
		panel_1.add(lblMasterNode);
		
		JLabel lblSlaveNode = new JLabel("Slave1 Node");
		lblSlaveNode.setBounds(454, 53, 107, 15);
		panel_1.add(lblSlaveNode);
		
		JLabel lblSlaveNode_2 = new JLabel("Slave2 Node");
		lblSlaveNode_2.setBounds(678, 53, 107, 15);
		panel_1.add(lblSlaveNode_2);
		
		txtOn = new JTextField();
		txtOn.setText("ON");
		txtOn.setBounds(220, 80, 114, 19);
		panel_1.add(txtOn);
		txtOn.setColumns(10);
		
		txtSlave1State = new JTextField();
		txtSlave1State.setText("Unknown");
		txtSlave1State.setColumns(10);
		txtSlave1State.setBounds(447, 80, 114, 19);
		panel_1.add(txtSlave1State);
		
		txtSlave2State = new JTextField();
		txtSlave2State.setText("Unknown");
		txtSlave2State.setColumns(10);
		txtSlave2State.setBounds(671, 80, 114, 19);
		panel_1.add(txtSlave2State);
		
		JButton btnCheckS1 = new JButton("Check S1");
		btnCheckS1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					boolean result = ProcessHandler.pingVM("192.168.237.130", 5);
					if(result) {
						txtSlave1State.setText("Running..");
					}else {
						txtSlave1State.setText("OFF");
					}
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		});
		btnCheckS1.setBounds(444, 111, 117, 25);
		panel_1.add(btnCheckS1);
		
		JButton btnCheckS2 = new JButton("Check S2");
		btnCheckS2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					boolean result = ProcessHandler.pingVM("192.168.237.131", 5);
					if(result) {
						txtSlave2State.setText("Running..");
					}else {
						txtSlave2State.setText("OFF");
					}
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		});
		btnCheckS2.setBounds(671, 111, 117, 25);
		panel_1.add(btnCheckS2);

	}
	
	public enum Job {
		AverageFlightDelay,
		MinMaxFlightDelay,
		StdDevFlightDelay,
		TotalFlightsByAirline,
		DailyCountOfDiffTailNumber;
	}
}
