<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<script type="text/javascript">
    function commentDelete(id) {
        if (confirm("您确定要删除这条留言吗？")) {
            window.location = "comment?action=delete&id=" + id;
        }
    }

    function commentApprove(id) {
        if (confirm("您确定要通过这条留言吗？")) {
            window.location = "comment?action=verify&id=" + id;
        }
    }

    function convertDate(millisecond, id) {
        date = new Date(millisecond);
        document.getElementById(id).innerText = date.toDateString();
    }
</script>
<div class="data_list">
    <div class="data_list_title">
        待审核留言
    </div>
    <div>
        <table class="table table-striped table-bordered table-hover datatable">
            <thead>
            <tr>
                <th>日期</th>
                <th>客户</th>
                <th>留言内容</th>
                <th>操作</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach varStatus="unverifiedCommentListLoop" var="comment"
                       items="${unverifiedCommentList }">
                <tr>
                    <td id="unverifiedCommentListLoop${unverifiedCommentListLoop.index}">
                        <script>convertDate(${comment.date },
                            "unverifiedCommentListLoop${unverifiedCommentListLoop.index}");</script>
                    </td>
                    <td>${comment.userId }</td>
                    <td>${comment.content }</td>
                    <td>
                        <button class="btn btn-mini btn-danger" type="button"
                                id="verify${comment.id}" onclick="commentApprove(${comment.id })">
                            通过
                        </button>
                        <button class="btn btn-mini btn-danger" type="button"
                                id="deleteUnverified${comment.id}"
                                onclick="commentDelete(${comment.id })">
                            删除
                        </button>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
</div>
<nav class="gj-page">
    <span class="pageinfo">第<strong>${currentPage}</strong>页    共<strong>${totalPage}</strong>页    <strong>${totalDocument}</strong>条记录</span>
    <a href="javascript:gotoPage(1)" class="homepage">首页</a>
    <a href="javascript:gotoPage(${currentPage-1})" class="nopage">上页</a>
    <a href="javascript:gotoPage(${currentPage+1})" class="nopage">下页</a>
    <a href="javascript:gotoPage(${totalPage})" class="endpage">尾页</a>
</nav>
<script>
    function gotoPage(page) {
        if (page >${totalPage}) {
            page = ${totalPage};
        }
        if (page == 0) {
            page = 1;
        }
        if (page == ${currentPage}) {
            return;
        }
        window.location = 'comment?action=list&page=' + page;
    }
</script>
</div>
</div>