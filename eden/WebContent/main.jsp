<%@page language="java" pageEncoding="utf-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<<html>
<head>
<meta charset="utf-8">
<meta name="renderer" content="webkit|ie-comp|ie-stand">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no" />
<meta http-equiv="Cache-Control" content="no-siteapp" />
<LINK rel="Bookmark" href="/favicon.ico" >
<LINK rel="Shortcut Icon" href="/favicon.ico" />
<link href="css/H-ui.min.css" rel="stylesheet" type="text/css" />
<link href="css/H-ui.admin.css" rel="stylesheet" type="text/css" />
<link href="skin/default/skin.css" rel="stylesheet" type="text/css" id="skin" />
<link href="lib/Hui-iconfont/1.0.1/iconfont.css" rel="stylesheet" type="text/css" />
<link href="css/style.css" rel="stylesheet" type="text/css" />
<title>伊甸园</title>
<meta name="keywords" content="H-ui.admin v2.3,H-ui网站后台模版,后台模版下载,后台管理系统模版,HTML后台模版下载">
<meta name="description" content="H-ui.admin v2.3，是一款由国人开发的轻量级扁平化网站后台模板，完全免费开源的网站后台管理系统模版，适合中小型CMS后台系统。">
</head>
<body>
<header class="Hui-header cl"> <a class="Hui-logo l" title="伊甸园" href="/">伊甸园</a> <a class="Hui-logo-m l" href="/" title="伊甸园">伊甸园</a> <span class="Hui-subtitle l"></span>
	<nav class="mainnav cl" id="Hui-nav">
		<ul>
			<li class="dropDown dropDown_click"><a href="javascript:;" class="dropDown_A"><i class="Hui-iconfont">&#xe600;</i> 新增 <i class="Hui-iconfont">&#xe6d5;</i></a>
				<ul class="dropDown-menu radius box-shadow">
					<li><a href="javascript:;" onclick="article_add('添加资讯','article-add.html')"><i class="Hui-iconfont">&#xe616;</i> 资讯</a></li>
					<li><a href="javascript:;" onclick="picture_add('添加资讯','picture-add.html')"><i class="Hui-iconfont">&#xe613;</i> 图片</a></li>
					<li><a href="javascript:;" onclick="product_add('添加资讯','product-add.html')"><i class="Hui-iconfont">&#xe620;</i> 产品</a></li>
					<li><a href="javascript:;" onclick="member_add('添加用户','member-add.html','','510')"><i class="Hui-iconfont">&#xe60d;</i> 用户</a></li>
				</ul>
			</li>
		</ul>
	</nav>
	<ul class="Hui-userbar">
		<li>超级管理员</li>
		<li class="dropDown dropDown_hover"><a href="#" class="dropDown_A">${manager.name}<i class="Hui-iconfont">&#xe6d5;</i></a>
			<ul class="dropDown-menu radius box-shadow">
				<li><a href="#">个人信息</a></li>
				<li><a href="#">切换账户</a></li>
				<li><a href="#">退出</a></li>
			</ul>
		</li>
		<li id="Hui-msg"> <a href="#" title="消息"><span class="badge badge-danger">1</span><i class="Hui-iconfont" style="font-size:18px">&#xe68a;</i></a> </li>
		<li id="Hui-skin" class="dropDown right dropDown_hover"><a href="javascript:;" title="换肤"><i class="Hui-iconfont" style="font-size:18px">&#xe62a;</i></a>
			<ul class="dropDown-menu radius box-shadow">
				<li><a href="javascript:;" data-val="default" title="默认（黑色）">默认（黑色）</a></li>
				<li><a href="javascript:;" data-val="blue" title="蓝色">蓝色</a></li>
				<li><a href="javascript:;" data-val="green" title="绿色">绿色</a></li>
				<li><a href="javascript:;" data-val="red" title="红色">红色</a></li>
				<li><a href="javascript:;" data-val="yellow" title="黄色">黄色</a></li>
				<li><a href="javascript:;" data-val="orange" title="绿色">橙色</a></li>
			</ul>
		</li>
	</ul>
	<a aria-hidden="false" class="Hui-nav-toggle" href="#"></a> </header>
<aside class="Hui-aside">
	<input runat="server" id="divScrollValue" type="hidden" value="" />
	<div class="menu_dropdown bk_2">
		<dl id="menu-article">
			<dt><i class="Hui-iconfont">&#xe616;</i> 用户管理<i class="Hui-iconfont menu_dropdown-arrow">&#xe6d5;</i></dt>
			<dd>
				<ul>
					<li><a _href="${pageContext.request.contextPath}/ManagerQueryAllServlet.do?page=1" href="javascript:void(0)">管理管理员</a></li>
                    <li><a _href="${pageContext.request.contextPath}/ShowUserServlet.do?page=1" href="javascript:void(0)">管理用户</a></li>
                    <li><a _href="${pageContext.request.contextPath}/ShowExpertServlet.do?page=1" href="javascript:void(0)">管理专家</a></li>
                   
				</ul>
			</dd>
		</dl>
		
		<dl id="menu-product">
			<dt><i class="Hui-iconfont">&#xe620;</i> 商城管理<i class="Hui-iconfont menu_dropdown-arrow">&#xe6d5;</i></dt>
			<dd>
				<ul>
					<li><a _href="product-brand.html" href="javascript:void(0)">商品展示</a></li>
					<li><a _href="product-category.html" href="javascript:void(0)">分类管理</a></li>
					<li><a _href="product-list.html" href="javascript:void(0)">产品管理</a></li>
				</ul>
			</dd>
		</dl>
		<dl id="menu-page">
			<dt><i class="Hui-iconfont">&#xe626;</i> 收藏管理<i class="Hui-iconfont menu_dropdown-arrow">&#xe6d5;</i></dt>
			<dd>
				<ul>
					<li><a _href="page-home.html" href="javascript:void(0)">收藏的文字</a></li>
					<li><a _href="page-flinks.html" href="javascript:void(0)">收藏的视屏</a></li>
                    <li><a _href="page-home.html" href="javascript:void(0)">收藏的商品</a></li>
                    <li><a _href="page-home.html" href="javascript:void(0)">收藏的问题</a></li>
				</ul>
			</dd>
		</dl>
		<dl id="menu-comments">
			<dt><i class="Hui-iconfont">&#xe622;</i> 评论管理<i class="Hui-iconfont menu_dropdown-arrow">&#xe6d5;</i></dt>
			<dd>
				<ul>
					<li><a _href="${pageContext.request.contextPath}/ShowCommentServlet.do?page=1" href="javascript:;">评论表</a></li>
					<li><a _href="feedback-list.html" href="javascript:void(0)">教学评论</a></li>
                    <li><a _href="feedback-list.html" href="javascript:void(0)">意见反馈</a></li>
                    <li><a _href="feedback-list.html" href="javascript:void(0)">意见反馈</a></li>
				</ul>
			</dd>
		</dl>
		<dl id="menu-order">
			<dt><i class="Hui-iconfont">&#xe63a;</i> 资源管理<i class="Hui-iconfont menu_dropdown-arrow">&#xe6d5;</i></dt>
			<dd>
				<ul>
					<li><a _href="${pageContext.request.contextPath}/ShowContentServlet.do?page=1" href="javascript:void(0)">资源列表</a></li>
				</ul>
			</dd>
		</dl>
		<dl id="menu-admin">
			<dt><i class="Hui-iconfont">&#xe62d;</i> 表白管理<i class="Hui-iconfont menu_dropdown-arrow">&#xe6d5;</i></dt>
			<dd>
				<ul>
					<li><a _href="${pageContext.request.contextPath}/ShowConfessionServlet.do?page=1" href="javascript:void(0)">表白管理</a></li>
					<li><a _href="admin-permission.html" href="javascript:void(0)">权限管理</a></li>
					<li><a _href="admin-list.html" href="javascript:void(0)">管理员列表</a></li>
				</ul>
			</dd>
		</dl>
		
		<dl id="menu-system">
			<dt><i class="Hui-iconfont">&#xe62e;</i> 咨询管理<i class="Hui-iconfont menu_dropdown-arrow">&#xe6d5;</i></dt>
			<dd>
				<ul>
					<li><a _href="${pageContext.request.contextPath}/ShowProblemServlet.do?page=1" href="javascript:void(0)">问题管理</a></li>
					<li><a _href="" href="javascript:void(0)">栏目管理</a></li>
					<li><a _href="system-data.html" href="javascript:void(0)">数据字典</a></li>
					<li><a _href="system-shielding.html" href="javascript:void(0)">屏蔽词</a></li>
					<li><a _href="system-log.html" href="javascript:void(0)">系统日志</a></li>
				</ul>
			</dd>
		</dl>
	</div>
</aside>
<div class="dislpayArrow"><a class="pngfix" href="javascript:void(0);" onClick="displaynavbar(this)"></a></div>
<section class="Hui-article-box">
	<div id="Hui-tabNav" class="Hui-tabNav">
		<div class="Hui-tabNav-wp">
			<ul id="min_title_list" class="acrossTab cl">
				<li class="active"><span title="我的桌面" data-href="">我的桌面</span><em></em></li>
			</ul>
		</div>
		<div class="Hui-tabNav-more btn-group"><a id="js-tabNav-prev" class="btn radius btn-default size-S" href="javascript:;"><i class="Hui-iconfont">&#xe6d4;</i></a><a id="js-tabNav-next" class="btn radius btn-default size-S" href="javascript:;"><i class="Hui-iconfont">&#xe6d7;</i></a></div>
	</div>
	<div id="iframe_box" class="Hui-article">
		<div class="show_iframe">
			<div style="display:none" class="loading"></div>
			<iframe scrolling="yes" frameborder="0" src="welcome.html"></iframe>
		</div>
	</div>
</section>
<script type="text/javascript" src="lib/jquery/1.9.1/jquery.min.js"></script> 
<script type="text/javascript" src="lib/layer/1.9.3/layer.js"></script> 
<script type="text/javascript" src="js/H-ui.js"></script> 
<script type="text/javascript" src="js/H-ui.admin.js"></script> 
<script type="text/javascript">
/*资讯-添加*/
function article_add(title,url){
	var index = layer.open({
		type: 2,
		title: title,
		content: url
	});
	layer.full(index);
}
/*图片-添加*/
function picture_add(title,url){
	var index = layer.open({
		type: 2,
		title: title,
		content: url
	});
	layer.full(index);
}
/*产品-添加*/
function product_add(title,url){
	var index = layer.open({
		type: 2,
		title: title,
		content: url
	});
	layer.full(index);
}
/*用户-添加*/
function member_add(title,url,w,h){
	layer_show(title,url,w,h);
}
</script> 
<script type="text/javascript">
var _hmt = _hmt || [];
(function() {
  var hm = document.createElement("script");
  hm.src = "//hm.baidu.com/hm.js?080836300300be57b7f34f4b3e97d911";
  var s = document.getElementsByTagName("script")[0]; 
  s.parentNode.insertBefore(hm, s)})();
var _bdhmProtocol = (("https:" == document.location.protocol) ? " https://" : " http://");
document.write(unescape("%3Cscript src='" + _bdhmProtocol + "hm.baidu.com/h.js%3F080836300300be57b7f34f4b3e97d911' type='text/javascript'%3E%3C/script%3E"));
</script>
</body>
</html>