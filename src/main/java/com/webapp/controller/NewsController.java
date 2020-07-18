package com.webapp.controller;

import com.webapp.model.News;
import com.webapp.service.database.dao.NewsDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;

/**
 * This class is a part of Dbsys final project.
 *
 * <p>
 * This is the controller for news.
 *
 * @author Juntao Peng, Shangzhen Li
 */
@Controller
public class NewsController {

    private final NewsDao newsDao;
    int recordPerPage = 15;

    @Autowired
    public NewsController(NewsDao newsDao) {
        this.newsDao = newsDao;
    }

    @RequestMapping("/news")
    public ModelAndView service(@RequestParam("action") String action,
                                @RequestParam(value = "page", required = false) Integer page,
                                HttpServletRequest request) {
        ModelAndView mv = new ModelAndView();
        HttpSession session = request.getSession();
        String currentUserType = (String) session.getAttribute("currentUserType");
        if ("admin".equals(currentUserType)) {
            mv.setViewName("mainAdmin");
            adminNewsService(mv, action, request, page);
        } else if ("user".equals(currentUserType)) {
            mv.setViewName("mainUser");
            userNewsService(mv, action, request, page);
        }
        return mv;
    }

    private void adminNewsService(ModelAndView mv, String action, HttpServletRequest request, Integer page) {
        switch (action) {
            case "add":
                mv.addObject("mainPage", "admin/newsModify.jsp");
                break;
            case "delete": {
                int newsId = Integer.parseInt(request.getParameter("newsId"));
                newsDao.deleteNewsById(newsId);
                listNews(mv, true, 1);
                break;
            }
            case "modify": {
                int newsId = Integer.parseInt(request.getParameter("newsId"));
                News news = newsDao.queryNewsById(newsId);
                mv.addObject("news", news);
                mv.addObject("mainPage", "admin/newsModify.jsp");
                break;
            }
            case "save": {
                String strNewsId = request.getParameter("newsId");
                News news;
                Date time = new Date();
                if (!"".equals(strNewsId)) {
                    // Save action for a modified news
                    int id = Integer.parseInt(strNewsId);
                    news = newsDao.queryNewsById(id);
                    news.setLastModified(time.getTime());
                    news.setId(id);
                    news.setTitle(request.getParameter("title"));
                    news.setAuthor(request.getParameter("author"));
                    news.setDetail(request.getParameter("detail"));
                    newsDao.updateNews(news);
                } else {
                    news = new News();
                    news.setCreated(time.getTime());
                    news.setLastModified(time.getTime());
                    news.setTitle(request.getParameter("title"));
                    news.setAuthor(request.getParameter("author"));
                    news.setDetail(request.getParameter("detail"));
                    System.out.println(request.getParameter("title"));
                    newsDao.insertNews(news);
                }
                listNews(mv, true, 1);
                break;
            }
            case "list":
            default:
                listNews(mv, true, page);
                break;
        }
    }

    private void userNewsService(ModelAndView mv, String action, HttpServletRequest request, Integer page) {
        switch (action) {
            case "detail": {
                int newsId = Integer.parseInt(request.getParameter("newsId"));
                News news = newsDao.queryNewsById(newsId);
                mv.addObject("news", news);
                mv.addObject("mainPage", "user/newsDetail.jsp");
                break;
            }
            case "list":
            default:
                listNews(mv, false, page);
                break;
        }
    }

    private void listNews(ModelAndView mv, boolean isAdmin, int page) {
        if (isAdmin) {
            mv.addObject("mainPage", "admin/news.jsp");
        } else {
            mv.addObject("mainPage", "user/news.jsp");
        }
        List<News> newsList = this.newsDao.listNews((page-1)*recordPerPage, recordPerPage);
        int totalDocument = this.newsDao.queryTotalNews();
        int totalPage;
        if (totalDocument % recordPerPage == 0) {
            totalPage = totalDocument / recordPerPage;
        } else {
            totalPage = totalDocument / recordPerPage + 1;
        }
        if (newsList != null) {
            mv.addObject("newsList", newsList);
        }
        mv.addObject("totalPage", totalPage);
        mv.addObject("totalDocument", totalDocument);
        mv.addObject("currentPage", page);
    }
}
