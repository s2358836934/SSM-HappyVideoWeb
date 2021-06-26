package com.happy.video.dao;

import com.happy.video.entity.Banner;
import com.happy.video.entity.CourseType;

import java.util.HashMap;
import java.util.List;

public interface CourseTypeDao {


    int insertCourseType(CourseType courseType);


    List<CourseType> findCourseTypeByCondition(HashMap<String, Object> map);

    List<CourseType> findCourseTypeAll();



}
