package com.happy.video.controller;


import com.happy.video.dao.CourseVideoDao;
import com.happy.video.entity.CourseTopic;
import com.happy.video.entity.CourseType;
import com.happy.video.entity.CourseVideo;
import com.happy.video.service.CourseTopicService;
import com.happy.video.service.CourseTypeService;
import com.happy.video.service.CourseVideoService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
public class TopicController {

    @Autowired
    CourseTopicService courseTopicService;

    @Autowired
    CourseTypeService courseTypeService;

    @Autowired
    CourseVideoService courseVideoService;

    @RequestMapping("/topicList/{typeId}")
    public String topicList(@PathVariable Integer typeId, Model model, Integer pageNum) {


        if (pageNum == null || pageNum <= 0) {
            pageNum = 1;
        }

        PageHelper.startPage(pageNum, 16);
        PageInfo<CourseTopic> topicList = courseTopicService.getCourseTopicList(typeId);

        List<CourseType> courseTypeList = courseTypeService.getAllType();

        model.addAttribute("topicList", topicList);
        model.addAttribute("courseTypeList", courseTypeList);
        // 记录获取对应类型的ID
        model.addAttribute("clickType", typeId);

        return "topic_list";
    }

    // 指定专题ID，視頻ID
    @RequestMapping("/topic/{topicId}/{videoId}")
    public String topicVideo(@PathVariable Integer topicId, @PathVariable Integer videoId, Model model) {


        // 对应专题ID的内容介绍
        CourseTopic courseTopic = courseTopicService.getCourseTopic(topicId);

        // 获取对应专题ID的所有视频
        List<CourseVideo> courseVideoList = courseVideoService.getCourseVideo(topicId);

        CourseVideo courseVideo = null;
        // 播放用户指定的视频ID
        for (CourseVideo video : courseVideoList) {
            if (video.getId() == videoId) {
                // 当前页面显示播放的视频
                courseVideo = video;
                break;
            }
        }

        model.addAttribute("courseVideo", courseVideo);
        model.addAttribute("courseVideoList", courseVideoList);
        model.addAttribute("courseTopic", courseTopic);


        return "course_video";
    }

    // 指定专题ID，显示对应专题的视频
    @RequestMapping("/topic/{topicId}")
    public String topic(@PathVariable Integer topicId, Model model) {

        // 对应专题ID的内容介绍
        CourseTopic courseTopic = courseTopicService.getCourseTopic(topicId);

        // 获取对应专题ID的所有视频
        List<CourseVideo> courseVideoList = courseVideoService.getCourseVideo(topicId);

        // 显示专题，默认显示播放第一集
        CourseVideo courseVideo = courseVideoList.get(0);

        model.addAttribute("courseVideo", courseVideo);
        model.addAttribute("courseVideoList", courseVideoList);
        model.addAttribute("courseTopic", courseTopic);

        return "course_video";

    }
}
