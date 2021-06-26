package com.happy.video.service;

import com.happy.video.entity.CourseTopic;
import com.happy.video.entity.CourseVideo;
import com.github.pagehelper.PageInfo;

import java.util.LinkedList;
import java.util.List;

public interface CourseVideoService {

    // 根据专题ID 获取所有视频
    List<CourseVideo> getCourseVideo(int topicId);

}
