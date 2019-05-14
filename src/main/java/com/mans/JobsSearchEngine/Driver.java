package com.mans.JobsSearchEngine;

import java.awt.EventQueue;

import com.mans.JobsSearchEngine.controller.MainViewController;

public class Driver {

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					new MainViewController();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}
