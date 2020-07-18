package com.webapp.controller;

import com.webapp.filter.LoginFilter;
import com.webapp.model.user.Gender;
import com.webapp.model.user.User;
import com.webapp.service.database.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

/**
 * This class is a part of Dbsys final project.
 *
 * <p>
 * This is the controller for managing user information.
 *
 * @author Juntao Peng
 */
@Controller
public class UserController {

    UserDao userDao;

    int recordPerPage = 15;

    @Autowired
    public UserController(UserDao userDao) {
        this.userDao = userDao;
    }

    @RequestMapping(value = "/user")
    public ModelAndView service(@RequestParam("action") String action,
                                @RequestParam(value = "page", required = false) Integer page,
                                HttpServletRequest request,
                                HttpSession session) {
        ModelAndView mv = new ModelAndView();
        String currentUserType = (String) session.getAttribute("currentUserType");
        boolean isAuthorized = LoginFilter.isAuthorized(currentUserType, "admin", mv);
        if (!isAuthorized) {
            return mv;
        }
        String idStr = request.getParameter("id");
        int id = 0;
        if (idStr != null && idStr.length() != 0) {
            id = Integer.parseInt(idStr);
        }

        mv.setViewName("mainAdmin");
        switch (action) {
            case "delete":
                deleteUser(mv, id);
                listUsers(mv, 1);
                break;
            case "modify":
                modifyUser(mv, id);
                break;
            case "add":
                addUser(mv);
                break;
            case "save":
                saveUser(mv, request);
                listUsers(mv, 1);
                break;
            case "search":
                searchUser(mv, request);
                break;
            case "list":
            default:
                listUsers(mv, page);
        }
        return mv;
    }

    private void listUsers(ModelAndView mv, int page) {
        mv.addObject("mainPage", "admin/user.jsp");
        List<User> users = this.userDao.queryAllUsers((page-1)*recordPerPage, recordPerPage);
        int totalDocument = this.userDao.queryTotalUserNumber();
        int totalPage;
        if (totalDocument % recordPerPage == 0) {
            totalPage = totalDocument / recordPerPage;
        } else {
            totalPage = totalDocument / recordPerPage + 1;
        }
        if (users.size() > 0) {
            mv.addObject("userList", users);
        }
        mv.addObject("totalPage", totalPage);
        mv.addObject("totalDocument", totalDocument);
        mv.addObject("currentPage", page);
    }

    private void deleteUser(ModelAndView mv, int id) {
        this.userDao.deleteUser(id);
    }

    private void modifyUser(ModelAndView mv, int id) {
        User user = this.userDao.queryUserById(id);
        mv.addObject("mainPage", "admin/userModify.jsp");
        mv.addObject("id", String.valueOf(id));
        mv.addObject("user", user);
    }

    private void addUser(ModelAndView mv) {
        mv.addObject("mainPage", "admin/userModify.jsp");
        mv.addObject("id", null);
        mv.addObject("user", null);
    }

    private void saveUser(ModelAndView mv, HttpServletRequest request) {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String name = request.getParameter("name");
        String tel = request.getParameter("tel");
        String sex = request.getParameter("sex");
        User user = new User(username, password, name, sex, tel);
        String idStr = request.getParameter("id");

        if (idStr != null && idStr.length() != 0) {
            int id = Integer.valueOf(idStr);
            user.setId(id);
            this.userDao.updateUser(user);
        } else {
            this.userDao.addUser(user);
        }

    }

    private void searchUser(ModelAndView mv, HttpServletRequest request) {
        String keyword = request.getParameter("searchType");
        String argument = request.getParameter("searchUser_text");
        mv.addObject("mainPage", "admin/user.jsp");
        assert "name".equals(keyword) || "id".equals(keyword) || "sex".equals(keyword);
        if (argument == null || argument.length() == 0) {
            listUsers(mv, 1);
            return;
        }
        List<User> resultList = new ArrayList<>();
        switch (keyword) {
            case "name":
                resultList = this.userDao.queryUserByName(argument);
                break;
            case "sex":
                resultList = this.userDao.queryUserBySex(Gender.valueOf(argument));
                break;
            case "id":
                resultList = new ArrayList<>();
                User result = this.userDao.queryUserById(Integer.valueOf(argument));
                if (result != null) {
                    resultList.add(result);
                }
            default:
        }
        mv.addObject("userList", resultList);

    }

}
