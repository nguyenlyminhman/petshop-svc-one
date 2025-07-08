package com.pts.adm.config.interceptor;

import com.pts.common.ResponseObject;
import com.pts.util.WebClientUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import java.util.Date;
import java.util.HashMap;

@Component
public class PetshopInterceptor implements HandlerInterceptor {
    protected final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        log.info(" \n\n*** Begin - preHandle *** ");
        log.info(" ==> URL : " + request.getRequestURI());
        log.info(" ==> Method : " + request.getMethod());

        log.info(" ==> Time : " + new Date());

        if(request.getRequestURI().toLowerCase().contains("swagger-ui")) return true;

        String token = "";
        String authHeader = request.getHeader("Authorization");
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            token = authHeader.substring(7);
        }

        String urlAuth = "http://localhost:8080/pet-shop-auth/api/v1/auth/verify";
        TokenRequestDto tokenRequestDto =  new TokenRequestDto(token);

        ResponseObject responseObject = WebClientUtils.post(urlAuth, tokenRequestDto, null, null, ResponseObject.class);

        if(responseObject.isSuccess()) {
            AccountModel accountModel = modelMapper.map(responseObject.getData(), AccountModel.class );
            request.setAttribute("accountId", accountModel.getUsername());
        }

        log.info(" *** End - preHandle *** ");
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
                           ModelAndView modelAndView) throws Exception {
        log.info(" ==> Begin - postHandle <== ");

        log.info(" ==> End - postHandle <== ");
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler,
                                Exception ex) throws Exception {
        log.info(" ==> Begin - afterCompletion <== ");

        log.info(" ==> End - afterCompletion <== ");
    }

}
