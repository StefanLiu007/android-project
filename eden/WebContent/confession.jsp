<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script type="text/javascript" src="scripts/jquery/jquery-1.7.1.js"></script>
<link href="style/authority/basic_layout.css" rel="stylesheet" type="text/css">
<link href="style/authority/common_style.css" rel="stylesheet" type="text/css">
<script type="text/javascript" src="scripts/authority/commonAll.js"></script>
<script type="text/javascript" src="scripts/fancybox/jquery.fancybox-1.3.4.js"></script>
<script type="text/javascript" src="scripts/fancybox/jquery.fancybox-1.3.4.pack.js"></script>
<link rel="stylesheet" type="text/css" href="style/authority/jquery.fancybox-1.3.4.css" media="screen"></link>
<script type="text/javascript" src="scripts/artDialog/artDialog.js?skin=default"></script>
<title>表白表操作</title>
<script type="text/javascript">
function deleteManager(mid,cpage) {
	if(confirm("确定要删除吗？")){
		  
	  window.location="${pageContext.request.contextPath}/DeleteConfessionServlet.do?id="+mid+"&page="+cpage;
	}
	}
function search(){
	var input = document.getElementById("fyCh");
	var searchContent = input.value;
	location.href = "${pageContext.request.contextPath}/ShowConfessionServlet.do?page=1&searchContent="+searchContent;
	
}
function search1(){
	var input = document.getElementById("fyCh1");
	var searchContent = input.value;
	location.href = "${pageContext.request.contextPath}/ShowConfessionServlet.do?acode=4&page=1&searchContent="+searchContent;
	
}
function textChang(value){
	if(value.length == 0){
		location.href="${pageContext.request.contextPath}/ShowConfessionServlet.do?page=1";
	}
}
</script>
<script type="text/javascript" src="js/page.js"></script>
<style>
	.alt td{ background:black !important;}
</style>
</head>
<body>
	
	
	
		<input type="hidden" name="allIDCheck" value="" id="allIDCheck"/>
		<input type="hidden" name="fangyuanEntity.fyXqName" value="" id="fyXqName"/>
		<div id="container">
	        <center>
			<div class="ui_content">
				<div class="ui_text_indent">
					<div id="box_border">
						<div id="box_top">搜索</div>
						 
						<div id="box_center">
					   
							
							<tr>
					<td class="ui_text_rt">按表白时间查找</td>
					<td class="ui_text_lt">
						<input type="date"  id="fyCh1" name="searchContent1" value="${s}"  class="ui_input_txt01" />
						<input type="button" value="查询"  class="ui_input_btn01" onclick="search1();" /> 
					</td>
				</tr>

							
								<tr>
					<td class="ui_text_rt">根据表白人查找</td>
					<td class="ui_text_lt">
						<input type="text" id="fyCh" name="searchContent" value="${s}" onkeyup="textChang(this.value)"  class="ui_input_txt01"/>
						<input type="button" value="查询"  class="ui_input_btn01" onclick="search();" /> 
					</td>
				</tr>
                           
						</div>
						
					</div>
				</div>
			</div>
			<div class="ui_content">
				<div class="ui_tb">
					<table class="table" cellspacing="0" cellpadding="0" width="100%" align="center" border="0">
						<tr>
							<th>ID</th>
							<th>表白人</th>
							<th>表白内容</th>
							<th>表白时间</th>
							<th>点赞数</th>
							<th>操作</th>
						</tr>
							<c:choose>
					<c:when test="${empty pb.data}">
						<tr bgcolor="#FFFFFF">
							<td colspan="7">没有符合查询条件的记录</td>
						</tr>
					</c:when>
					<c:otherwise>
						<c:forEach items="${pb.data}" var="m">
							<!-- 把迭代的每个元素Manager存在在pageScope中，名字叫m -->
							<tr bgcolor="#FFFFFF">
								<td>
									<div align="center">
										<a href="${pageContext.request.contextPath}/EditManagerServlet?acode=3&mid=${m.id}">${m.id}</a>
									</div>
								</td>
								<td>
									<div align="center">${m.userName}</div>
								</td>
								<td>
									<div align="center">${m.confessionContent}</div>
								</td>
								<td>
									<div align="center">${m.modifyTime}</div>
								</td>
								<td>
									<div align="center">${m.zan}</div>
								</td>
								<td>
									<div align="center">
										<input type="hidden" name="id" value="xxxx" /> 
										<a href="house_edit.html?fyID=14458625716623" class="edit">编辑</a> 
										<a href="javascript:deleteManager('${m.id}','${pb.currentPage}')">删除</a>&nbsp;
										
									</div>
								</td>
							</tr>
						</c:forEach>
					</c:otherwise>
				</c:choose>	
					</table>
				</div>
					<div class="ui_frt">
						<jsp:include page="/common/pagenavigation.jsp">
					   <jsp:param value="${pageContext.request.contextPath}/ShowConfessionServlet.do" name="url"/>
					</jsp:include>
					</div>
					
				</div>
				 </center>
			</div>
			
		
		
	

</body>
</html>