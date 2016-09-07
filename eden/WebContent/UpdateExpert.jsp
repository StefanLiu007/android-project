<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" "%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
<form method="post" action="${pageContext.request.contextPath }/UpdateExpertServlet.do">
	专家账号:<input type="text" name="EXPERT_ACCOUNT" value="${expert.expertAccount }" disabled="disabled"/><br />
	专家姓名:<input type="text" name="EXPERT_NAME" value="${expert.expertName }"/><br />
	专家性别:<input type="radio" name="EXPERT_SEX" value="男" ${expert.expertSex == "男" ? "checked":""}/>男
	      <input type="radio" name="EXPERT_SEX" value="女" ${expert.expertSex == "女" ? "checked":""}/>女<br />
	状态:<select name="EXPERT_STATE">
	<option value="在线">在线</option>
	<option value="离开">离开</option>
	<option value="离线">离线</option>
	    </select></br>
	个人简介:<input type="text" name="EXPERT_INTRODUCE" value="${expert.expertIntroduce }"/><br />
	访问量:<input type="text" name="EXPERT_PV" value="${expert.expertPv }"/><br />
	地址:<input type="text" name="EXPERT_ADDRESS" value="${expert.expertAddress }"/><br />
	头像:<input type="file" name="ERTERT_ICON" value="${expert.expertIcon }"/><br />
	邮箱:<input type="text" name="ERTERT_EMAIL" value="${expert.expertEmail }"/><br />
	<input type="hidden" name="hexpertAccount" value="${expert.expertAccount}" />
	<input type="submit" value="修改"/>
	</form>
</body>
</html>