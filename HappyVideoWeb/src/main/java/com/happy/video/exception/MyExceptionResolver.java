package com.happy.video.exception;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 统一异常处理
 */
@Component
public class MyExceptionResolver implements HandlerExceptionResolver {


    public ModelAndView resolveException(HttpServletRequest request,
                                         HttpServletResponse response, Object handler, Exception ex) {

        ModelAndView mv = new ModelAndView();
        mv.setViewName("error");

        if (ex instanceof UserException) {
            mv.addObject("message", ex.getMessage());
        } else {
            mv.addObject("message", "未知错误");
        }

        return mv;
    }
}
