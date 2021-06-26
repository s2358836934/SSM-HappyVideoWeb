package com.happy.video.service.impl;

import com.happy.video.dao.CourseTopicDao;
import com.happy.video.entity.CourseTopic;
import com.happy.video.entity.CourseVideo;
import com.happy.video.service.CourseTopicService;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class CourseTopicServiceImpl implements CourseTopicService {

    @Autowired
    CourseTopicDao courseTopicDao;

    public PageInfo<CourseTopic> getCourseTopicList(int typeId) {

        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("courseTypeId", typeId);
        map.put("flag", 1);

        List<CourseTopic> dbList = courseTopicDao.findCourseTopicByCondition(map);

        PageInfo<CourseTopic> pageInfo = new PageInfo<CourseTopic>(dbList);

        return pageInfo;
    }


    public PageInfo<CourseTopic> getCourseTopicList() {

        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("flag", 1);

        List<CourseTopic> dbList = courseTopicDao.findCourseTopicByCondition(map);

        PageInfo<CourseTopic> pageInfo = new PageInfo<CourseTopic>(dbList);

        return pageInfo;
    }

    // 根据ID 获取专题
    public CourseTopic getCourseTopic(int topicId) {

        List<Integer> idList = new ArrayList<Integer>();
        idList.add(topicId);

        List<CourseTopic> list = courseTopicDao.findCourseTopicByIds(idList);

        return list.get(0);
    }

    public PageInfo<CourseTopic> searchCourseTopic(String keyword) {

        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("topicName", keyword);
        map.put("flag", 1);

        List<CourseTopic> list = courseTopicDao.findCourseTopicByCondition(map);

        PageInfo<CourseTopic> pageInfo = new PageInfo<CourseTopic>(list);

        return pageInfo;
    }

}
