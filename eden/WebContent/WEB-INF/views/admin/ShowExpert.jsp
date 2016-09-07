<%@ page language="java" contentType="text/html; charset=UTF-8"
import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>专家管理</title>
<script type="text/javascript">
	function deleteExpert(acco,currentPage){
		alert("--acco"+acco+":   "+currentPage);
		var flag = window.confirm("您确定删除吗？");
		if(flag){
			location.href = "${pageContext.request.contextPath}/DeleteExpertServlet.do?acco="+acco+"&currentPage="+currentPage;
		}
	}
	function find(){
		var input = document.getElementById("searchid");
		var searchContent = input.value;
		location.href = "${pageContext.request.contextPath}/ShowExpertServlet.do?searchContent="+searchContent;
	}
	function textChange(value){
		if(value.length == 0){
			location.href = "${pageContext.request.contextPath}/ShowExpertServlet.do";
		}
	}
</script>
</head>
<body>
<table width="100%">
<tr>
<td>hello,jack&nbsp;<a href="${pageContext.request.contextPath }/ToAddExpert.do"">添加专家</a>;
<input type="text" name="searchcontent" onkeyup="textChange(this.value)" value="${searchContent}" id="searchid"/> 
<a href="#" onclick="find()">搜索</a>
</td>
</tr>
</table>
<hr />
<table border="1px" width="1200px" align="center">
		<tr>
			<th>专家账号</th>
			<th>专家姓名</th>
			<th>专家性别</th>
			<th>状态</th>
	        <th>个人简介</th>
			<th>访问量</th>
			<th>地址</th>
			<th>头像</th>
			<th>邮箱</th>
			<th>操作</th>
		</tr>
		<c:forEach items="${page.data}" var="expert">
		<tr>
		<td>${expert.expertAccount}</td>
		<td>${expert.expertName}</td>
		<td>${expert.expertSex}</td>
		<td>${expert.expertState}</td>
		<td>${expert.expertIntroduce}</td>
		<td>${expert.expertPv}</td>
		<td>${expert.expertAddress}</td>
		<td>${expert.expertIcon}</td>
		<td>${expert.expertEmail}</td>
		<td>
		<a href="${pageContext.request.contextPath}/ToUpdateExpertServlet.do?acco=${expert.expertAccount}">修改</a>
		<a href="javascript:void(0)"	onclick="deleteExpert(${expert.expertAccount },${page.currentPage})">删除</a>
		</td>
		</tr>
		</c:forEach>
		<tr align="center">
			<td colspan="10">当前${page.currentPage}页 共${page.totalPage}页
				<%--显示上一页 --%>
				<c:choose>
					<c:when test="${page.currentPage == 1}">
					      上一页
					</c:when>
					<c:otherwise>
					  <a href="${pageContext.request.contextPath}/ShowExpertServlet?currentPage=${page.currentPage - 1}&searchContent=${searchContent}">上一页</a>
					</c:otherwise>
				</c:choose>
				<%--显示下一页 --%>
				<c:choose>
					<c:when test="${page.currentPage == page.totalPage}">
					      下一页
					</c:when>
					<c:otherwise>
						<a href="${pageContext.request.contextPath}/ShowExpertServlet?currentPage=${page.currentPage + 1}&searchContent=${searchContent}">下一页</a>
					</c:otherwise>
				</c:choose>
			</td>
		</tr>
	</table>
</body>
</html>