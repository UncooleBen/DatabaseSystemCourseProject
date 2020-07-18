<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<script type="text/javascript">
    function userDelete(id) {
        if (confirm("您确定要删除这个用户吗？")) {
            window.location = "user?action=delete&id=" + id;
        }
    }

    function checkSearch() {
        var keyword = document.getElementById("searchType").value;
        var argument = document.getElementById("id_search_user_text").value;
        if (keyword == "sex") {
            if (argument != "MALE" && argument != "FEMALE") {
                document.getElementById("error").innerHTML = "性别只能为MALE或FEMALE";
                return false;
            }
        }
        return "user?action=search";
    }
</script>
<style type="text/css">
    .span6 {
        width: 0px;
        height: 0px;
        padding-top: 0px;
        padding-bottom: 0px;
        margin-top: 0px;
        margin-bottom: 0px;
    }

</style>
<div class="data_list">
    <div class="data_list_title" id="#id_title">
        用户管理
    </div>
    <div>
        <table class="table table-striped table-bordered table-hover datatable">
            <thead>
            <tr>
                <th>ID</th>
                <th>用户名</th>
                <th>姓名</th>
                <th>性别</th>
                <th>电话</th>
                <th>操作</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach varStatus="i" var="user" items="${userList }">
                <c:if test="${user.permission == 1}">
                    <tr>
                        <td>${user.id }</td>
                        <td id="#id_username_'${i}'">${user.username }</td>
                        <td>${user.name} </td>
                        <td>${user.sex }</td>
                        <td>${user.tel }</td>
                        <td>
                            <button class="btn btn-mini btn-info" type="button" id="#id_modify_button_${user.id}"
                                    onclick="window.location='user?action=modify&id=${user.id }'">修改
                            </button>&nbsp;
                            <button class="btn btn-mini btn-danger" id="#id_delete_button_${user.id}" type="button"
                                    onclick="userDelete(${user.id })">删除
                            </button>
                        </td>
                    </tr>
                </c:if>
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
        window.location = 'user?action=list&page=' + page;
    }
</script>
</div>
</div>