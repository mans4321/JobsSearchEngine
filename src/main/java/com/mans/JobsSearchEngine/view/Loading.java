package com.mans.JobsSearchEngine.view;

import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

public class Loading extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	/**
	 * Create the frame.
	 */
	public Loading() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 395, 151);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		setLocation((screenSize.width - getWidth()) / 2, (screenSize.height - getHeight()) / 2);
		
		ImageIcon imageLoading = new ImageIcon(getClass().getResource("/rotate.png"));
		JLabel label = new JLabel(imageLoading);
		label.setBounds(38, 41, 46, 37);
		contentPane.add(label);

		JLabel lblLoadingJobsShould = new JLabel("Loading Jobs Should Takes 1 Minute");
		lblLoadingJobsShould.setHorizontalAlignment(SwingConstants.CENTER);
		lblLoadingJobsShould.setBounds(78, 41, 250, 37);
		contentPane.add(lblLoadingJobsShould);

	}

}
