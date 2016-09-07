<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>内容修改</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<script type="text/javascript" src="scripts/jquery/jquery-1.7.1.js"></script>
<link href="style/authority/basic_layout.css" rel="stylesheet"
	type="text/css">
<link href="style/authority/common_style.css" rel="stylesheet"
	type="text/css">
<script type="text/javascript" src="scripts/authority/commonAll.js"></script>
<script type="text/javascript" src="scripts/jquery/jquery-1.4.4.min.js"></script>
<script src="scripts/My97DatePicker/WdatePicker.js"
	type="text/javascript" defer="defer"></script>
<script type="text/javascript"
	src="scripts/artDialog/artDialog.js?skin=default"></script>
<script type="text/javascript">
	$(document).ready(function() {
		$("#contentText").html("${updateContent.contentText}");
		$("#submitbutton").click(function() {
			if (validateForm()) {
				$("form").submit();
			}

		});
	});
	/** 表单验证  **/
	function validateForm() {
		if ($("#contentTitle").val() == "") {
			art.dialog({
				icon : 'error',
				title : '友情提示',
				drag : false,
				resize : false,
				content : '填写内容标题',
				ok : true,
			});
			return false;
		} else if ($("#contentText").val() == "") {
			art.dialog({
				icon : 'error',
				title : '友情提示',
				drag : false,
				resize : false,
				content : '填写文字内容',
				ok : true,
			});
			return false;
		} else {
			return true;
		}
	}
</script>
</head>
<body>
	<form id="submitForm" name="submitForm"
		action="${pageContext.request.contextPath}/UpdateContentServlet.do"
		method="post" enctype="multipart/form-data">
		<div id="container">
			<div id="nav_links">
				当前位置：基础数据&nbsp;>&nbsp;<span style="color: #1A5CC6;">修改内容</span>
				<div id="page_close">
					<a href="javascript:parent.$.fancybox.close();"> <img
						src="images/common/page_close.png" width="20" height="20"
						style="vertical-align: text-top;" />
					</a>
				</div>
			</div>
			<input type="hidden" name="contentId"
				value="${updateContent.contentId}" /> <input type="hidden"
				name="Video" value="${updateContent.contentVideo}" /> <input
				type="hidden" name="Picture" value="${updateContent.contentPicture}" />
			<div class="ui_content">
				<table cellspacing="0" cellpadding="0" width="100%" align="left"
					border="0">
					<tr>
						<td class="ui_text_rt">标题</td>
						<td class="ui_text_lt"><input type="text" id="contentTitle"
							name="contentTitle" value="${updateContent.contentTitle}" /></td>
					</tr>
					<tr>
						<td class="ui_text_rt">视频</td>
						<td><img src="upload/${updateContent.contentVideo}"
							width="120" height="90" border="1" /></td>
						<td class="ui_text_lt"><input type="file" id="contentVideo"
							name="contentVideo" /></td>
					</tr>
					<tr>
						<td class="ui_text_rt">图片</td>
						<td><img src="upload/${updateContent.contentPicture}"
							width="120" height="90" border="1" /></td>
						<td class="ui_text_lt"><input type="file" id="contentPicture"
							name="contentPicture" /></td>
						<td>
					</tr>
					<tr>
						<td class="ui_text_rt">文字</td>
						<td class="ui_text_lt"><textarea rows="5" cols="30"
								id="contentText" name="contentText"></textarea></td>
					</tr>
					<tr>
						<td>&nbsp;</td>
						<td class="ui_text_lt">&nbsp; <input id="submitbutton"
							type="button" value="提交" class="ui_input_btn01" /> &nbsp; <input
							id="cancelbutton" type="button" value="取消" class="ui_input_btn01" />
						</td>
					</tr>
				</table>
			</div>
		</div>
	</form>
</body>
</html>