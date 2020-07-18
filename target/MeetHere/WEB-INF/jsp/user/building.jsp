<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<script type="text/javascript">
    function commentDelete(commentId) {
        if (confirm("您确定要删除这条留言吗？")) {
            window.location = "comment?action=delete&commentId=" + commentId;
        }
    }

    function convertDate(millisecond, id) {
        date = new Date(millisecond);
        document.getElementById(id).innerText = date.toDateString();
    }

    function newsDetail(newsID) {
        window.location = "building?action=detail&buildingId=" + newsID;
    }
</script>

<div class="data_list">
    <div class="data_list_title" id="#id_title">
        场地介绍
    </div>
    <div class="data_box">
        <c:forEach varStatus="i" var="building" items="${buildingList }">
            <div class="building">
                <div class="image">
                    <img src="resources/images/building.jpg">
                </div>
                <div class="right">
                    <div class="data_box_title">
                        <p>${building.name}</p>
                    </div>
                    <div class="btn" id="detail${building.id}" onclick=newsDetail(${building.id })>查看</div>
                </div>
            </div>
        </c:forEach>
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
        window.location = 'news?action=list&page=' + page;
    }
</script>
</div>
</div>