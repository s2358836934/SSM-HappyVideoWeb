package com.happy.video.dao;

import com.happy.video.entity.Banner;
import com.happy.video.entity.User;

import java.util.HashMap;
import java.util.List;

public interface BannerDao {

    int insertBanner(Banner banner);


    List<Banner> findBannerByCondition(HashMap<String, Object> map);


}
