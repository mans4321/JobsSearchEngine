package com.mans.JobsSearchEngine.model.job;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mans.JobsSearchEngine.utility.CleanText;

public class JobSearchInfo {

	private List<String> userSkills;
	private int userExperience;
	private List<String> cities;
	private List<String> titles;
	
	public JobSearchInfo(){
		userSkills = new ArrayList<String>();
		cities = new ArrayList<String>();
		titles = new ArrayList<String>();
	}

	public JobSearchInfo(String titles, String cities, String userExperience, String userSkills){
		this.titles = getList(titles);
		this.cities = getList(cities);
		this.userSkills = getList(userSkills);
		this.userExperience = getInteger(userExperience);
	}
	public void setUserSkills(List<String> userSkills) {
		this.userSkills = userSkills;
	}
	
	public int getUserExperience() {
		return userExperience;
	}
	
	public void setUserExperience(int userExperience) {
		this.userExperience = userExperience;
	}
	
	public List<String> getCities() {
		return cities;
	}
	
	public void setCities(List<String> cities) {
		this.cities = cities;
	}
	
	public List<String> getTitles() {
		return titles;
	}
	
	public void setTitles(List<String> titles) {
		this.titles = titles;
	}
	
	public List<String> getSkills() {
		return userSkills;
	}
	
	@JsonIgnore
	private int getInteger(String intValue) {
		return Integer.parseInt(intValue);
	}
	
	@JsonIgnore
	private List<String> getList(String text) { 
		List<String> list = new ArrayList<String>();
		if (text.contains(",")) {
			for (String jobInlist : text.split(",")){
				jobInlist = CleanText.simpleCleanText(jobInlist);
				list.add(jobInlist);
			}
		} else {
			list.add(text.trim());
		}
		return list;
	}
	
	@JsonIgnore
	public String getUserSkillsAsString(){
		return listToString(userSkills);
	}
	
	@JsonIgnore
	public String getUserExperienceAsString(){
		return String.valueOf(userExperience);
	}
	
	@JsonIgnore
	public String getCitiesAsString(){
		return listToString(cities);
	}
	
	@JsonIgnore
	public String getTitlesAsString(){
		return listToString(titles);
	}
	
	private String listToString(List<String> list) {
		String str = "";
		for(int i = 0 ; i < list.size() -1 ; i++)
			str += list.get(i) + ", ";
		if(list.size() >= 1)
			str += list.get(list.size() -1);
		
		str = CleanText.capitailizeWord(str);
		return str;
	}
}
