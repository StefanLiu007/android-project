<%@ page import="java.util.*" contentType="text/html;charset=utf-8"%>
<%@ page import="java.awt.*" %> 
<%@page import="java.text.SimpleDateFormat,java.util.Date"%>
<%
  
  String path = request.getContextPath();
  String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<html>
<!DOCTYPE html>
<head lang="en">
<base href="<%=basePath%>">
<title>My JSP 'managerLogin.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
    <meta charset="UTF-8">
    <style type="text/css">
        body{
			background-image:url(images/login.jpg);
            width: 100%;
            margin: 0 auto;
        }
        #txtid,#txtpass,#txtrad{
            width: 70%;
            height: 40px;
            border: solid 1px gray;
            font-size: 1.5em;
            float:left;
        }
        #txtrad{
            width: 60%;
            height: 40px;
            border: solid 1px gray;
            font-size: 1.5em;
            float:left;
        }
        #imgrd{
            width: 80px;
            height: 40px;
            margin-left:10px;
            float:left;
        }
         #txtid:hover,#txtpass:hover{
             border: solid 1px darkslategrey;
             box-shadow: 1px 1px 1px darkslategrey;
         }
        table{
            border: solid 1px;
            margin: auto;
            width: 36%;
            height: 100px;
			margin-right:20px ;
            margin-top:180px ;
        }
        #btnlogin,#btncancel{
            width: 30%;
            height: 40px;
            background-color: #555555;
            color: white;
        }
        caption{
            font-size: 3em;
            color: darkslategrey;
        }
        #diverror{
            position: absolute;
            width: 60%;
            height: 80px;
            border: solid 1px gainsboro;
            top: 100px;
            left: 300px;
            font-size: 2em;
            text-align: center;
            line-height: 80px;
            margin:auto;
            display: none;

        }
		#btnlogin:hover,#btncancel:hover{
			background-color:#999;
			}
    </style>
    <script src="js/jquery-2.1.1.js"></script>
    <script src="js/login.js">
    </script>
</head>
<body>
    <div id="diverror">
    <%=new SimpleDateFormat("yyyy-MM-dd").format(new Date())
    %>
    </div>
<form name="myform" method="post" action="LoginServlet">
  <input type="hidden" name="acode" value="1" id="acode"/>
   <table>
    
        <caption>伊甸园</caption>
        <tr>
            <td width="30%" align="right">姓名</td>
            <td><input type="text"  name="txtid" id="txtid" autocomplete="true"  required/></td>
        </tr>
        <tr>
            <td align="right">密码</td>
                <td valign="middle"><input type="password" name="txtpass" maxlength="6" id="txtpass" required/>
        </td>
        </tr>
         <tr>
            <td align="right"></td>
                <td valign="middle"><input type="text" name="txtrad" maxlength="4" id="txtrad" placeholder="请输入验证码" required/>
            <span id="imgrd">
                <img id="radimg"  alt="random" src="RandomCode">
            </span>
        </td>
        </tr>
        <tr>
            <td> </td>
            <td>
                <input type="checkbox" id="chksave" style="width: 20px;height: 20px"/><label style="font-size: 1.5em">记住密码</label>
            </td>
        </tr>
        <tr>
        
            <td colspan="2" align="center">
                <input type="button" value="LGOIN" id="btnlogin"/>
                <input type="button" value="CANCEL" id="btncancel"/>
            </td>
        </tr>
    </table>
</form>
</body>
</html>