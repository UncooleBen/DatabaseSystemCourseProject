package com.webapp.filter;

import org.springframework.web.servlet.ModelAndView;


/**
 * This class is a part of Dbsys final project.
 *
 * <p>
 * This is the filter for user log-in.
 *
 * @author Juntao Peng
 */
public class LoginFilter {

    public static boolean isAuthorized(String currentUserType, String authorizedUserType, ModelAndView mv) {
        if (currentUserType == null || "".equals(currentUserType)) { /* Not login */
            mv.setViewName("index");
            return false;
        }
        if ("user".equals(authorizedUserType)) { /* Login and authorized user type */
            return true;
        }
        if ("admin".equals(authorizedUserType) && "admin".equals(currentUserType)) { /* Login and authorized user type */
            return true;
        }
        /* Login and unauthorized user type */
        mv.setViewName("mainUser");
        mv.addObject("mainPage", "user/blank.jsp");
        return false;
    }

}
