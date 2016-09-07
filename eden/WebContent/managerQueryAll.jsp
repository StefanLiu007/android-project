<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

<title>Basic CRUD Application - jQuery EasyUI CRUD Demo</title>
<link href="css/shopping.css" rel="stylesheet" type="text/css">
<script type="text/javascript" src="js/page.js"></script>
<script Language="JavaScript">

function deleteManager(mid,cpage) {
if(confirm("确定要删除吗？")){
	  
  window.location="${pageContext.request.contextPath}/DeleteManagerServlet?id="+mid+"&page="+cpage;
}
}
</script>
</head>
<body>
	<div id="container">
	<center>
		<br>
			<table width="559" height="26" background="image/02.png">
				<tr>
					<td align="center"><font size="3" color="#F36D13" face="宋体"><strong>管理员信息</strong>
					</font></td>
				</tr>
			</table>
			<br> <br>
			<table width="510" height="26" border="0">
				<tr>
					<td align="right"><img src="image/add.gif" /> &nbsp; <a
						href="managerRegister.jsp"><font size="2"
							color="#334502" face="宋体">添加管理员</font> </a>&nbsp;&nbsp; <img
						src="image/shanchu.gif" /> &nbsp; <a href="."><font size="2"
							color="#334502" face="宋体">清空管理员</font> </a></td>
				</tr>
			</table>
			<table width="510" id="table2" border="1" cellpadding="0"
				cellspacing="0" bordercolor="#D3DABF" bgcolor="#5DC3F8">
				<tr>
					<td width="30%" height="28">
						<div align="center">管理员账户</div>
					</td>
					<td width="30%">
						<div align="center">管理员姓名</div>
					</td>
					<td width="30%">
						<div align="center">操作</div>
					</td>
				</tr>
				<!-- 
						   if(查询的结果为空) 提示：没有符合条件的记录
						   else 把结果跌代出来  
						   在JSTL中具有if...else功能的标签就是choose...when...otherwise
						 -->
				<c:choose>
					<c:when test="${empty pb.data}">
						<tr bgcolor="#FFFFFF">
							<td colspan="3">没有符合查询条件的记录</td>
						</tr>
					</c:when>
					<c:otherwise>
						<c:forEach items="${pb.data}" var="m">
							<!-- 把迭代的每个元素Manager存在在pageScope中，名字叫m -->
							<tr bgcolor="#FFFFFF">
								<td>
									<div align="center">
										<a href="${pageContext.request.contextPath}/EditManagerServlet?acode=3&mid=${m.id}">${m.account}</a>
									</div>
								</td>
								<td>
									<div align="center">${m.name}</div>
								</td>
								<td>
									<div align="center">
										<input type="hidden" name="id" value="xxxx" /> <a
											href="javascript:deleteManager('${m.id}','${pb.currentPage}')">删除</a>&nbsp;
									</div>
								</td>
							</tr>
						</c:forEach>
					</c:otherwise>
				</c:choose>
				
			</table>
			<br />
			
		
		<jsp:include page="/common/pagenavigation.jsp">
					   <jsp:param value="ManagerQueryAllServlet" name="url"/>
					  
					</jsp:include>
		</center>
		</div>
</body>
</html>