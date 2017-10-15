package com.hytch.lfpspringmaster.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.hytch.lfpspringmaster.base.BaseModel;
import java.util.Date;
import javax.persistence.*;

@Table(name = "client_user")
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
    @JsonIgnore
    @Column(name = "CREATE_DATE")
    private Date createDate;

    /**
     * 更新时间
     */
    @JsonIgnore
    @Column(name = "UPDATE_TIME")
    private Date updateTime;

    /**
     * 是否删除，false标示不删除
     */
    @JsonIgnore
    @Column(name = "ID_DELETE")
    private Boolean idDelete;

    /**
     * 是否冻结，false标示不冻结
     */
    @JsonIgnore
    @Column(name = "IS_FROZEN")
    private Boolean isFrozen;

    @Column(name = "CLIENT_TYPE")
    private String clientType;

    @Column(name = "PHONE_SYS")
    private String phoneSys;

    /**
     * @return ID
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
     * 获取昵称
     *
     * @return NAME - 昵称
     */
    public String getName() {
        return name;
    }

    /**
     * 设置昵称
     *
     * @param name 昵称
     */
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    /**
     * 获取账号
     *
     * @return ACCOUNT - 账号
     */
    public String getAccount() {
        return account;
    }

    /**
     * 设置账号
     *
     * @param account 账号
     */
    public void setAccount(String account) {
        this.account = account == null ? null : account.trim();
    }

    /**
     * 获取密码
     *
     * @return PASSWORD - 密码
     */
    public String getPassword() {
        return password;
    }

    /**
     * 设置密码
     *
     * @param password 密码
     */
    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    /**
     * 获取客户端版本号
     *
     * @return CLIENT_VERSION - 客户端版本号
     */
    public Integer getClientVersion() {
        return clientVersion;
    }

    /**
     * 设置客户端版本号
     *
     * @param clientVersion 客户端版本号
     */
    public void setClientVersion(Integer clientVersion) {
        this.clientVersion = clientVersion;
    }

    /**
     * 获取客户端版本号 int
     *
     * @return CLIENT_VERSION_NAME - 客户端版本号 int
     */
    public String getClientVersionName() {
        return clientVersionName;
    }

    /**
     * 设置客户端版本号 int
     *
     * @param clientVersionName 客户端版本号 int
     */
    public void setClientVersionName(String clientVersionName) {
        this.clientVersionName = clientVersionName == null ? null : clientVersionName.trim();
    }

    /**
     * 获取创建时间
     *
     * @return CREATE_DATE - 创建时间
     */
    public Date getCreateDate() {
        return createDate;
    }

    /**
     * 设置创建时间
     *
     * @param createDate 创建时间
     */
    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    /**
     * 获取更新时间
     *
     * @return UPDATE_TIME - 更新时间
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * 设置更新时间
     *
     * @param updateTime 更新时间
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * 获取是否删除，false标示不删除
     *
     * @return ID_DELETE - 是否删除，false标示不删除
     */
    public Boolean getIdDelete() {
        return idDelete;
    }

    /**
     * 设置是否删除，false标示不删除
     *
     * @param idDelete 是否删除，false标示不删除
     */
    public void setIdDelete(Boolean idDelete) {
        this.idDelete = idDelete;
    }

    /**
     * 获取是否冻结，false标示不冻结
     *
     * @return IS_FROZEN - 是否冻结，false标示不冻结
     */
    public Boolean getIsFrozen() {
        return isFrozen;
    }

    /**
     * 设置是否冻结，false标示不冻结
     *
     * @param isFrozen 是否冻结，false标示不冻结
     */
    public void setIsFrozen(Boolean isFrozen) {
        this.isFrozen = isFrozen;
    }

    /**
     * @return CLIENT_TYPE
     */
    public String getClientType() {
        return clientType;
    }

    /**
     * @param clientType
     */
    public void setClientType(String clientType) {
        this.clientType = clientType == null ? null : clientType.trim();
    }

    /**
     * @return PHONE_SYS
     */
    public String getPhoneSys() {
        return phoneSys;
    }

    /**
     * @param phoneSys
     */
    public void setPhoneSys(String phoneSys) {
        this.phoneSys = phoneSys == null ? null : phoneSys.trim();
    }
}