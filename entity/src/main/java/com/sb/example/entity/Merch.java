package com.sb.example.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 用户(商户)表
 * Created by lwnull2018@gmail.com ON 2021/2/1.
 */
@Table(name="merch")
public class Merch implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 用户名
     */
    @Column(name = "username")
    private String username;

    /**
     * 密码
     */
    @Column(name = "password")
    private String password;

    /**
     * 注册时间
     */
    @Column(name = "create_time")
    private String createTime;

    /**
     * 最后登录时间
     */
    @Column(name = "last_time")
    private String lastTime;

    /**
     * 最后登录ip
     */
    @Column(name = "last_ip")
    private String lastIp;

    /**
     * 手机号码
     */
    @Column(name = "mobile")
    private String mobile;

    @Column(name = "appkey")
    private String appkey;

    /**
     * 会员等级
     */
    @Column(name = "level")
    private Integer level;

    /**
     * 状态
     */
    @Column(name = "status")
    private Integer status;

    /**
     * 上级代理id
     */
    @Column(name = "agent_id")
    private Integer agentId;

    /**
     * 手续费
     */
    @Column(name = "agent_fee")
    private BigDecimal agentFee;

    /**
     * 手续费配置
     */
    @Column(name = "fee_config")
    private String feeConfig;

    /**
     * 余额
     */
    @Column(name = "balance")
    private BigDecimal balance;

    /**
     * 冻结余额
     */
    @Column(name = "freeze_balance")
    private BigDecimal freezeBalance;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getLastTime() {
        return lastTime;
    }

    public void setLastTime(String lastTime) {
        this.lastTime = lastTime;
    }

    public String getLastIp() {
        return lastIp;
    }

    public void setLastIp(String lastIp) {
        this.lastIp = lastIp;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getAppkey() {
        return appkey;
    }

    public void setAppkey(String appkey) {
        this.appkey = appkey;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getAgentId() {
        return agentId;
    }

    public void setAgentId(Integer agentId) {
        this.agentId = agentId;
    }

    public BigDecimal getAgentFee() {
        return agentFee;
    }

    public void setAgentFee(BigDecimal agentFee) {
        this.agentFee = agentFee;
    }

    public String getFeeConfig() {
        return feeConfig;
    }

    public void setFeeConfig(String feeConfig) {
        this.feeConfig = feeConfig;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public BigDecimal getFreezeBalance() {
        return freezeBalance;
    }

    public void setFreezeBalance(BigDecimal freezeBalance) {
        this.freezeBalance = freezeBalance;
    }

    @Override
    public String toString() {
        return "Merch{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", createTime='" + createTime + '\'' +
                ", lastTime='" + lastTime + '\'' +
                ", lastIp='" + lastIp + '\'' +
                ", mobile='" + mobile + '\'' +
                ", appkey='" + appkey + '\'' +
                ", level=" + level +
                ", status=" + status +
                ", agentId=" + agentId +
                ", agentFee=" + agentFee +
                ", feeConfig='" + feeConfig + '\'' +
                ", balance=" + balance +
                ", freezeBalance=" + freezeBalance +
                '}';
    }
}
