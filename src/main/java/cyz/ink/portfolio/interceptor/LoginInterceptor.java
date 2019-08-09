package cyz.ink.portfolio.interceptor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class LoginInterceptor implements HandlerInterceptor {
    @Autowired
    private static final Logger logger = LoggerFactory.getLogger(LoginInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        if(request.getRequestURI().matches("/admin*")){
            logger.warn("someone want to reach admin page");
            if (request.getCookies() == null){
                response.sendRedirect("/login");
                return false;
            }
            for (Cookie cookie : request.getCookies()){
                if (cookie.getName().equals("t") && cookie.getValue().equals("tickets"))return true;
            }
            logger.info("fail to access admin page");
            response.sendRedirect("/login");
            return false;
        }
        logger.warn("someone reach admin page successfully");
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }
}
