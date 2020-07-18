<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"
         isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<script>
    function convertDate(millisecond, id) {
        date = new Date(millisecond);
        document.getElementById(id).innerText = date.toDateString();
    }
</script>
<div class="data_list">
    <div class="data_list_title">
        场地查看
    </div>
    <div class="buildingBox">
        <div class="buildingName" id="name">${building.name}</div>
        <div class="buildingPrice" id="price">${building.price }</div>
        <div class="data_box_text" id="description">${building.description }</div>
    </div>
    <button class="btn btn-mini btn-info" type="button" onclick="window.location='comment?action=add&buildingId='+${building.id}">我要留言</button>&nbsp;
</div>
<c:forEach varStatus="i" var="comment" items="${commentList }">
    <div class="news">
        <div class="right">
            <div class="data_box_title">
                <p>${comment.userId }</p>
            </div>
            <div class="data_box_text">
                <p>${comment.content }</p>
            </div>
            <div class="data_box_text">
                <p id="createdTime${i.index}">
                    <script>convertDate(${comment.date }, "createdTime${i.index}")</script>
                </p>
            </div>
        </div>
    </div>
</c:forEach>
</html>
