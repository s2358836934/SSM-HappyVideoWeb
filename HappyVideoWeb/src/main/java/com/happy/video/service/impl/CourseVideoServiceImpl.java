package com.happy.video.service.impl;

import com.happy.video.dao.CourseVideoDao;
import com.happy.video.entity.CourseTopic;
import com.happy.video.entity.CourseVideo;
import com.happy.video.service.CourseVideoService;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;


@Service
public class CourseVideoServiceImpl implements CourseVideoService {

    @Autowired
    CourseVideoDao courseVideoDao;

    public List<CourseVideo> getCourseVideo(int topicId) {

        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("courseTopicId", topicId);
        map.put("flag", 1);

        List<CourseVideo> list = courseVideoDao.findCourseVideoByCondition(map);

        return list;
    }


}
