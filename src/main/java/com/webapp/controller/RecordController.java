package com.webapp.controller;

import com.webapp.model.Record;
import com.webapp.model.user.User;
import com.webapp.service.database.dao.RecordDao;
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
 * This is the controller for managing booking records.
 *
 * @author Juntao Peng, Shangzhen Li
 */
@Controller
public class RecordController {

    RecordDao recordDao;
    int recordPerPage = 15;

    @Autowired
    public RecordController(RecordDao recordDao) {
        this.recordDao = recordDao;
    }

    @RequestMapping("/record")
    public ModelAndView service(
            @RequestParam(value = "action") String action,
            @RequestParam(value = "page", required = false) Integer page,
            HttpServletRequest request) {
        ModelAndView mv = new ModelAndView();
        HttpSession session = request.getSession();
        String userType = (String) session.getAttribute("currentUserType");
        if ("user".equals(userType)) {
            mv.setViewName("mainUser");
            User user = (User) session.getAttribute("currentUser");
            switch (action) {
                case "add":
                    mv.addObject("mainPage", "user/recordAdd.jsp");
                    break;
                case "save": {
                    saveRecord(mv, user, request);
                    mv.addObject("mainPage", "user/record.jsp");
                    break;
                }
                case "delete": {
                    userDeleteRecord(mv, user, request);
                    mv.addObject("mainPage", "user/record.jsp");
                    break;
                }
                case "list":
                default: {
                    listRecord(mv, page, false, user.getId());
                    mv.addObject("mainPage", "user/record.jsp");
                }
                break;
            }
        } else if ("admin".equals(userType)) {
            mv.setViewName("mainAdmin");
            switch (action) {
                case "verify":
                    int id = Integer.parseInt(request.getParameter("recordId"));
                    Record record = recordDao.queryRecordById(id);
                    record.setVerified(true);
                    recordDao.updateRecord(record);
                    listRecord(mv, 1, true, -1);
                    mv.addObject("mainPage", "admin/record.jsp");
                    break;
                case "delete":
                    int recordId = Integer.parseInt(request.getParameter("recordId"));
                    recordDao.deleteRecord(recordId);
                    listRecord(mv, 1, true, -1);
                    mv.addObject("mainPage", "admin/record.jsp");
                    break;
                case "list":
                default:
                    listRecord(mv, page, true, -1);
                    mv.addObject("mainPage", "admin/record.jsp");
                    break;
            }
        }
        return mv;
    }

    private void saveRecord(ModelAndView mv, User user, HttpServletRequest request) {
        Record record = new Record();
        record.setBuildingId(Integer.parseInt(request.getParameter("buildingId")));
        int userId = user.getId();
        record.setUserId(userId);
        long startDate = Long.parseLong(request.getParameter("startDate"));
        long endDate = Long.parseLong(request.getParameter("endDate"));
        record.setStartDate(startDate);
        record.setEndDate(endDate);
        record.setTime(endDate - startDate);
        record.setVerified(false);
        recordDao.addRecord(record);
        listRecord(mv, 1, false, userId);
    }

    private void userDeleteRecord(ModelAndView mv, User user, HttpServletRequest request) {
        int recordId = Integer.parseInt(request.getParameter("recordId"));
        recordDao.deleteRecord(recordId);
        int userId = user.getId();
        listRecord(mv, 1, false, userId);
    }

    private void listRecord(ModelAndView mv, int page, boolean isAdmin, int userId) {
        int totalDocument;
        if (isAdmin) {
            List<Record> unverifiedRecordList = recordDao.listRecord((page - 1) * recordPerPage, recordPerPage,false);
            if (unverifiedRecordList.size() > 0) {
                mv.addObject("unverifiedRecordList", unverifiedRecordList);
            }
            totalDocument = this.recordDao.queryNumberOfRecords(false);
        } else {
            List<Record> recordList = recordDao.listRecordWithUserId((page - 1) * recordPerPage, recordPerPage,userId);
            if (recordList.size() > 0) {
                mv.addObject("recordList", recordList);
            }
            totalDocument = this.recordDao.queryNumberOfRecordsWithUserId(userId);
        }
        int totalPage;
        if (totalDocument % recordPerPage == 0) {
            totalPage = totalDocument / recordPerPage;
        } else {
            totalPage = totalDocument / recordPerPage + 1;
        }
        mv.addObject("totalPage", totalPage);
        mv.addObject("totalDocument", totalDocument);
        mv.addObject("currentPage", page);
    }
}
