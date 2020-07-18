package com.webapp.controller;

import com.webapp.model.user.User;
import com.webapp.service.database.dao.LoginDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * This class is a part of Dbsys final project.
 *
 * <p>
 * This is the controller for user sign-up.
 *
 * @author Juntao Peng
 */
@Controller
public class SignupController {


    private final LoginDao loginDao;

    @Autowired
    public SignupController(LoginDao loginDao) {
        this.loginDao = loginDao;
    }

    @RequestMapping("/signup")
    public ModelAndView signupPage() {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("signup");
        return mv;
    }

    @RequestMapping("/signupSubmit")
    public ModelAndView signupSubmit(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        ModelAndView mv = new ModelAndView();
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String name = request.getParameter("name");
        String tel = request.getParameter("tel");
        String sex = request.getParameter("sex");

        User currentUser = null;
        User user = new User(username, password, name, sex, tel);
        currentUser = loginDao.signup(user);
        if (currentUser == null) {
            mv.setViewName("signup");
            mv.addObject("error", "该用户名已存在");
        } else {
            mv.setViewName("index");
            mv.addObject("user", currentUser);
        }
        return mv;
    }

}
