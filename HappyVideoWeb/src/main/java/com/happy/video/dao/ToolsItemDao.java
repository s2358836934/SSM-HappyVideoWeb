package com.happy.video.dao;

import com.happy.video.entity.CourseVideo;
import com.happy.video.entity.ToolsItem;

import java.util.HashMap;
import java.util.List;

public interface ToolsItemDao {

    int insertToolsItem(ToolsItem toolsItem);


    List<ToolsItem> findToolsItemByCondition(HashMap<String, Object> map);


}
