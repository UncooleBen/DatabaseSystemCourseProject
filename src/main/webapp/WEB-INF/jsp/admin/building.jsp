<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<script type="text/javascript">
    function buildDelete(buildingId) {
        if (confirm("您确定要删除这个楼吗？")) {
            window.location = "building?action=delete&id=" + buildingId;
        }
    }
</script>
<div class="data_list">
    <div class="data_list_title" id="#id_title">
        场地管理
    </div>
    <div>
        <table class="table table-striped table-bordered table-hover datatable">
            <thead>
            <button class="btn btn-mini btn-info" type="button" id="#id_add_button"
                    onclick="window.location='building?action=add'">添加场地
            </button>
            <tr>
                <th width="15%">编号</th>
                <th>名称</th>
                <th>简介</th>
                <th>价格</th>
                <th width="20%">操作</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach varStatus="i" var="building" items="${buildingList }">
                <tr>
                    <td>${i.count+(page-1)*pageSize }</td>
                    <td>${building.name }</td>
                    <td>${building.description==null || building.description=="" ? "无":building.description }</td>
                    <td>${building.price==null || building.price=="" ? "无":building.price }</td>
                    <td>
                        <button class="btn btn-mini btn-info" type="button" id="#id_modify_button_${building.id}"
                                onclick="window.location='building?action=modify&id=${building.id }'">
                            修改
                        </button>&nbsp;
                        <button class="btn btn-mini btn-danger" type="button" id="#id_delete_button_${building.id}"
                                onclick="buildDelete(${building.id})">删除
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
        window.location = 'building?action=list&page=' + page;
    }
</script>
</div>
</div>