package com.hytch.lfpspringmaster.sys.upload;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 上传属性.
 */
@ConfigurationProperties("storage")
public class StorageProperties {

	private static final String FILE = "files/";
	/**
	 * Folder upload for storing files
	 */
	private String upload = FILE + "upload";
	/**
	 * 附件保存位置
	 */
	private String addAttachment = FILE + "attachment";

	public String getAddAttachment() {
		return addAttachment;
	}

	public void setAddAttachment(String addAttachment) {
		this.addAttachment = addAttachment;
	}

	public String getUpload() {
		return upload;
	}

	public void setUpload(String upload) {
		this.upload = upload;
	}
}
