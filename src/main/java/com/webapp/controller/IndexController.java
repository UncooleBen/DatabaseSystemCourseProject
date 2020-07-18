package com.webapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * This class is a part of Dbsys final project.
 *
 * <p>
 * This is the controller for displaying index page.
 *
 * @author Juntao Peng
 */
@Controller
public class IndexController {

    @RequestMapping(value = {"/", "index"}, method = RequestMethod.GET)
    public String service(HttpServletRequest request) {
        HttpSession session = request.getSession();
        session.invalidate();
        return "index";
    }
}
