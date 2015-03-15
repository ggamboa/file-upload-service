package com.ggg.fus.domain;

import java.util.ArrayList;
import java.util.List;

public class ProfileImpl implements Profile {
	private String userName;
	private String password;
	private List<Application> applications;
	
	public ProfileImpl(String userName, String password) {
		this.userName = userName;
		this.password = password;
		applications = new ArrayList<Application>();
	}
	
	public boolean addApplication(Application app) {
		return applications.add(app);
	}
	
	public boolean removeApplication(Application app) {
		return applications.remove(app);
	}

	public String getUserName() {
		return userName;
	}

	public String getPassword() {
		return password;
	}

	@Override
	public List<Application> getApplications() {
		return applications;
	}
	
	
}
