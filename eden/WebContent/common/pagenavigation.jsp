<%@page language="java" pageEncoding="utf-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<a href="javascript:gotoPage('${param.url}','1','${pb.pageSize}','${s}')" >首页</a> &nbsp;&nbsp;
<a href="javascript:gotoPage('${param.url}','${pb.currentPage-1==0 ? 1 : pb.currentPage-1}','${pb.pageSize}','${s}')">上一页</a> &nbsp;&nbsp;
<a href="javascript:gotoPage('${param.url}','${pb.currentPage+1> pb.totalPages? pb.totalPages:pb.currentPage+1}','${pb.pageSize}','${s}')">下一页</a> &nbsp;&nbsp;
<a href="javascript:gotoPage('${param.url}','${pb.totalPages}','${pb.pageSize}','${s}')">尾页</a> &nbsp;&nbsp;
每页显示的记录数:
<select onchange="gotoPage('${param.url}','1',this.value,'${s}')" id="ps" class="ui_select01">
	<option value="1" ${pb.pageSize==1?"selected='selected'":""}>1</option>
	<option value="2" ${pb.pageSize==2?"selected='selected'":""}>2</option>
	<option value="3" ${pb.pageSize==3?"selected='selected'":""}>3</option>
	<option value="8" ${pb.pageSize==8?"selected='selected'":""}>8</option>
	<option value="9" ${pb.pageSize==9?"selected='selected'":""}>9</option>
	<option value="10" ${pb.pageSize==10?"selected='selected'":""}>10</option>
	</select> 
	当前<select onchange="gotoPage('${param.url}',this.value,'${pb.pageSize}','${s}')" class="ui_select01"> 
		<c:forEach var="i" begin="1" end="${pb.totalPages}" step="1">
		<option value="${i}" ${pb.currentPage==i?"selected='selected'":""}>第${i}页</option>
		</c:forEach> 
	</select> 
	/共有${pb.totalPages}页/共有${pb.totalRows}条记录