package com.happy.video.interceptor;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.unit.DataUnit;
import cn.hutool.crypto.digest.DigestUtil;
import com.happy.video.dto.UserToken;
import com.happy.video.entity.User;
import com.happy.video.util.Constants;
import com.happy.video.util.VideoUtil;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.ServletContext;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * 自动登录拦截器
 */
public class AutoLoginInterceptor implements HandlerInterceptor {


    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) throws Exception {
        // 获取cookie，如果合法，未登录，直接登录，继续请求
        Cookie[] cookies = request.getCookies();

        if (cookies == null) {
            return true;
        }
        for (Cookie cookie : cookies) {
            String cname = cookie.getName();
            if (Constants.COOKIE_NAME_LOGIN_TOKEN.equalsIgnoreCase(cname)) {
                String cToken = cookie.getValue();
                // 判斷cookie的token是否合法

                // 根据用户的token获取userToken，如果拿不到，直接返回
                // 如果拿到，判断token值（合法，是否过期）
                ServletContext servletContext = request.getServletContext();

                // servletContext -- 所有人的登录信息
                HashMap<String, UserToken> userTokenmMap = (HashMap<String, UserToken>) servletContext.getAttribute(Constants.CONTEXT_TOKEN_USER);
                if (userTokenmMap == null) {
                    // 没有登录数据
                    return true;
                }

                UserToken userToken = userTokenmMap.get(cToken);
                if (userToken == null) {
                    return true;
                }

                // 验证是否同一个浏览器请求
                StringBuilder builder = new StringBuilder();
                builder.append(userToken.getNowString());
                builder.append(userToken.getUser().getEmail());
                // -------------------------
                builder.append(request.getHeader("User-Agent"));
                builder.append(VideoUtil.getIPAddress(request));
                // -------------------------
                builder.append(Constants.SERVER_SEC_KEY);
                String newToken = DigestUtil.md5Hex(builder.toString());

                if (!newToken.equalsIgnoreCase(cToken)) {
                    return true;
                }

                // 服务器有用户的token信息
                // 验证是否正确或者过期
                // 过期？？
                String loginNowString = userToken.getNowString();
                // 用户登录的时间戳
                long lnt = DateUtil.parseDateTime(loginNowString).getTime();
                long now = System.currentTimeMillis();

                long timeout = now - lnt;

                if (timeout > 60 * 60 * 24 * 1000) {
                    return true;
                }

                // 添加自动登录
                HttpSession session = request.getSession(true);
                // 保存session
                session.setAttribute(Constants.SESSION_LOGINUSER, userToken.getUser());
                break;
            }

        }

        // 其他情况，直接请求
        return true;
    }
}
