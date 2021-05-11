package com.youming.project.name.data.entity;

import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;

/**
 * <p>
 * 教师表
 * </p>
 *
 * @author author
 *
 */
public class TeacherInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private String username;

    /**
     * 姓名
     */
    private String name;

    /**
     * 密码
     */
    private String password;

    /**
     * 教师介绍
     */
    private String introduce;

    /**
     * 头像地址
     */
    private String profilePath;

    /**
     * 授课评分(总得分平均分)
     */
    private BigDecimal teachingGrade;

    /**
     * 评分总人数
     */
    private Integer gradeAmount;

    /**
     * 授课总时长（分钟）
     */
    private Integer teachingDuration;

    /**
     * 状态
     */
    private Integer loginStatus;

    /**
     * 后台用户id（每个教师应该有一个后台用户账号）
     */
    private Integer adminId;

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
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    public String getIntroduce() {
        return introduce;
    }

    public void setIntroduce(String introduce) {
        this.introduce = introduce;
    }
    public String getProfilePath() {
        return profilePath;
    }

    public void setProfilePath(String profilePath) {
        this.profilePath = profilePath;
    }
    public BigDecimal getTeachingGrade() {
        return teachingGrade;
    }

    public void setTeachingGrade(BigDecimal teachingGrade) {
        this.teachingGrade = teachingGrade;
    }
    public Integer getGradeAmount() {
        return gradeAmount;
    }

    public void setGradeAmount(Integer gradeAmount) {
        this.gradeAmount = gradeAmount;
    }
    public Integer getTeachingDuration() {
        return teachingDuration;
    }

    public void setTeachingDuration(Integer teachingDuration) {
        this.teachingDuration = teachingDuration;
    }
    public Integer getLoginStatus() {
        return loginStatus;
    }

    public void setLoginStatus(Integer loginStatus) {
        this.loginStatus = loginStatus;
    }
    public Integer getAdminId() {
        return adminId;
    }

    public void setAdminId(Integer adminId) {
        this.adminId = adminId;
    }

    @Override
    public String toString() {
        return "TeacherInfo{" +
        "id=" + id +
        ", username=" + username +
        ", name=" + name +
        ", password=" + password +
        ", introduce=" + introduce +
        ", profilePath=" + profilePath +
        ", teachingGrade=" + teachingGrade +
        ", gradeAmount=" + gradeAmount +
        ", teachingDuration=" + teachingDuration +
        ", loginStatus=" + loginStatus +
        ", adminId=" + adminId +
        "}";
    }
}
