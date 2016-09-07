<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="com.eden.domain.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>修改用户信息</title>
</head>
<body>
<form method="post" action="${pageContext.request.contextPath }/UpdateUserServlet.do">
	用户账号:<input type="text" name="USER_ACCOUNT" value="${user.userAccount}" disabled="disabled"/><br />
	昵称:<input type="text" name="USER_NICKNAME"  value="${user.userNickname}"/><br />
	密码:<input type="password" name="USER_PASSWORD" value="${user.userPassword}"/><br />
	姓名:<input type="text" name="USER_NAME" value="${user.userName}"/><br />
	个性签名:<input type="text" name="USER_SIGNATURE" value="${user.userSignature}"/><br />
	出生日期:<input type="date" name="USER_BIRTHDATE" value="${user.userBirthday}"/><br />
	性别:<input type="radio" name="USER_SEX" value="男" ${user.userSex == "男" ? "checked":""}/>男<input type="radio" name="USER_SEX" value="女" ${user.userSex == "女" ? "checked":""}/>女<br />
	学校:<input type="text" name="USER_SCHOOL" value="${user.userSchool}"/><br />
	电话:<input type="text" name="USER_MOBILE" value="${user.userMobile}"/><br />
	邮箱:<input type="text" name="USER_EMAIL"  value="${user.userMail}"/><br />
	头像:<input type="file" name="USER_ICON"  value="${user.userIcon}"/><br />
	<input type="hidden" name="huserAccount" value="${user.userAccount}"/>
	<input type="submit" value="修改"/>
</form>
</body>
</html>