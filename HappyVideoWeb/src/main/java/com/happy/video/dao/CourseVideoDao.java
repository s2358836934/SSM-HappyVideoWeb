package com.happy.video.dao;

import com.happy.video.entity.CourseType;
import com.happy.video.entity.CourseVideo;

import java.util.HashMap;
import java.util.List;

public interface CourseVideoDao {


    int insertCourseVideo(CourseVideo courseVideo);


    List<CourseVideo> findCourseVideoByCondition(HashMap<String, Object> map);

}
