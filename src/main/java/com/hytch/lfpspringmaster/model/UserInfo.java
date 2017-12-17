package com.hytch.lfpspringmaster.model;

import com.hytch.lfpspringmaster.base.BaseModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.Date;

@EqualsAndHashCode(callSuper = true)
@Table(name = "user_info")
@Data
public class UserInfo extends BaseModel {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(name = "userName")
	private String username;

	/**
	 * 用户昵称
	 */
	@Column(name = "nickName")
	private String nickname;

	@Column(name = "passWrd")
	private String passwrd;

	private String tag;

	@Column(name = "createDate")
	private Date createdate;

	@Column(name = "updateDate")
	private Date updatedate;

	@Column(name = "groupCode")
	private String groupcode;

	private String remark;

	/**
	 * 盐值
	 */
	private String salt;
}