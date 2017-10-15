package com.hytch.lfpspringmaster.model;

import com.hytch.lfpspringmaster.base.BaseModel;
import java.util.Date;
import javax.persistence.*;

@Table(name = "user_info")
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

    /**
     * @return id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @return userName
     */
    public String getUsername() {
        return username;
    }

    /**
     * @param username
     */
    public void setUsername(String username) {
        this.username = username == null ? null : username.trim();
    }

    /**
     * 获取用户昵称
     *
     * @return nickName - 用户昵称
     */
    public String getNickname() {
        return nickname;
    }

    /**
     * 设置用户昵称
     *
     * @param nickname 用户昵称
     */
    public void setNickname(String nickname) {
        this.nickname = nickname == null ? null : nickname.trim();
    }

    /**
     * @return passWrd
     */
    public String getPasswrd() {
        return passwrd;
    }

    /**
     * @param passwrd
     */
    public void setPasswrd(String passwrd) {
        this.passwrd = passwrd == null ? null : passwrd.trim();
    }

    /**
     * @return tag
     */
    public String getTag() {
        return tag;
    }

    /**
     * @param tag
     */
    public void setTag(String tag) {
        this.tag = tag == null ? null : tag.trim();
    }

    /**
     * @return createDate
     */
    public Date getCreatedate() {
        return createdate;
    }

    /**
     * @param createdate
     */
    public void setCreatedate(Date createdate) {
        this.createdate = createdate;
    }

    /**
     * @return updateDate
     */
    public Date getUpdatedate() {
        return updatedate;
    }

    /**
     * @param updatedate
     */
    public void setUpdatedate(Date updatedate) {
        this.updatedate = updatedate;
    }

    /**
     * @return groupCode
     */
    public String getGroupcode() {
        return groupcode;
    }

    /**
     * @param groupcode
     */
    public void setGroupcode(String groupcode) {
        this.groupcode = groupcode == null ? null : groupcode.trim();
    }

    /**
     * @return remark
     */
    public String getRemark() {
        return remark;
    }

    /**
     * @param remark
     */
    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    /**
     * 获取盐值
     *
     * @return salt - 盐值
     */
    public String getSalt() {
        return salt;
    }

    /**
     * 设置盐值
     *
     * @param salt 盐值
     */
    public void setSalt(String salt) {
        this.salt = salt == null ? null : salt.trim();
    }
}