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
import java.util.Set;

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
import javax.swing.text.DefaultCaret;

import com.mans.JobsSearchEngine.model.JobDescription;
import com.mans.JobsSearchEngine.model.Score;
import com.mans.JobsSearchEngine.utility.CleanText;
import com.mans.JobsSearchEngine.utility.WordList;

public class ListOfJobs extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private String url;

	/**
	 * Create the frame.
	 */
	public ListOfJobs(JobDescription[] listOfJobs) {

		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		// height of the task bar
		Insets scnMax = Toolkit.getDefaultToolkit().getScreenInsets(getGraphicsConfiguration());
		int taskBarSize = scnMax.bottom;
		setBounds(0, 0, screenSize.width - getWidth(), screenSize.height - taskBarSize - getHeight());
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);


		JSplitPane splitPane = new JSplitPane();
		splitPane.setBackground(new Color(255, 255, 255));
		splitPane.setOneTouchExpandable(true);
		splitPane.setDividerLocation( (int)( screenSize.width * 0.25 ));
		contentPane.add(splitPane, BorderLayout.CENTER);

		Border border = BorderFactory.createLineBorder(new Color(23,162,184), 3);
		CompoundBorder compoundBorder = BorderFactory.createCompoundBorder(border,
				BorderFactory.createEmptyBorder(8, 8, 8, 8));
		// Left panel

		JPanel panelLeft = new JPanel();
		panelLeft.setLayout(new BorderLayout(4, 4));
		panelLeft.setBackground(new Color(255, 255, 255));
		splitPane.setLeftComponent(panelLeft);

		JLabel lblNewLabel = new JLabel("Job List", SwingConstants.CENTER);
		lblNewLabel.setPreferredSize(new Dimension(panelLeft.getWidth(), 30));
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblNewLabel.setForeground(new Color(23,162,184));
		lblNewLabel.setBorder(compoundBorder);

		JList<JobDescription> list = new JList(listOfJobs);
		list.setSelectionForeground(new Color(23,162,184));
		list.setSelectionBackground(new Color(214, 255, 179));
		list.setFont(new Font("Tahoma", Font.BOLD, 13));
		list.setForeground(Color.BLACK);
		setList(list);
		JScrollPane jScrollPaneJList = new JScrollPane(list);
		jScrollPaneJList.setBackground(new Color(255,255,255));
		jScrollPaneJList.setBorder(compoundBorder);
		panelLeft.add(jScrollPaneJList, BorderLayout.CENTER);
		panelLeft.add(lblNewLabel, BorderLayout.NORTH);

		// Right panel
		JPanel panel = new JPanel();
		panel.setBackground(new Color(255, 255, 255));
		panel.setLayout(new BorderLayout(4, 4));
		splitPane.setRightComponent(panel);

		JLabel lblNewLabel2 = new JLabel("Job Title", SwingConstants.CENTER);
		lblNewLabel2.setBackground(new Color(255, 255, 255));
		lblNewLabel2.setForeground(new Color(23,162,184));
		lblNewLabel2.setBorder(compoundBorder);
		lblNewLabel2.setPreferredSize(new Dimension(panelLeft.getWidth(), 30));
		lblNewLabel2.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblNewLabel2.setPreferredSize(new Dimension(panel.getWidth(), 30));

		JTextPane textPane = new JTextPane();
		textPane.setEditable(false);
		textPane.setContentType("text/html");
		textPane.setMargin(new Insets(10, 10, 10, 10));
		JScrollPane jScrollPane = new JScrollPane(textPane);
		jScrollPane.setBackground(new Color(255,255,255));
		jScrollPane.setBorder(compoundBorder);
		Border borderbutton = BorderFactory.createLineBorder(new Color(23,162,184), 3);
		CompoundBorder CompoundBorderbutton = BorderFactory.createCompoundBorder(borderbutton,
				BorderFactory.createEmptyBorder(15, 15, 15, 15));
		
		JButton btnNewButton = new JButton("Click To Vist The Website");
		btnNewButton.setFont(new Font("Tahoma", Font.BOLD, 13));
		btnNewButton.setBorder(CompoundBorderbutton);
		btnNewButton.setForeground(new Color(23,162,184));
		btnNewButton.setBackground(new Color(214, 255, 179));
		btnNewButton.setPreferredSize(new Dimension(panel.getWidth(), 30));
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					java.awt.Desktop.getDesktop().browse(java.net.URI.create(url));
				} catch (IOException e1) {
					JOptionPane.showMessageDialog(contentPane, "couldn't open link: " + url);
				}
			}
		});

		panel.add(btnNewButton, BorderLayout.SOUTH);
		panel.add(lblNewLabel2, BorderLayout.NORTH);
		panel.add(jScrollPane, BorderLayout.CENTER);

		list.addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent arg0) {
				if (!arg0.getValueIsAdjusting()) {
					JobDescription job = list.getSelectedValue();
					textPane.setText(job.getHtmlJobDes());
					lblNewLabel2.setText(
							CleanText.capitailizeWord(job.getJobTitle() + " - " + job.getCompanyName() + " - " + job.getCity()));
					url = job.getUrl();
					textPane.setCaretPosition(0);
				}
			}
		});

		// set Selection For First Time
		if (listOfJobs.length > 0) {
			url = listOfJobs[0].getUrl();
			textPane.setText(listOfJobs[0].getHtmlJobDes());
			lblNewLabel2.setText(CleanText.capitailizeWord(listOfJobs[0].getJobTitle() + "<br/>\t" + listOfJobs[0].getCity()));
			list.setSelectedIndex(0);
		}

	}

	private void setList(JList<JobDescription> list) {
		list.setCellRenderer(new DefaultListCellRenderer() {
			private static final long serialVersionUID = 1L;

			@Override
			public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected,
					boolean cellHasFocus) {
				Component renderer = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
				if (renderer instanceof JLabel && value instanceof JobDescription) {
					// Here value will be of the Type 'JObD..'
					JobDescription job = (JobDescription) value;
					String jobTitle    = job.getJobTitle();
					String jobCompany  = job.getCompanyName(); 
					((JLabel) renderer).setText( getShortJobTitle( CleanText.capitailizeWord( jobTitle )));
				}
				return renderer;
			}
		});
	}

	protected String getShortJobTitle(String jobTitle) {
		String str = "";
		for (String string : jobTitle.split(" ")) {
			if (str.length() > 27) {
				str += "...";
				return str;
			}
			str += string+" ";
		}
		return str;
	}



}
