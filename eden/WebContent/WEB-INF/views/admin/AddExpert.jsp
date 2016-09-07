<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>添加账户</title>
</head>
<body>
<form method="post" action="${pageContext.request.contextPath }/AddExpertServlet.do">
	专家账号:<input type="text" name="EXPERT_ACCOUNT" /><br />
	专家姓名:<input type="text" name="EXPERT_NAME"/><br />
	专家性别:<input type="radio" name="EXPERT_SEX" checked/>男
	      <input type="radio" name="EXPERT_SEX" />女<br />
	<!-- 状态:<input type="text" name="EXPERT_STATE" maxlength="2" /><br /> -->
	状态:<select name="EXPERT_STATE">
	<option value="在线">在线</option>
	<option value="离开">离开</option>
	<option value="离线">离线</option>
	</select></br>
	个人简介:<input type="text" name="EXPERT_INTRODUCE" /><br />
	访问量:<input type="text" name="EXPERT_PV" /><br />
	地址:<input type="text" name="EXPERT_ADDRESS" /><br />
	头像:<input type="file" name="ERTERT_ICON" /><br />
	邮箱:<input type="text" name="ERTERT_EMAIL" /><br />
	<input type="hidden" name="hexpertAccount" />
	<input type="submit" value="添加"/>
</form>
</body>
</html>