package com.mans.JobsSearchEngine.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import javax.swing.BorderFactory;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextPane;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import com.mans.JobsSearchEngine.model.job.JobDescription;
import com.mans.JobsSearchEngine.utility.CleanText;

public class ListOfJobsView extends JFrame {

	private static final long serialVersionUID = 1L;

	private JPanel contentPane;

	private Dimension screenSize;

	private JSplitPane splitPane;

	private JobDescription[] listOfJobs;

	private String url;

	private JPanel leftPanel;

	private JPanel rightPanel;

	private JList<JobDescription> jobsList;

	private JTextPane jobDes;

	private JLabel jobTitleLabel;

	public ListOfJobsView(JobDescription[] listOfJobs) {
		this.listOfJobs = listOfJobs;
		screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		splitPane = new JSplitPane();

		initFrame(screenSize);
		initSplitPane();
		initRightPanel();
		initLeftPanel();
		initViewForFirstTime();
	}

	private void initFrame(Dimension screenSize) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Insets scnMax = Toolkit.getDefaultToolkit().getScreenInsets(getGraphicsConfiguration());
		int taskBarSize = scnMax.bottom;
		setBounds(0, 0, screenSize.width - getWidth(), screenSize.height - taskBarSize - getHeight());
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
	}

	private void initSplitPane() {
		splitPane.setBackground(new Color(255, 255, 255));
		splitPane.setOneTouchExpandable(true);
		splitPane.setDividerLocation((int) (screenSize.width * 0.25));
		contentPane.add(splitPane, BorderLayout.CENTER);
	}

	private void initRightPanel() {
		rightPanel = new JPanel();
		rightPanel.setBackground(new Color(255, 255, 255));
		rightPanel.setLayout(new BorderLayout(4, 4));

		JLabel jobTitleLabel = initJobTitleLabel();
		rightPanel.add(jobTitleLabel, BorderLayout.NORTH);

		JScrollPane jobDescriptionScrollPane = initJobDescriptionScrollPane();
		rightPanel.add(jobDescriptionScrollPane, BorderLayout.CENTER);

		JButton visitButton = initVisitWebsiteButton();
		rightPanel.add(visitButton, BorderLayout.SOUTH);

		splitPane.setRightComponent(rightPanel);
	}

	private JLabel initJobTitleLabel() {
		jobTitleLabel = new JLabel("Job Title", SwingConstants.CENTER);
		jobTitleLabel.setBackground(new Color(255, 255, 255));
		jobTitleLabel.setForeground(new Color(23, 162, 184));
		jobTitleLabel.setBorder(getCompoundBorder());
		jobTitleLabel.setFont(new Font("Tahoma", Font.BOLD, 13));
		jobTitleLabel.setPreferredSize(new Dimension(rightPanel.getWidth(), 30));
		return jobTitleLabel;
	}

	private JScrollPane initJobDescriptionScrollPane() {
		jobDes = new JTextPane();
		jobDes.setEditable(false);
		jobDes.setContentType("text/html");
		jobDes.setMargin(new Insets(10, 10, 10, 10));
		JScrollPane jScrollPane = new JScrollPane(jobDes);
		jScrollPane.setBackground(new Color(255, 255, 255));
		jScrollPane.setBorder(getCompoundBorder());

		return jScrollPane;
	}

	private JButton initVisitWebsiteButton() {
		Border borderbutton = BorderFactory.createLineBorder(new Color(23, 162, 184), 3);
		CompoundBorder CompoundBorderbutton = BorderFactory.createCompoundBorder(borderbutton,
				BorderFactory.createEmptyBorder(15, 15, 15, 15));

		JButton button = new JButton("Click To Vist The Website");
		button.setFont(new Font("Tahoma", Font.BOLD, 13));
		button.setBorder(CompoundBorderbutton);
		button.setForeground(new Color(23, 162, 184));
		button.setBackground(new Color(214, 255, 179));
		button.setPreferredSize(new Dimension(rightPanel.getWidth(), 30));
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					java.awt.Desktop.getDesktop().browse(java.net.URI.create(url));
				} catch (IOException e1) {
					JOptionPane.showMessageDialog(contentPane, "couldn't open link: " + url);
				}
			}
		});

		return button;
	}

	private CompoundBorder getCompoundBorder() {
		Border border = BorderFactory.createLineBorder(new Color(23, 162, 184), 3);
		CompoundBorder compoundBorder = BorderFactory.createCompoundBorder(border,
				BorderFactory.createEmptyBorder(8, 8, 8, 8));
		return compoundBorder;
	}

	private void initLeftPanel() {
		CompoundBorder compoundBorder = getCompoundBorder();

		leftPanel = new JPanel();
		leftPanel.setLayout(new BorderLayout(4, 4));
		leftPanel.setBackground(new Color(255, 255, 255));

		JLabel jobListLabel = initJobListLabel(compoundBorder, leftPanel);
		leftPanel.add(jobListLabel, BorderLayout.NORTH);

		JScrollPane jScrollPaneJList = initScrollPane(compoundBorder);
		leftPanel.add(jScrollPaneJList, BorderLayout.CENTER);

		splitPane.setLeftComponent(leftPanel);
	}

	private JLabel initJobListLabel(CompoundBorder compoundBorder, JPanel leftPanel) {
		JLabel jobListLabel = new JLabel("Job List", SwingConstants.CENTER);
		jobListLabel.setPreferredSize(new Dimension(leftPanel.getWidth(), 30));
		jobListLabel.setFont(new Font("Tahoma", Font.BOLD, 13));
		jobListLabel.setForeground(new Color(23, 162, 184));
		jobListLabel.setBorder(compoundBorder);
		return jobListLabel;
	}

	private JScrollPane initScrollPane(CompoundBorder compoundBorder) {
		jobsList = new JList<JobDescription>(listOfJobs);
		jobsList.setSelectionForeground(new Color(23, 162, 184));
		jobsList.setSelectionBackground(new Color(214, 255, 179));
		jobsList.setFont(new Font("Tahoma", Font.BOLD, 13));
		jobsList.setForeground(Color.BLACK);

		setCellRenderer(jobsList);
		setListListener(jobsList);

		JScrollPane jScrollPaneJList = new JScrollPane(jobsList);
		jScrollPaneJList.setBackground(new Color(255, 255, 255));
		jScrollPaneJList.setBorder(compoundBorder);
		return jScrollPaneJList;
	}

	private void setCellRenderer(JList<JobDescription> list) {
		list.setCellRenderer(new DefaultListCellRenderer() {
			private static final long serialVersionUID = 1L;

			@Override
			public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected,
					boolean cellHasFocus) {
				Component renderer = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
				if (renderer instanceof JLabel && value instanceof JobDescription) {
					// Here value will be of the Type 'JObD..'
					JobDescription job = (JobDescription) value;
					String jobTitle = job.getJobTitle();
					((JLabel) renderer).setText(getShortJobTitle(CleanText.capitailizeWord(jobTitle)));
				}
				return renderer;
			}
		});
	}

	private void setListListener(JList<JobDescription> list) {
		list.addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent arg0) {
				if (!arg0.getValueIsAdjusting()) {
					JobDescription job = list.getSelectedValue();
					jobDes.setText(job.getJobDesInHtmlFormat());
					jobTitleLabel.setText(CleanText
							.capitailizeWord(job.getJobTitle() + " - " + job.getCompanyName() + " - " + job.getCity()));
					url = job.getUrl();
					jobDes.setCaretPosition(0);
				}
			}
		});
	}

	private void initViewForFirstTime() {
		if (listOfJobs.length > 0) {
			url = listOfJobs[0].getUrl();
			jobDes.setText(listOfJobs[0].getJobDesInHtmlFormat());
			jobTitleLabel.setText(
					CleanText.capitailizeWord(listOfJobs[0].getJobTitle() + "<br/>\t" + listOfJobs[0].getCity()));
			jobsList.setSelectedIndex(0);
		}
	}

	protected String getShortJobTitle(String jobTitle) {
		String str = "";

		for (String string : jobTitle.split(" ")) {
			if (str.length() > 27) {
				str += "...";
				return str;
			}
			str += string + " ";
		}
		return str;
	}
}
