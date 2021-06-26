package com.happy.video.dao;

import com.happy.video.entity.CourseTopic;
import com.happy.video.entity.CourseType;

import java.util.HashMap;
import java.util.List;

public interface CourseTopicDao {


    int insertCourseTopic(CourseTopic courseTopic);


    List<CourseTopic> findCourseTopicByCondition(HashMap<String, Object> map);


    List<CourseTopic> findCourseTopicByIds(List<Integer> idList);
}
