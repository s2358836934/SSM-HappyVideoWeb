package com.happy.video.dao;

import com.happy.video.entity.ToolsItem;
import com.happy.video.entity.ToolsType;

import java.util.HashMap;
import java.util.List;

public interface ToolsTypeDao {

    int insertToolsType(ToolsType toolsType);


    List<ToolsType> findToolsTypeByCondition(HashMap<String, Object> map);
}
