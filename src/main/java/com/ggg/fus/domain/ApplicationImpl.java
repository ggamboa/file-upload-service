package com.ggg.fus.domain;

public class ApplicationImpl implements Application {
	
	private String applicationName;
	private boolean canDownload;
	private boolean canDelete;
	
	public ApplicationImpl(String applicationName, boolean canDownload,	boolean canDelete) {
		this.applicationName = applicationName;
		this.canDownload = canDownload;
		this.canDelete = canDelete;
	}

	public String getApplicationName() {
		return applicationName;
	}

	public boolean isCanDownload() {
		return canDownload;
	}

	public boolean isCanDelete() {
		return canDelete;
	}
	
	

}
