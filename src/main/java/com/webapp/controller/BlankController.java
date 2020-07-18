package com.webapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * This class is a part of Dbsys final project.
 *
 * <p>
 * This is the controller for displaying blank page.
 *
 * @author Juntao Peng
 */
@Controller
public class BlankController {

    @RequestMapping("/blank")
    public ModelAndView service(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        ModelAndView mv = new ModelAndView();
        HttpSession session = request.getSession();
        Object currentUserType = session.getAttribute("currentUserType");
        if ("admin".equals(currentUserType)) {
            mv.setViewName("mainAdmin");
            mv.addObject("mainPage", "admin/blank.jsp");
            return mv;
        } else if ("user".equals(currentUserType)) {
            mv.setViewName("mainUser");
            mv.addObject("mainPage", "user/blank.jsp");
            return mv;
        } else {
            mv.setViewName("index");
        }
        return mv;
    }

}
