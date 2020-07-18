<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<script type="text/javascript">
    function checkForm() {
        var content = document.getElementById("content").value;
        // var date = document.getElementById("date").value;
        // if (date == null || date === "") {
        //     document.getElementById("error").innerHTML = "日期不能为空！";
        //     return false;
        // }
        if (content == null || content === "") {
            document.getElementById("error").innerHTML = "内容不能为空！";
            return false;
        }
        return true;
    }

    $(document).ready(function () {
        $("ul li:eq(2)").addClass("active");
    });
</script>
<div class="data_list">
    <div class="data_list_title">
        <c:choose>
            <c:when test="${user.studentId!=null }">
                留言
            </c:when>
            <c:otherwise>
                留言
            </c:otherwise>
        </c:choose>
    </div>
    <form action="comment?action=save" method="post" onsubmit="return checkForm()">
        <div>
            <textarea id="buildingId" name="buildingId" style="display:none;">${buildingId}</textarea>
            <textarea id="content" name="content" class="Mytextarea"></textarea>
            <div align="left">
                <input type="submit" class="btn btn-primary" value="发表"/>
            </button>
            </div>
            <div align="left">
                <font id="error" color="red">${error }</font>
            </div>
        </div>
    </form>
</div>