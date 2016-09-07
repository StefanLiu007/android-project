<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script type="text/javascript" src="scripts/jquery/jquery-1.7.1.js"></script>
<link href="style/authority/basic_layout.css" rel="stylesheet"
	type="text/css">
<link href="style/authority/common_style.css" rel="stylesheet"
	type="text/css">
<script type="text/javascript" src="scripts/authority/commonAll.js"></script>
<script type="text/javascript"
	src="scripts/fancybox/jquery.fancybox-1.3.4.js"></script>
<script type="text/javascript"
	src="scripts/fancybox/jquery.fancybox-1.3.4.pack.js"></script>
<link rel="stylesheet" type="text/css"
	href="style/authority/jquery.fancybox-1.3.4.css" media="screen"></link>
<script type="text/javascript"
	src="scripts/artDialog/artDialog.js?skin=default"></script>
<title>表白表操作</title>
<script type="text/javascript">
function deleteComment(commentId,currentPage){
	var flag = window.confirm("您确定删除吗？");
	if(flag){
		//发送请求并删除
		location.href = "${pageContext.request.contextPath}/DeleteCommentServlet.do?commentId="+commentId+"&currentPage="+currentPage;
	}
}
function find(){
	var input = document.getElementById("searchid");
	var searchContent = input.value;
	location.href = "${pageContext.request.contextPath}/ShowCommentServlet.do?searchContent="+searchContent;
}
function textChange(value){
	//alert(value.length);
	if(value.length == 0){
		location.href = "${pageContext.request.contextPath}/ShowCommentServlet.do";
	}
}
</script>
<style>
.alt td {
	background: black !important;
}
</style>
</head>
<body>



	<input type="hidden" name="allIDCheck" value="" id="allIDCheck" />
	<input type="hidden" name="fangyuanEntity.fyXqName" value=""
		id="fyXqName" />
	<div id="container">
		<center>
			<div class="ui_content">
				<div class="ui_text_indent">
					<div id="box_border">
						<div id="box_top">搜索</div>

						<div id="box_center">
							<tr><td>
							   
								
								<td class="ui_text_lt"><input type="text" name="searchcontent" onkeyup="textChange(this.value)" class="ui_input_txt01" value="${searchContent}" id="searchid"/> 
								 <input
									type="button" value="查询" class="ui_input_btn01"
									onclick="find()" /></td>
							</tr>

						</div>

					</div>
				</div>
			</div>
			<div class="ui_content">
				<div class="ui_tb">
					<table class="table" cellspacing="0" cellpadding="0" width="100%"
						align="center" border="0">
						<tr>
							<th>评论编号</th>
							<th>内容编号</th>
							<th>评论者账号</th>
							<th>回复内容</th>
							<th>标题</th>
							<th>被回复的编号</th>
							<th>操作</th>
						</tr>
						<c:choose>
							<c:when test="${empty page.data}">
								<tr bgcolor="#FFFFFF">
									<td colspan="7">没有符合查询条件的记录</td>
								</tr>
							</c:when>
							<c:otherwise>
								<c:forEach items="${page.data}" var="com">
									<!-- 把迭代的每个元素Manager存在在pageScope中，名字叫m -->
									<tr bgcolor="#FFFFFF">
										<td>
											<div align="center">${com.commentId}</div>
										</td>
										<td>
											<div align="center">${com.contentId}</div>
										</td>
										<td>
											<div align="center">${com.userAccount}</div>
										</td>
										<td>
											<div align="center">${com.commentContent}</div>
										</td>
										<td>
											<div align="center">${com.commentTitle}</div>
										</td>
										<td>
											<div align="center">${com.commentReply}</div>
										</td>
										<td><a href="javascript:void(0)"
											onclick="deleteComment(${com.commentId},${page.currentPage})">删除</a>
										</td>
									</tr>
								</c:forEach>
							</c:otherwise>
						</c:choose>
					</table>
				</div>
				<div class="ui_frt">
					<table>
						<tr align="center">
							<td colspan="10">当前${page.currentPage}页 共${page.totalPage}页
								<%--显示上一页 --%> <c:choose>
									<c:when test="${page.currentPage == 1}">
					      上一页
					</c:when>
									<c:otherwise>
										<a
											href="${pageContext.request.contextPath}/ShowCommentServlet?currentPage=${page.currentPage - 1}&searchContent=${searchContent}">上一页</a>
									</c:otherwise>
								</c:choose> <%--显示下一页 --%> <c:choose>
									<c:when test="${page.currentPage == page.totalPage}">
					      下一页
					</c:when>
									<c:otherwise>
										<a
											href="${pageContext.request.contextPath}/ShowCommentServlet?currentPage=${page.currentPage + 1}&searchContent=${searchContent}">下一页</a>
									</c:otherwise>
								</c:choose>
							</td>
						</tr>
					</table>

				</div>

			</div>
		</center>
	</div>





</body>
</html>