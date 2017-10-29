package com.hytch.lfpspringmaster.sys.upload;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 上传属性.
 */
@ConfigurationProperties("storage")
public class StorageProperties {

	/**
	 * Folder location for storing files
	 */
	private String location = "upload/tmp";

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}
}
