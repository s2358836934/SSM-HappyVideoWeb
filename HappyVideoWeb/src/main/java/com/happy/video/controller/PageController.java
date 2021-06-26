package com.happy.video.controller;

import com.happy.video.entity.CourseTopic;
import com.happy.video.entity.CourseType;
import com.happy.video.service.CourseTopicService;
import com.happy.video.service.CourseTypeService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sun.org.glassfish.gmbal.ParameterNames;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class PageController {

    @Autowired
    CourseTopicService courseTopicService;

    @Autowired
    CourseTypeService courseTypeService;

    //    首页
    @RequestMapping(value = "/")
    public String indexPage(Model model) {

        // 首页 常用框架 1
        PageHelper.startPage(1, 4);
        PageInfo<CourseTopic> pageType1 = courseTopicService.getCourseTopicList(1);
        // 首页 数据库 3
        PageHelper.startPage(1, 4);
        PageInfo<CourseTopic> pageType3 = courseTopicService.getCourseTopicList(3);


        model.addAttribute("pageType1", pageType1);
        model.addAttribute("pageType3", pageType3);


        // 首页 1
        model.addAttribute("clickNav", 1);

//        return "/WEB-INF/jsp/index.jsp";
        // 找jsp頁面，通过视图解析器，找视图
        //  @ResponseBody
        // 直接返回内容 String 返回字符串
        return "index";
    }

    // 忘记密码（密码找回）
    @RequestMapping(value = "/forgetPage")
    public String forgetPage() {


        return "forget";
    }


    @RequestMapping("/topicList")
    public String topicList(Model model,@RequestParam(name="pageNum") String pageNum) {
        System.out.println("我进来了嘛？");
        // 获取专题数据显示
        // 获取所有专题数据(不指定类型)
        if(pageNum == null) {
            System.out.println("这是为什么"+pageNum);
            PageHelper.startPage(1, 16);
        }else{
            PageHelper.startPage(Integer.valueOf(pageNum), 16);
        }
        PageInfo<CourseTopic> topicList = courseTopicService.getCourseTopicList();
        System.out.println(topicList);
        List<CourseType> courseTypeList = courseTypeService.getAllType();


        model.addAttribute("topicList", topicList);
        model.addAttribute("courseTypeList", courseTypeList);
        System.out.println("这里数据传输上去了嘛？");
        // 全部
        model.addAttribute("clickType", 0);
        // 课程 2
        model.addAttribute("clickNav", 2);

        return "topic_list";
    }

}
