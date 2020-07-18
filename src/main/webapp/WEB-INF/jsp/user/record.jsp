<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<script type="text/javascript">
    $(document).ready(function () {
        $("ul li:eq(1)").addClass("active");
        $('.form_date').datetimepicker({
            language: 'en',
            weekStart: 1,
            todayBtn: 1,
            autoclose: 1,
            todayHighlight: 1,
            startView: 2,
            minView: 2,
            forceParse: 0
        });
        $('.datatable').dataTable({
            "oLanguage": {
                "sUrl": "/DormManage/media/zh_CN.json"
            },
            "bLengthChange": false, //改变每页显示数据数量
            "bFilter": false, //过滤功能
            "aoColumns": [
                null,
                null,
                null,
                null,
                null,
                {"asSorting": []},
            ]
        });
        $("#DataTables_Table_0_wrapper .row-fluid").remove();
    });

    window.onload = function () {
        $("#DataTables_Table_0_wrapper .row-fluid").remove();
    };

    function recordDelete(recordId) {
        if (confirm("您确定要取消本次预约吗？")) {
            window.location = "record?action=delete&recordId=" + recordId;
        }
    }

    function convertDate(millisecond, id) {
        date = new Date(millisecond);
        document.getElementById(id).innerText = date.toDateString();
    }

    function convertTime(millisecond, id) {
        var day = millisecond / (86400) / 1000;
        document.getElementById(id).innerText = day.toString() + "天";
    }
</script>

<div class="data_list">
    <div class="data_list_title">
        预约记录
    </div>
    <div>
        <table class="table table-striped table-bordered table-hover datatable">
            <thead>
            <tr>
                <th>起始日期</th>
                <th>结束日期</th>
                <th>客户号</th>
                <th>场地号</th>
                <th>使用时间</th>
                <th>审核状态</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach varStatus="i" var="record" items="${recordList }">
                <tr>
                    <td id="unverifiedStartDate${i.index}">
                        <script>convertDate(${record.startDate },
                            "unverifiedStartDate${i.index}")</script>
                    </td>
                    <td id="unverifiedEndDate${i.index}">
                        <script>convertDate(${record.endDate},
                            "unverifiedEndDate${i.index}")</script>
                    </td>
                    <td>${record.userId }</td>
                    <td>${record.buildingId==null?"无":record.buildingId }</td>
                    <td id="unverifiedInterval${i.index}">
                        <script>convertTime(${record.endDate - record.startDate},
                            "unverifiedInterval${i.index}")</script>
                    </td>
                    <td>${record.verified ? '通过':'未通过'}</td>
                    <td>
                        <button class="btn btn-mini btn-danger" type="button"
                                onclick="recordDelete(${record.id })">取消
                        </button>
                    </td>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
    <div align="center"><font color="red">${error }</font></div>
</div>
