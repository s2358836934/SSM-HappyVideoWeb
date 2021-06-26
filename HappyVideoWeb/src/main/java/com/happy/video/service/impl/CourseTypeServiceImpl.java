package com.happy.video.service.impl;

import com.happy.video.dao.CourseTypeDao;
import com.happy.video.entity.CourseTopic;
import com.happy.video.entity.CourseType;
import com.happy.video.service.CourseTypeService;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
public class CourseTypeServiceImpl implements CourseTypeService {

    @Autowired
    CourseTypeDao courseTypeDao;


    public List<CourseType> getAllType() {

        List<CourseType> list = courseTypeDao.findCourseTypeAll();

        return list;
    }
}
