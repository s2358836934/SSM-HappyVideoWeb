package com.happy.video.service;

import com.happy.video.entity.CourseTopic;
import com.happy.video.entity.CourseVideo;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface CourseTopicService {

    // 根据课程分类获取专题视频
//    List<CourseTopic> getCourseTopicList(int typeId);

    // 分页插件
    PageInfo<CourseTopic> getCourseTopicList(int typeId);

    PageInfo<CourseTopic> getCourseTopicList();

    CourseTopic getCourseTopic(int topicId);

    // 根据用户指定名字 搜索视频
    PageInfo<CourseTopic> searchCourseTopic(String keyword);

}
