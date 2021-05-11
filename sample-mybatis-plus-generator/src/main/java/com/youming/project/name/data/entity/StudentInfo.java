package com.youming.project.name.data.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import java.time.LocalDate;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import java.io.Serializable;

/**
 * <p>
 * 学生用户表
 * </p>
 *
 * @author author
 *
 */
public class StudentInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 账号
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 姓名
     */
    private String name;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 头像地址
     */
    private String profilePath;

    /**
     * 性别(男，女)
     */
    private String gender;

    /**
     * 出生日期
     */
    private LocalDate birthday;

    /**
     * 注册时间
     */
    private LocalDateTime createTime;

    /**
     * 注册类型 1。用户注册 2.后台添加
     */
    private Integer registType;

    /**
     * 设备类型（1 安卓 2 IOS 3...）
     */
    private Integer deviceType;

    /**
     * 设备id
     */
    private String deviceId;

    /**
     * 连续答对X次，移出错题本，属于个人设置
     */
    private Integer removeCountSet;

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
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
    public String getProfilePath() {
        return profilePath;
    }

    public void setProfilePath(String profilePath) {
        this.profilePath = profilePath;
    }
    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
    public LocalDate getBirthday() {
        return birthday;
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }
    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }
    public Integer getRegistType() {
        return registType;
    }

    public void setRegistType(Integer registType) {
        this.registType = registType;
    }
    public Integer getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(Integer deviceType) {
        this.deviceType = deviceType;
    }
    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }
    public Integer getRemoveCountSet() {
        return removeCountSet;
    }

    public void setRemoveCountSet(Integer removeCountSet) {
        this.removeCountSet = removeCountSet;
    }

    @Override
    public String toString() {
        return "StudentInfo{" +
        "id=" + id +
        ", username=" + username +
        ", password=" + password +
        ", name=" + name +
        ", phone=" + phone +
        ", profilePath=" + profilePath +
        ", gender=" + gender +
        ", birthday=" + birthday +
        ", createTime=" + createTime +
        ", registType=" + registType +
        ", deviceType=" + deviceType +
        ", deviceId=" + deviceId +
        ", removeCountSet=" + removeCountSet +
        "}";
    }
}
