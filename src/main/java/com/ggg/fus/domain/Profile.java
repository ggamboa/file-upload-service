package com.ggg.fus.domain;

import java.util.List;

public interface Profile {
	
	public String getUserName();
	public String getPassword();
	public List<Application> getApplications();
	public boolean addApplication(Application app);
	public boolean removeApplication(Application app);
	
}
