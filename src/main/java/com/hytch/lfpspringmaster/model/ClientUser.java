package com.hytch.lfpspringmaster.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.hytch.lfpspringmaster.base.BaseModel;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Table(name = "client_user")
@Data
public class ClientUser extends BaseModel {
	@Id
	@Column(name = "ID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	/**
	 * 昵称
	 */
	@Column(name = "NAME")
	private String name;

	/**
	 * 账号
	 */
	@Column(name = "ACCOUNT")
	private String account;

	/**
	 * 密码
	 */
	@Column(name = "PASSWORD")
	private String password;

	/**
	 * 客户端版本号
	 */
	@Column(name = "CLIENT_VERSION")
	private Integer clientVersion;

	/**
	 * 客户端版本号 int
	 */
	@Column(name = "CLIENT_VERSION_NAME")
	private String clientVersionName;

	/**
	 * 创建时间
	 */
	@Column(name = "CREATE_DATE")
	@JsonIgnore
	private Date createDate;

	/**
	 * 更新时间
	 */
	@Column(name = "UPDATE_TIME")
	@JsonIgnore
	private Date updateTime;

	/**
	 * 是否删除，false标示不删除
	 */
	@Column(name = "IS_DELETE")
	@JsonIgnore
	private Boolean isDelete;

	/**
	 * 是否冻结，false标示不冻结
	 */
	@Column(name = "IS_FROZEN")
	@JsonIgnore
	private Boolean isFrozen;

	@Column(name = "CLIENT_TYPE")
	private String clientType;

	@Column(name = "PHONE_SYS")
	private String phoneSys;
}