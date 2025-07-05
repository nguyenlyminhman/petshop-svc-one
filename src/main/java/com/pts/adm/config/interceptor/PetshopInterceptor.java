package com.pts.adm.config.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Date;

@Component
public class PetshopInterceptor implements HandlerInterceptor {
    protected final Logger log = LoggerFactory.getLogger(getClass());

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        log.info(" *** Begin - preHandle *** ");
        log.info(" ==> URL : " + request.getRequestURI());
        log.info(" ==> Method : " + request.getMethod());
        log.info(" ==> Time : " + new Date());

        request.setAttribute("accountId", "HelloNeko");

        log.info(" *** End - preHandle *** ");
        return true;
    }
}
