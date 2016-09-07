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
function deleteProblem(id,currentPage){
	alert("删除的ID是"+id+":   "+currentPage);
	var flag = window.confirm("您确定删除吗？");
	if(flag){
		location.href = "${pageContext.request.contextPath}/DeleteProblemServlet.do?id="+id+"&currentPage="+currentPage;
	}
}
function find(){
	var input = document.getElementById("searchid");
	var searchContent = input.value;
	location.href = "${pageContext.request.contextPath}/ShowProblemServlet.do?searchContent="+searchContent;
}
function textChange(value){
	if(value.length == 0){
		location.href = "${pageContext.request.contextPath}/ShowProblemServlet.do";
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
							    <a href="${pageContext.request.contextPath }/ToAddExpert.do"">添加专家</a></td>
								<td class="ui_text_rt">根据表白人查找</td>
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
							<th>问题编号</th>
							<th>用户账号</th>
							<th>问题内容</th>
							<th>图片</th>
							<th>时间</th>
							<th>评论数</th>
							<th>操作</th>
						</tr>
						<c:choose>
							<c:when test="${empty page.data}">
								<tr bgcolor="#FFFFFF">
									<td colspan="7">没有符合查询条件的记录</td>
								</tr>
							</c:when>
							<c:otherwise>
								<c:forEach items="${page.data}" var="problem">
									<!-- 把迭代的每个元素Manager存在在pageScope中，名字叫m -->
									<tr bgcolor="#FFFFFF">
										<td>
											<div align="center">${problem.problemID}</div>
										</td>
										<td>
											<div align="center">${problem.userAccou}</div>
										</td>
										<td>
											<div align="center">${problem.problemContent}</div>
										</td>
										<td>
											<div align="center">${problem.problemImage}</div>
										</td>
										<td>
											<div align="center">${problem.problemLastTime}</div>
										</td>
										<td>
											<div align="center">${problem.problemAnswerNum}</div>
										</td>
										<td><a href="javascript:void(0)"
											onclick="deleteProblem(${problem.problemID },${page.currentPage})">删除</a>
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
											href="${pageContext.request.contextPath}/ShowExpertServlet?currentPage=${page.currentPage - 1}&searchContent=${searchContent}">上一页</a>
									</c:otherwise>
								</c:choose> <%--显示下一页 --%> <c:choose>
									<c:when test="${page.currentPage == page.totalPage}">
					      下一页
					</c:when>
									<c:otherwise>
										<a
											href="${pageContext.request.contextPath}/ShowExpertServlet?currentPage=${page.currentPage + 1}&searchContent=${searchContent}">下一页</a>
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