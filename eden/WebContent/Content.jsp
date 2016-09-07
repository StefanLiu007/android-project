<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="com.eden.domain.*"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
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
<script type="text/javascript" src="js/jquery-2.1.4.js"></script>
<title>内容管理</title>
<script type="text/javascript">
  $(document).ready(function() {
	 $("#jumpNum").click(function() {
		 var p=$("#jumpNumTxt").val();
		 var totalPage=$("#totalPage").val();
		 if (p!=null&&p>0&&p<=totalPage) {
			jumpPage(p);
		}
		
	});
	 
	 $("#search").click(function() {
		 var searchContent=$("#searchContent").val();
		 if (searchContent!="") {
			 window.location.href="${pageContext.request.contextPath}/ShowContentServlet.do?searchContent="+searchContent;
		}
		 
	});
});
	function jumpPage(current) {
		window.location.href="${pageContext.request.contextPath}/ShowContentServlet.do?currentPage="+current;
	}
	function addContent() {
		window.location.href="${pageContext.request.contextPath}/AddContent.jsp";
	}
	function delContent(a,b) {
		var isTrue=window.confirm("确定删除这条信息吗？");
		if (isTrue) {
			window.location.href="${pageContext.request.contextPath}/DeleteContentServlet.do?contentId="+a+"&currentPage="+b;
		return true;
		}
	}
	function textChange(value){
		if (value==0) {
			window.location.href="${pageContext.request.contextPath}/ShowContentServlet.do";
		}
	}
	function dele(d) {
		var isTrue=window.confirm("确定删除这些信息吗？");
		var id = document.getElementsByName('IDCheck');
		var value = new Array();
		for(var i = 0; i < id.length; i++){
	         if(id[i].checked)
	         value.push(id[i].value);
	        }  
		if (isTrue) {
			window.location.href="${pageContext.request.contextPath}/DeleteContentServlet.do?checkId="+value.toString()+"&currentPage="+d;
		    return true;
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
	<div id="container">
		<div class="ui_content">
			<div class="ui_text_indent">
				<div id="box_border">
					<div id="box_bottom">
						<input type="text" id="searchContent"
							onkeyup="textChange(this.value)" value="${searchContent}"
							placeholder="请输入要查询的标题" /> <input type="button" value="查询"
							class="ui_input_btn01" id="search" /> <input type="button"
							value="新增" class="ui_input_btn01" onclick="addContent();" /> <input
							type="button" value="删除" class="ui_input_btn01"
							onclick="dele('${ContentPage.currentPage}')" />
					</div>
				</div>
			</div>
		</div>
		<div class="ui_content">
			<div class="ui_tb">
				<table class="table" cellspacing="0" cellpadding="0" width="100%"
					align="center" border="0">
					<tr>
						<th width="30"><input type="checkbox" id="all"
							onclick="selectOrClearAllCheckbox(this);" /></th>
						<th>用户帐号</th>
						<th>标题</th>
						<th>视频</th>
						<th>图片</th>
						<th>文字</th>
						<th>点赞次数</th>
						<th>最后修改时间</th>
						<th>操作</th>
					</tr>
					<c:forEach items="${ContentPage.contents}" var="content">
						<tr>
							<td><input type="checkbox" name="IDCheck"
								value="${content.contentId}" class="acb" /></td>
							<td>${content.userAccount}</td>
							<td>${content.contentTitle}</td>
							<td><img src="upload/${content.contentVideo}" width="120"
								height="90" border="1" /></td>
							<td><img src="upload/${content.contentPicture}" width="120"
								height="90" border="1" /></td>
							<td>${content.contentText}</td>
							<td>${content.thumbUpNum}</td>
							<td>${content.contentLastTime}</td>
							<td><a
								href="${pageContext.request.contextPath}/ToUpdateContentServlet.do?contentId=${content.contentId}"
								class="edit">编辑</a> <a href="#"
								onclick="delContent('${content.contentId}','${ContentPage.currentPage}')"
								class="edit">删除</a></td>
						</tr>
					</c:forEach>
				</table>
			</div>
			<div class="ui_tb_h30">
				<div class="ui_flt" style="height: 30px; line-height: 30px;">
					共有 <span class="ui_txt_bold04">${ContentPage.totalPage }</span>
					页记录，当前第 <span class="ui_txt_bold04">${ContentPage.currentPage}</span>
					页
				</div>
				<div class="ui_frt">
					<!--    如果是第一页，则只显示下一页、尾页 -->
					<c:choose>
						<c:when test="${ContentPage.currentPage==1}">

							<input type="button" value="上一页" class="ui_input_btn01" />
						</c:when>
						<c:otherwise>

							<input type="button" value="上一页" class="ui_input_btn01"
								onclick="jumpPage(${ContentPage.currentPage-1})" />
						</c:otherwise>
					</c:choose>
					<c:choose>
						<c:when test="${ContentPage.currentPage==ContentPage.totalPage}">
							<input type="button" value="下一页" class="ui_input_btn01" />

						</c:when>
						<c:otherwise>
							<input type="button" value="下一页" class="ui_input_btn01"
								onclick="jumpPage(${ContentPage.currentPage+1})" />

						</c:otherwise>
					</c:choose>

					<!--     如果是最后一页，则只显示首页、上一页 -->
					<input type="hidden" id="totalPage"
						value="${ContentPage.totalPage}"> 转到第<input type="text"
						id="jumpNumTxt" class="ui_input_txt01" name="jumpNumTxt" />页 <input
						type="button" class="ui_input_btn01" value="跳转" id="jumpNum" />
				</div>
			</div>
		</div>
	</div>
</body>
</html>
