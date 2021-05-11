package com.youming.sample.spring.boot.cache.po;


import java.util.Date;

import java.io.Serializable;

/**
 * <p>
 * 用户的角色关系表
 * </p>
 *
 * @author Author
 * @since 2018-10-16
 */
public class UserRole implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    
    private Integer id;
    /**
     * 用户类型(0学生，1老师)
     */
    private Integer userType;
    /**
     * 用户编号(外键，对应学生的学号或者老师的工号)
     */
    private String userNumber;
    /**
     * 角色id(外键，role_info的id)
     */
    private Integer roleId;
    /**
     * 学院代码，院级管理员，院级督导，需要指定所属院
     */
    private String manageCollegeNumber;
    /**
     * 专业代码，专业管理员，需要指明属于的专业
     */
    private String manageSpecialityNumber;
    /**
     * 教研室id，系主任使用
     */
    private Integer manageTeachingResearchRoomId;
    /**
     * 所管理的年级(入学年份)
     */
    private Integer manageGrade;
    /**
     * 所管理的班级编码
     */
    private String manageClassNumber;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 更新时间
     */
    private Date updateTime;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserType() {
        return userType;
    }

    public void setUserType(Integer userType) {
        this.userType = userType;
    }

    public String getUserNumber() {
        return userNumber;
    }

    public void setUserNumber(String userNumber) {
        this.userNumber = userNumber;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public String getManageCollegeNumber() {
        return manageCollegeNumber;
    }

    public void setManageCollegeNumber(String manageCollegeNumber) {
        this.manageCollegeNumber = manageCollegeNumber;
    }

    public String getManageSpecialityNumber() {
        return manageSpecialityNumber;
    }

    public void setManageSpecialityNumber(String manageSpecialityNumber) {
        this.manageSpecialityNumber = manageSpecialityNumber;
    }

    public Integer getManageTeachingResearchRoomId() {
        return manageTeachingResearchRoomId;
    }

    public void setManageTeachingResearchRoomId(Integer manageTeachingResearchRoomId) {
        this.manageTeachingResearchRoomId = manageTeachingResearchRoomId;
    }

    public Integer getManageGrade() {
        return manageGrade;
    }

    public void setManageGrade(Integer manageGrade) {
        this.manageGrade = manageGrade;
    }

    public String getManageClassNumber() {
        return manageClassNumber;
    }

    public void setManageClassNumber(String manageClassNumber) {
        this.manageClassNumber = manageClassNumber;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    public String toString() {
        return "UserRole{" +
        ", id=" + id +
        ", userType=" + userType +
        ", userNumber=" + userNumber +
        ", roleId=" + roleId +
        ", manageCollegeNumber=" + manageCollegeNumber +
        ", manageSpecialityNumber=" + manageSpecialityNumber +
        ", manageTeachingResearchRoomId=" + manageTeachingResearchRoomId +
        ", manageGrade=" + manageGrade +
        ", manageClassNumber=" + manageClassNumber +
        ", createTime=" + createTime +
        ", updateTime=" + updateTime +
        "}";
    }
}
