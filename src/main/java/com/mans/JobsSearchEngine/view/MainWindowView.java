package com.mans.JobsSearchEngine.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
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

public class MainWindowView {

	private JFrame frame;

	private JTextField jobTitleTextField;

	private JLabel jobTitleLabel;

	private JTextArea userSkillsTextArea;

	private JLabel userSkillsLabel;

	private JTextField jobLocationTextField;

	private JLabel jobLocationsLabel;

	private JLabel yearsLabel;

	private JLabel experienceLable;

	private JTextField experienceTextField;

	private JButton searchButton;

	private JLabel userSkillsIcon;

	private JLabel websitesIcon;

	private JLabel questionIcon;

	private CompoundBorder compoundBorder;

	private Border border;

	public MainWindowView() {
		border = BorderFactory.createLineBorder(new Color(23, 162, 184), 3);
		compoundBorder = BorderFactory.createCompoundBorder(border, BorderFactory.createEmptyBorder(8, 8, 8, 8));
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		initFrame();
		intiJobTitle();
		intiJobLocation();
		initExperience();
		initUserSkill();
		initSearchButton();
		initIcon();
	}

	private void initFrame() {
		frame = new JFrame();
		frame.getContentPane().setBackground(new Color(240, 240, 240));
		frame.setBounds(100, 100, 527, 481);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		frame.setResizable(false);
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		frame.setLocation((screenSize.width - frame.getWidth()) / 2, (screenSize.height - frame.getHeight()) / 2);
		frame.setVisible(true);
	}

	private void intiJobTitle() {
		jobTitleTextField = new JTextField();
		jobTitleTextField.setFont(new Font("Times New Roman", Font.BOLD, 12));
		jobTitleTextField.setBounds(129, 79, 267, 38);
		jobTitleTextField.setForeground(Color.black);
		jobTitleTextField.setBorder(compoundBorder);

		jobTitleLabel = new JLabel("Your Next Job");
		jobTitleLabel.setFont(new Font("Tahoma", Font.BOLD, 11));
		jobTitleLabel.setBounds(36, 84, 93, 28);

		frame.getContentPane().add(jobTitleTextField);
		frame.getContentPane().add(jobTitleLabel);
	}

	private void intiJobLocation() {
		jobLocationTextField = new JTextField();
		jobLocationTextField.setFont(new Font("Times New Roman", Font.BOLD, 12));
		jobLocationTextField.setBounds(129, 138, 267, 38);
		jobLocationTextField.setBorder(compoundBorder);
		jobLocationTextField.setForeground(Color.black);

		jobLocationsLabel = new JLabel("Job Location");
		jobLocationsLabel.setFont(new Font("Tahoma", Font.BOLD, 11));
		jobLocationsLabel.setBounds(36, 143, 93, 28);

		frame.getContentPane().add(jobLocationsLabel);
		frame.getContentPane().add(jobLocationTextField);
	}

	private void initUserSkill() {
		userSkillsTextArea = new JTextArea();
		userSkillsTextArea.setWrapStyleWord(true);
		userSkillsTextArea.setLineWrap(true);
		userSkillsTextArea.setFont(new Font("Tahoma", Font.BOLD, 11));
		userSkillsTextArea.setMargin(new Insets(10, 10, 10, 10));

		JScrollPane jScrollPane = new JScrollPane(userSkillsTextArea);
		jScrollPane.setBounds(129, 258, 267, 106);

		CompoundBorder compoundBorder = BorderFactory.createCompoundBorder(border,
				BorderFactory.createEmptyBorder(1, 1, 1, 1));
		jScrollPane.setBorder(compoundBorder);

		userSkillsLabel = new JLabel("Your Skills");
		userSkillsLabel.setFont(new Font("Tahoma", Font.BOLD, 11));
		userSkillsLabel.setBounds(36, 295, 93, 28);

		frame.getContentPane().add(jScrollPane);
		frame.getContentPane().add(userSkillsLabel);
	}

	private void initExperience() {
		yearsLabel = new JLabel("Years of");
		yearsLabel.setFont(new Font("Tahoma", Font.BOLD, 11));
		yearsLabel.setBounds(36, 207, 109, 14);

		experienceLable = new JLabel("Experience");
		experienceLable.setFont(new Font("Tahoma", Font.BOLD, 11));
		experienceLable.setBounds(56, 218, 73, 14);

		experienceTextField = new JTextField();
		experienceTextField.setFont(new Font("Times New Roman", Font.BOLD, 12));
		experienceTextField.setBounds(129, 198, 267, 38);
		experienceTextField.setBorder(compoundBorder);

		frame.getContentPane().add(experienceLable);
		frame.getContentPane().add(yearsLabel);
		frame.getContentPane().add(experienceTextField);
	}

	private void initSearchButton() {
		Border borderbutton = BorderFactory.createLineBorder(new Color(23, 162, 184), 3);
		CompoundBorder CompoundBorderbutton = BorderFactory.createCompoundBorder(borderbutton,
				BorderFactory.createEmptyBorder(15, 15, 15, 15));

		searchButton = new JButton();
		searchButton.setText("Search");
		searchButton.setFont(new Font("Tahoma", Font.BOLD, 13));
		searchButton.setBorder(CompoundBorderbutton);
		searchButton.setForeground(new Color(23, 162, 184));
		searchButton.setBackground(new Color(255, 255, 255));
		searchButton.setBounds(209, 395, 89, 34);

		frame.getContentPane().add(searchButton);
	}

	private void initIcon() {
		ImageIcon imageWebsite = new ImageIcon(getClass().getResource("/all.png"));
		websitesIcon = new JLabel(imageWebsite);
		websitesIcon.setBounds(66, 11, 376, 48);

		ImageIcon imageQuestion = new ImageIcon(getClass().getResource("/question.png"));
		questionIcon = new JLabel(imageQuestion);
		questionIcon.setBounds(396, 74, 46, 38);
		questionIcon.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				JOptionPane.showMessageDialog(null, getJobTitleIconMessage());
			}
		});

		JLabel jobLocationsIcon = new JLabel(imageQuestion);
		jobLocationsIcon.setBounds(396, 133, 46, 38);
		jobLocationsIcon.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				JOptionPane.showMessageDialog(null, getJobLocationIconMessage());
			}
		});

		userSkillsIcon = new JLabel(imageQuestion);
		userSkillsIcon.setBounds(396, 279, 46, 38);
		userSkillsIcon.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				JOptionPane.showMessageDialog(null, getUserSkillsIconMessage());
			}
		});

		frame.getContentPane().add(jobLocationsIcon);
		frame.getContentPane().add(questionIcon);
		frame.getContentPane().add(websitesIcon);
		frame.getContentPane().add(userSkillsIcon);
	}

	private String getJobTitleIconMessage() {
		return " You can search for more than one job title\n"
				+ "just separate jobs title by comma\n" + "E.g. Java Developer, C# Developer";
	}
	
	private String getJobLocationIconMessage() {
		return "You can search for more than one job location\n" + "just separate jobs location by comma\n"
				+ "E.g. Montreal,Toronto";
	}
	
	private String getUserSkillsIconMessage() {
		return " your Skills will be used to score each job being retrieved\n"
				+ "Then Jobs will be shown in decreasing order\n" + "\nTips:\n\n" + "• Skills are separated by cooma.\n"
				+ "• Include skill famous synonymous e.g JavaEE & EE \n"
				+ "	   has the same meaning but you should include both.\n"
				+ "• To improve the accuracy Be specific. e.g do not include\n"
				+ "   skills that are required by everyone in your industry\n"
				+ "	   For example, HTML shouldn't be included for a web develope.";
	}

	public JTextField getJobTitle() {
		return jobTitleTextField;
	}

	public JTextField getJobLocation() {
		return jobLocationTextField;
	}

	public JFrame getFrame() {
		return frame;
	}

	public JButton getSearchButton() {
		return searchButton;
	}

	public void setJobTitle(JTextField jobTitle) {
		this.jobTitleTextField = jobTitle;
	}

	public JTextField getExperience() {
		return experienceTextField;
	}

	public JTextArea getUserSkills() {
		return userSkillsTextArea;
	}

	public void setUserSkills(JTextArea skills) {
		this.userSkillsTextArea = skills;
	}

	public void setJobLocation(JTextField jobLocation) {
		this.jobLocationTextField = jobLocation;
	}

}
