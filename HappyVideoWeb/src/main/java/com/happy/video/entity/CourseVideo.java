package com.happy.video.entity;

import java.util.Date;

/**
 * course_video
 * 
 * @author bianj
 * @version 1.0.0 2020-09-17
 */
public class CourseVideo implements java.io.Serializable {
    /** 版本号 */
    private static final long serialVersionUID = -3704123040802956075L;

    /* This code was generated by TableGo tools, mark 1 begin. */

    /** id */
    private Integer id;

    /** videoName */
    private String videoName;

    /** flag */
    private Integer flag;

    /** freeFlag */
    private Integer freeFlag;

    /** courseTopicId */
    private Integer courseTopicId;

    /** playUrl */
    private String playUrl;

    /** createTime */
    private Date createTime;

    /* This code was generated by TableGo tools, mark 1 end. */

    /* This code was generated by TableGo tools, mark 2 begin. */

    /**
     * 获取id
     * 
     * @return id
     */
    public Integer getId() {
        return this.id;
    }

    /**
     * 设置id
     * 
     * @param id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 获取videoName
     * 
     * @return videoName
     */
    public String getVideoName() {
        return this.videoName;
    }

    /**
     * 设置videoName
     * 
     * @param videoName
     */
    public void setVideoName(String videoName) {
        this.videoName = videoName;
    }

    /**
     * 获取flag
     * 
     * @return flag
     */
    public Integer getFlag() {
        return this.flag;
    }

    /**
     * 设置flag
     * 
     * @param flag
     */
    public void setFlag(Integer flag) {
        this.flag = flag;
    }

    /**
     * 获取freeFlag
     * 
     * @return freeFlag
     */
    public Integer getFreeFlag() {
        return this.freeFlag;
    }

    /**
     * 设置freeFlag
     * 
     * @param freeFlag
     */
    public void setFreeFlag(Integer freeFlag) {
        this.freeFlag = freeFlag;
    }

    /**
     * 获取courseTopicId
     * 
     * @return courseTopicId
     */
    public Integer getCourseTopicId() {
        return this.courseTopicId;
    }

    /**
     * 设置courseTopicId
     * 
     * @param courseTopicId
     */
    public void setCourseTopicId(Integer courseTopicId) {
        this.courseTopicId = courseTopicId;
    }

    /**
     * 获取playUrl
     * 
     * @return playUrl
     */
    public String getPlayUrl() {
        return this.playUrl;
    }

    /**
     * 设置playUrl
     * 
     * @param playUrl
     */
    public void setPlayUrl(String playUrl) {
        this.playUrl = playUrl;
    }

    /**
     * 获取createTime
     * 
     * @return createTime
     */
    public Date getCreateTime() {
        return this.createTime;
    }

    /**
     * 设置createTime
     * 
     * @param createTime
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /* This code was generated by TableGo tools, mark 2 end. */

    @Override
    public String toString() {
        return "CourseVideo{" +
                "id=" + id +
                ", videoName='" + videoName + '\'' +
                ", flag=" + flag +
                ", freeFlag=" + freeFlag +
                ", courseTopicId=" + courseTopicId +
                ", playUrl='" + playUrl + '\'' +
                ", createTime=" + createTime +
                '}';
    }
}