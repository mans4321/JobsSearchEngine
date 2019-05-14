package com.mans.JobsSearchEngine.view;

import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;


public class LoadingView extends JFrame {
	
	private static final long serialVersionUID = 1L;
	
	private JPanel contentPane;

	public LoadingView() {
		initFrame();
		initIcon();
		initMessage();
	}

	private void initFrame() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 395, 151);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		setLocation((screenSize.width - getWidth()) / 2, (screenSize.height - getHeight()) / 2);
	}

	private void initIcon() {
		ImageIcon loadingIcon = new ImageIcon(getClass().getResource("/rotate.png"));
		JLabel label = new JLabel(loadingIcon);
		label.setBounds(38, 41, 46, 37);
		contentPane.add(label);
	}

	private void initMessage() {
		JLabel message = new JLabel("fetching Jobs will take around 1 Minute");
		message.setHorizontalAlignment(SwingConstants.CENTER);
		message.setBounds(78, 41, 250, 37);
		contentPane.add(message);
	}
}
