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
<title>用户管理</title>
<script type="text/javascript">
function deleteUser(userAccount,currentPage){
	 var nn = window.confirm("您确定删除吗？");
	 if(nn){
		 location.href = "${pageContext.request.contextPath}/DeleteUserServlet.do?userAccount="+userAccount+"&currentPage="+currentPage;
	 }
}
function find(){
	var input = document.getElementById("searchuserAccount");
	var searchContent = input.value;
	location.href = "${pageContext.request.contextPath}/ShowUserServlet.do?searchContent="+searchContent;
}
 function textChange(value){
	if(value.length == 0){
		location.href = "${pageContext.request.contextPath}/ShowUserServlet.do";
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
						<div id="box_top">用户管理</div>
						 
						<div id="box_center">
					   
							
						<!-- 	<tr>
					<td class="ui_text_rt">按表白时间查找</td>
					<td class="ui_text_lt">
						<input type="date"  id="fyCh" name="fangyuanEntity.fyCh"  class="ui_input_txt01" />
						<input type="button" value="查询"  class="ui_input_btn01" onclick="search();" /> 
					</td>
				</tr> -->
				
					<table  width="100%">
              <tr> 
              <td align="center">
              <a href="${pageContext.request.contextPath}/ToAddUserServlet.do?userAccount=${user.userAccount}">添加</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
              <input type="text" name="searchcontent" onkeyup="textChange(this.value)" value="${searchContent}" id="searchuserAccount"/> <a href="#" onclick="find()">搜索</a>
              </td>   
          </tr>
     </table>
                      <hr/>     
						</div>
						
					</div>
				</div>
			</div>
			<div class="ui_content">
				<div class="ui_tb">
					<table class="table" cellspacing="0" cellpadding="0" width="100%" align="center" border="0">
						<tr>
							<th>用户账号</th>
							<th>昵称</th>
							<th>密码</th>
							<th>用户名</th>
							<th>个性签名</th>
							<th>出生日期</th>
							<th>性别</th>
							<th>学校</th>
							<th>手机号</th>
							<th>邮箱</th>
							<th>头像</th>
							<th>操作</th>
						</tr>
							<c:choose>
					<c:when test="${empty pb.data}">
						<tr bgcolor="#FFFFFF">
							<td colspan="12">没有符合查询条件的记录</td>
						</tr>
					</c:when>
					<c:otherwise>
						<c:forEach items="${pb.data}" var="user">
							<!-- 把迭代的每个元素Manager存在在pageScope中，名字叫m -->
							<tr bgcolor="#FFFFFF">
								<%-- <td>
									<div align="center">
										<a href="${pageContext.request.contextPath}/EditManagerServlet?acode=3&userAccount=${user.userAccount}"></a>
									</div>
								</td> --%>
								<td>
									<div align="center">${user.userAccount}</div>
								</td>
								<td>
									<div align="center">${user.userNickname}</div>
								</td>
								<td>
									<div align="center">${user.userPassword}</div>
								</td>
								<td>
									<div align="center">${user.userName}</div>
								</td>
								<td>
									<div align="center">${user.userSignature}</div>
								</td>
								<td>
									<div align="center">${user.userBirthday}</div>
								</td>
								<td>
									<div align="center">${user.userSex}</div>
								</td>
								<td>
									<div align="center">${user.userSchool}</div>
								</td>
								<td>
									<div align="center">${user.userMobile}</div>
								</td>
								<td>
									<div align="center">${user.userMail}</div>
								</td>
								<td>
									<div align="center">${user.userIcon}</div>
								</td>
								<td>
									<div align="center">		
			                           <a href="${pageContext.request.contextPath}/ToUpdateUserServlet.do?userAccount=${user.userAccount}">修改</a>
				                       <a href="javascript:void(0)"onclick="deleteUser(${user.userAccount},${pb.currentPage})">删除</a>
		                     	    	
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
					   <jsp:param value="${pageContext.request.contextPath}/ShowUserServlet" name="url"/>
					  
					</jsp:include>
					</div>
					
				</div>
				 </center>
			</div>

</body>
</html>