package com.mans.JobsSearchEngine.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;

import com.mans.JobsSearchEngine.model.JobDescription;
import com.mans.JobsSearchEngine.threads.Consumer;
import com.mans.JobsSearchEngine.threads.Producer;
import com.mans.JobsSearchEngine.utility.WordList;

public class MainWindow {

	private JFrame frame;
	private JTextField jobTitle;
	private JTextArea skils;
	private JLabel lblSearchKeywords;
	private JLabel label;
	private JLabel lblNewLabel;
	private JLabel labelJob;
	private JLabel labelSkilsIcon;
	private JTextField jobLocation;
	private JLabel lblYourYearsOf;
	private JLabel lblExperience;
	private JLabel lblJobLocations;
	private JTextField experience;
	private JLabel labelJobIcon;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainWindow window = new MainWindow();
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
	public MainWindow() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.getContentPane().setBackground(new Color(240, 240, 240));
		frame.setBounds(100, 100, 527, 481);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		frame.setResizable(false);
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		frame.setLocation((screenSize.width - frame.getWidth()) / 2, (screenSize.height - frame.getHeight()) / 2);

		Border border = BorderFactory.createLineBorder(Color.BLACK);
		CompoundBorder compoundBorder = BorderFactory.createCompoundBorder(border,
				BorderFactory.createEmptyBorder(10, 10, 10, 10));
		
		
		////Label

		jobTitle = new JTextField();
		jobTitle.setFont(new Font("Times New Roman", Font.BOLD, 12));
		jobTitle.setBounds(129, 79, 267, 38);
		jobTitle.setForeground(Color.black);
		jobTitle.setBorder(compoundBorder);
		frame.getContentPane().add(jobTitle);

		jobLocation = new JTextField();
		jobLocation.setFont(new Font("Times New Roman", Font.BOLD, 12));
		jobLocation.setBounds(129, 138, 267, 38);
		jobLocation.setBorder(compoundBorder);
		jobLocation.setForeground(Color.black);
		
		frame.getContentPane().add(jobLocation);

		experience = new JTextField();
		experience.setFont(new Font("Times New Roman", Font.BOLD, 12));
		experience.setBounds(129, 198, 267, 38);
		experience.setBorder(compoundBorder);
		frame.getContentPane().add(experience);

		skils = new JTextArea();
		skils.setWrapStyleWord(true);
		skils.setLineWrap(true);
		skils.setFont((new Font("Tahoma", Font.BOLD, 9)));
		skils.setMargin(new Insets(10, 10, 10, 10));
		JScrollPane jScrollPane = new JScrollPane(skils);
		jScrollPane.setBounds(129, 258, 267, 106);
		CompoundBorder compoundBorder2 = BorderFactory.createCompoundBorder(border,
				BorderFactory.createEmptyBorder(1, 1, 1, 1));
		jScrollPane.setBorder(compoundBorder2);
		frame.getContentPane().add(jScrollPane);

		lblSearchKeywords = new JLabel("Your Skils");
		lblSearchKeywords.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblSearchKeywords.setBounds(36, 295, 93, 28);
		frame.getContentPane().add(lblSearchKeywords);

		label = new JLabel("Your Next Job");
		label.setFont(new Font("Tahoma", Font.BOLD, 11));
		label.setBounds(36, 84, 93, 28);
		frame.getContentPane().add(label);
		
		lblYourYearsOf = new JLabel("Years of");
		lblYourYearsOf.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblYourYearsOf.setBounds(36, 207, 109, 14);
		frame.getContentPane().add(lblYourYearsOf);

		lblExperience = new JLabel("Experience");
		lblExperience.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblExperience.setBounds(56, 218, 73, 14);
		frame.getContentPane().add(lblExperience);
		
		lblJobLocations = new JLabel("Job Location");
		lblJobLocations.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblJobLocations.setBounds(36, 143, 93, 28);
		frame.getContentPane().add(lblJobLocations);
		
		ImageIcon imageWebsite = new ImageIcon(getClass().getResource("/all.png"));
		lblNewLabel = new JLabel(imageWebsite);
		lblNewLabel.setBounds(66, 11, 376, 48);
		frame.getContentPane().add(lblNewLabel);
		
	///////ICon 

		ImageIcon imageQuestion = new ImageIcon(getClass().getResource("/question.png"));

		labelJobIcon = new JLabel(imageQuestion);
		labelJobIcon.setBounds(396, 74, 46, 38);
		labelJobIcon.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				JOptionPane.showMessageDialog(null, " You can search for more than one job title\n"
						+ "just separate jobs title by comma\n"
						+ "E.g. Java Developer, C# Developer");
			}

		});
		frame.getContentPane().add(labelJobIcon);
		
		JLabel labelLocationsIcon = new JLabel(imageQuestion);
		labelLocationsIcon.setBounds(396, 133, 46, 38);
		labelLocationsIcon.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				JOptionPane.showMessageDialog(null, "You can search for more than one job location\n"
						+ "just separate jobs location by comma\n"
						+ "E.g. Montreal,Toronto");
			}

		});
		frame.getContentPane().add(labelLocationsIcon);
		
		labelSkilsIcon = new JLabel(imageQuestion);
		labelSkilsIcon.setBounds(396, 279, 46, 38);
		labelSkilsIcon.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				JOptionPane.showMessageDialog(null, " your Skills will be used to score each job being retrieved\n"
						+ "Then the Jobs will be shown in decreasing order\n"
						+ "\nTips:\n\n"
						+ "• Skills are separated by space.\n"
						+ "• Include skill famous synonymous e.g JavaEE & EE \n"
						+ "	   has the same meaning but you should include both.\n"
						+ "• To improve the accurac Be specific. e.g do not include\n"
						+ "   skills that are required by everone in you industry\n"
						+ "	   For example, HTML shouldn't be include for a web developer.");
			}
		});
		frame.getContentPane().add(labelSkilsIcon);
		

		
	/// Button 	
		JButton btnNewButton = new JButton("Search");
		btnNewButton.setFont(new Font("Tahoma", Font.BOLD, 13));
		btnNewButton.setForeground(new Color(255, 255, 255));
		btnNewButton.setBackground(new Color(66, 75, 244));
		btnNewButton.setBounds(209, 395, 89, 34);

		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int years;
				try {
					years = Integer.parseInt(experience.getText());
					if (years > 40 || years < 0)
						throw new Exception();
				} catch (Exception ex) {
					JOptionPane.showMessageDialog(frame, "Years of Experience must be a number between 0 and 40");
					return;
				}

				if (jobTitle.getText().trim().equals("") || jobLocation.getText().trim().equals("")) {
					JOptionPane.showMessageDialog(frame, "job Title Or job Location cannot be empty");
					return;
				}

				frame.setVisible(false);
				Loading loading = new Loading();
				loading.setVisible(true);
				new Thread(() -> {
					List<String> jobTitleLsit = getList(jobTitle.getText());
					List<String> cities = getList(jobLocation.getText());
					String skilsToLookFor = skils.getText();
					WordList.getInstance().writeToFile(skilsToLookFor, jobTitleLsit, cities, experience.getText());

					BlockingQueue<JobDescription> queue = new LinkedBlockingQueue<>();
					new Producer(cities, jobTitleLsit, queue).run();
					Consumer consumer = new Consumer(queue, loading);
					consumer.run();
				}).start();

			}
		});

		frame.getContentPane().add(btnNewButton);
		jobTitle.setText(getJobsTitleFromFile());
		jobLocation.setText(getJobsLocationFromFile());
		skils.setText(getSkilsFromFile());
		experience.setText(getJobsExperienceFromFile());
		

	}

	private String getSkilsFromFile() {
		String str = "";
		for (String city : WordList.getInstance().getJobKeywords())
			str += city + " ";
		return str;
	}

	private String getJobsExperienceFromFile() {
		return WordList.getInstance().getRememberMeExperience();
	}

	private String getJobsLocationFromFile() {
		String str = "";
		int i = 0;
		for (String city : WordList.getInstance().getRememberMeJobsLocation()) {
			str += city;
			if (WordList.getInstance().getJobKeywords().size() < (i + 1))
				str += ", ";
			i++;
		}
		return str;
	}

	private String getJobsTitleFromFile() {
		String str = "";
		int i = 0;
		for (String title : WordList.getInstance().getRememberMeJobsTitle()) {
			str += title;
			if (WordList.getInstance().getJobKeywords().size() < (i + 1))
				str += ", ";
			i++;
		}
		return str;
	}

	protected List<String> getList(String text) {
		List<String> list = new ArrayList<String>();
		if (text.contains(",")) {
			for (String jobInlist : text.split(","))
				list.add(jobInlist);
		} else {
			list.add(text.trim());
		}
		return list;
	}
}
