package com.mans.JobsSearchEngine.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

import com.mans.JobsSearchEngine.model.job.JobSearchInfo;
import com.mans.JobsSearchEngine.utility.StreamIO;
import com.mans.JobsSearchEngine.view.MainWindowView;

public class MainViewController {

	private MainWindowView mainWindowView;
	
	private FetchJobFacade fetchJobs; 

	public MainViewController() {
		mainWindowView = new MainWindowView();
		setSearchButtonListener();
		loadLatestUserInfo();
		mainWindowView.getFrame().setVisible(true);
		
		fetchJobs = new FetchJobFacade();
	}

	private void setSearchButtonListener() {
		mainWindowView.getSearchButton().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (!isValidInput()) {
					return;
				}
				search();
				mainWindowView.getFrame().setVisible(false);
			}
		});
	}

	private boolean isValidInput() {
		final String jobTitle = mainWindowView.getJobTitle().getText();
		final String jobLocation = mainWindowView.getJobLocation().getText();
		final String experience = mainWindowView.getExperience().getText();
        final boolean numeric = experience.matches("-?\\d+(\\.\\d+)?");
        
		if (jobTitle.trim().equals("") || jobLocation.trim().equals("") ) {
			JOptionPane.showMessageDialog(mainWindowView.getFrame(), "job Title Or job Location cannot be empty");
			return false;
		}else if(!numeric){
			JOptionPane.showMessageDialog(mainWindowView.getFrame(), "experience must be a number");
			return false;
		}
		return true;
	}

	private void loadLatestUserInfo() {
		final JobSearchInfo searchInfo = StreamIO.getInstance().getUserInfo();
		mainWindowView.getJobTitle().setText(searchInfo.getTitlesAsString());
		mainWindowView.getJobLocation().setText(searchInfo.getCitiesAsString());
		mainWindowView.getUserSkills().setText(searchInfo.getUserSkillsAsString());
		mainWindowView.getExperience().setText(searchInfo.getUserExperienceAsString());
	}

	private void search() {
		new Thread(() -> {
			JobSearchInfo searchInfo = constructSearchInfo();
			StreamIO.getInstance().writeToFile(searchInfo);
			fetchJobs.fetch(searchInfo);
		}).start();
	}

	private JobSearchInfo constructSearchInfo() {
		String titles = mainWindowView.getJobTitle().getText();
		String cities = mainWindowView.getJobLocation().getText();
		String userExperience = mainWindowView.getExperience().getText();
		String userSkills = mainWindowView.getUserSkills().getText();
		return new JobSearchInfo(titles, cities, userExperience, userSkills);
	}
}
