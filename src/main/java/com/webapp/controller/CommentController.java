package com.webapp.controller;

import com.webapp.filter.LoginFilter;
import com.webapp.model.Comment;
import com.webapp.model.user.User;
import com.webapp.service.database.dao.CommentDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * This class is a part of Dbsys final project.
 *
 * <p>
 * This is the controller for comment.
 *
 * @author Juntao Peng, Shangzhen Li
 */
@Controller
public class CommentController {

    CommentDao commentDao;
    int recordPerPage = 15;

    @Autowired
    public CommentController(CommentDao commentDao) {
        this.commentDao = commentDao;
    }

    @RequestMapping("/comment")
    public ModelAndView service(
            @RequestParam("action") String action,
            @RequestParam(value = "page", required = false) Integer page,
            HttpServletRequest request,
            HttpSession session) {
        ModelAndView mv = new ModelAndView();
        String currentUserType = (String) session.getAttribute("currentUserType");
        boolean isAuthorized =
                LoginFilter.isAuthorized(currentUserType, "user", mv); /* Filter not login*/
        if (!isAuthorized) {
            return mv;
        }
        if (!"list".equals(action) && !"add".equals(action) && !"save".equals(action)) {
            isAuthorized = LoginFilter.isAuthorized(currentUserType, "admin", mv); /* Filter not login*/
            if (!isAuthorized) {
                return mv;
            }
        }
        String idStr = request.getParameter("id");
        int id = 0;
        if (idStr != null && idStr.length() != 0) {
            id = Integer.parseInt(idStr);
        }
        switch (action) {
            case "delete":
                mv.setViewName("mainAdmin");
                deleteComment(mv, id);
                listUnverifiedComments(mv, 1);
                break;
            case "verify":
                mv.setViewName("mainAdmin");
                mv.addObject("mainPage", "admin/comment.jsp");
                verifyComment(mv, id);
                listUnverifiedComments(mv, 1);
                break;
            case "add":
                mv.setViewName("mainUser");
                addComment(mv, request);
                break;
            case "save":
                mv.setViewName("mainUser");
                saveComment(mv, request, session);
                break;
            case "list":
            default:
                if ("admin".equals(currentUserType)) {
                    mv.setViewName("mainAdmin");
                    mv.addObject("mainPage", "admin/comment.jsp");
                    listUnverifiedComments(mv, page);
                } else {
                    mv.setViewName("mainUser");
                    mv.addObject("mainPage", "user/building.jsp");
                    listVerifiedComments(mv, page);
                }
        }
        return mv;
    }

    public void verifyComment(ModelAndView mv, int id) {
        Comment comment = this.commentDao.queryCommentById(id);
        comment.setVerified(true);
        this.commentDao.updateComment(comment);
    }

    public void listVerifiedComments(ModelAndView mv, int page) {
        List<Comment> verifiedCommentList = this.commentDao.listComment((page - 1) * recordPerPage, recordPerPage, true);
        int totalDocument = this.commentDao.queryNumberOfComments(true);
        int totalPage;
        if (totalDocument % recordPerPage == 0) {
            totalPage = totalDocument / recordPerPage;
        } else {
            totalPage = totalDocument / recordPerPage + 1;
        }
        if (verifiedCommentList.size() > 0) {
            mv.addObject("commentList", verifiedCommentList);
        }
        mv.addObject("totalPage", totalPage);
        mv.addObject("totalDocument", totalDocument);
        mv.addObject("currentPage", page);
    }

    public void listUnverifiedComments(ModelAndView mv, int page) {
        List<Comment> unverifiedCommentList = this.commentDao.listComment((page - 1) * recordPerPage, recordPerPage, false);
        int totalDocument = this.commentDao.queryNumberOfComments(false);
        int totalPage;
        if (totalDocument % recordPerPage == 0) {
            totalPage = totalDocument / recordPerPage;
        } else {
            totalPage = totalDocument / recordPerPage + 1;
        }
        if (unverifiedCommentList.size() > 0) {
            mv.addObject("unverifiedCommentList", unverifiedCommentList);
        }
        mv.addObject("totalPage", totalPage);
        mv.addObject("totalDocument", totalDocument);
        mv.addObject("currentPage", page);
    }

    public void deleteComment(ModelAndView mv, int id) {
        this.commentDao.deleteComment(id);
    }

    public void addComment(ModelAndView mv, HttpServletRequest request) {
        mv.addObject("buildingId", request.getParameter("buildingId"));
        mv.addObject("mainPage", "user/buildingCommentSave.jsp");
    }

    public void saveComment(ModelAndView mv, HttpServletRequest request, HttpSession session) {
        User currentUser = (User) session.getAttribute("currentUser");
        int userId = currentUser.getId();
        long date = System.currentTimeMillis();
        int buildingId = Integer.parseInt(request.getParameter("buildingId"));
        String content = request.getParameter("content");
        Comment comment = new Comment(userId,buildingId, date, content, false);
        this.commentDao.addComment(comment);
        mv.setViewName("redirect:/building?action=detail&buildingId="+buildingId);
    }
}
