package com.mans.JobsSearchEngineI.view;

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
import javax.swing.text.DefaultCaret;

import com.mans.JobsSearchEngine.model.JobDescription;

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
		//height of the task bar
		Insets scnMax = Toolkit.getDefaultToolkit().getScreenInsets(getGraphicsConfiguration());
		int taskBarSize = scnMax.bottom;
		setBounds(0, 0,screenSize.width - getWidth(), screenSize.height - taskBarSize - getHeight());
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		Border border = BorderFactory.createLineBorder(Color.BLACK);
		CompoundBorder compoundBorder = BorderFactory.createCompoundBorder(border,
	            BorderFactory.createEmptyBorder(10, 10, 10, 10));
		
		JSplitPane splitPane = new JSplitPane();
		splitPane.setResizeWeight(0.3);
		splitPane.setOneTouchExpandable(true);
		contentPane.add(splitPane, BorderLayout.CENTER);
		
		// Left panel 	
		
		JPanel panelLeft = new JPanel();
		panelLeft.setPreferredSize(new Dimension( getWidth()/4,panelLeft.getHeight()));
		splitPane.setLeftComponent(panelLeft);
		panelLeft.setLayout(new BorderLayout(0, 0));
		 
		JLabel lblNewLabel = new JLabel("Job List", SwingConstants.CENTER);
		lblNewLabel.setPreferredSize(new Dimension(panelLeft.getWidth(), 30));
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblNewLabel.setForeground(Color.BLACK);

		
		JList<JobDescription> list = new JList(listOfJobs);
		list.setSelectionForeground(new Color(0, 0, 0));
		list.setSelectionBackground(new Color(255, 255, 255));
		list.setFont(new Font("Tahoma", Font.BOLD, 13));
		list.setForeground(Color.BLACK);
		setList(list);
		JScrollPane jScrollPaneJList = new JScrollPane(list);
		jScrollPaneJList.setBorder(compoundBorder);
		
		panelLeft.add(jScrollPaneJList, BorderLayout.CENTER);
		panelLeft.add(lblNewLabel, BorderLayout.NORTH);
		
		
		// Right panel
		JPanel panel = new JPanel();
		splitPane.setRightComponent(panel);
		panel.setLayout(new BorderLayout(4, 4));
		
		JLabel lblNewLabel2 = new JLabel("Job Title", SwingConstants.CENTER );
		lblNewLabel2.setPreferredSize(new Dimension(panelLeft.getWidth(), 30));
		lblNewLabel2.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblNewLabel2.setForeground(Color.BLACK);
		lblNewLabel2.setPreferredSize(new Dimension(panel.getWidth(), 30));
		
		JTextPane textPane = new JTextPane();
		textPane.setContentType("text/html");
		textPane.setMargin(new Insets(10,10, 10, 10));
		JScrollPane jScrollPane = new JScrollPane(textPane);
		jScrollPane.setBorder(compoundBorder);
		DefaultCaret caret = (DefaultCaret) textPane.getCaret(); // ←
		caret.setUpdatePolicy(DefaultCaret.NEVER_UPDATE);
		
		JButton btnNewButton = new JButton("Vist The Website");
		btnNewButton.setFont(new Font("Tahoma", Font.BOLD, 13));
		btnNewButton.setForeground(new Color(255, 255, 255));
		btnNewButton.setBackground(new Color(66, 75, 244));
		btnNewButton.setPreferredSize(new Dimension(panel.getWidth(), 30));
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				 try {
			          java.awt.Desktop.getDesktop().browse(java.net.URI.create(url));
			          }
			       catch (IOException e1) {
						JOptionPane.showMessageDialog(contentPane,
						        "couldn't open link: " + url);
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
                	lblNewLabel2.setText(job.getJobTitle() +" - " + job.getCity() );
                	url = job.getUrl();
                }
            }
        });	
		
		// set Selection For First Time
		url = listOfJobs[0].getUrl();
		textPane.setText(listOfJobs[0].getHtmlJobDes());
		lblNewLabel2.setText(listOfJobs[0].getJobTitle() + " - " + listOfJobs[0].getCity());
		list.setSelectedIndex(0);
		
	}
	


	private void setList(JList<JobDescription> list){
        list.setCellRenderer(new DefaultListCellRenderer() {
			private static final long serialVersionUID = 1L;

			@Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                Component renderer = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (renderer instanceof JLabel && value instanceof JobDescription) {
                    // Here value will be of the Type 'CD'
                    ((JLabel) renderer).setText( getJobTitle(((JobDescription) value).getJobTitle()));
                }
                return renderer;
            }
        });
	}



	protected String getJobTitle(String jobTitle) {
		String str = "";
		for(String string : jobTitle.split(" ")){
			if(str.length() > 30){
				str += "...";
				return str;
			}	
			str += string;
		}
		return str;
	}

}
