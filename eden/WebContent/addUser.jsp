<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="com.eden.domain.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>添加用户</title>
</head>
<body>
<form method="post" action="${pageContext.request.contextPath }/AddUserServlet.do">
	用户账号:<input type="text" name="USER_ACCOUNT" /><br />
	昵称:<input type="text" name="USER_NICKNAME"/><br />
	密码:<input type="password" name="USER_PASSWORD" /><br />
	姓名:<input type="text" name="USER_NAME" /><br />
	个性签名:<input type="text" name="USER_SIGNATURE" /><br />
	出生日期:<input type="date" name="USER_BIRTHDATE" /><br />
	性别:<input type="radio" name="USER_SEX" checked value="男"/>男
	  <input type="radio" name="USER_SEX" value="女" />女<br />
	学校:<input type="text" name="USER_SCHOOL"/><br />
	电话:<input type="text" name="USER_MOBILE"/><br />
	邮箱:<input type="text" name="USER_EMAIL" /><br />
	头像:<input type="file" name="USER_ICON" /><br /><br/>
	<input type="hidden" name="huserAccount" />
	<input type="submit" value="添加"/>
</form>
</body>
</html>