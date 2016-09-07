 $(function(){
        	$("#radimg").click(function(){
        		//location.reload();
				console.log("flash");
				$("#radimg").attr("src","RandomCode?id="+new Date()+"");
        	});
        	//验证加载页面时保存密码复选框是否选择
        	function checkSave(){
        		var status=localStorage.getItem("chkstatus");
            	if(status=="true"){        		
            		$("#chksave").prop("checked",true);
            		$("#txtid").val(localStorage.getItem("id"));
            		$("#txtpass").val(localStorage.getItem("pass"));           		
            	}else{
            		$("#chksave").prop("checked",false);
            	}
        	}
        	//调用方法=》》》//验证加载页面时保存密码复选框是否选择
        	checkSave();
        	//调用方法=》》》 // 返回服务器登录结果 
          	$("#btnlogin").click(ajaxFunction);
        });
       // 返回服务器登录结果
        function ajaxFunction(){
            $.post("/eden/ManagerLoginServlet.do",{txtrad:$("#txtrad").val(),txtid:$("#txtid").val(),txtpass:$("#txtpass").val(),acode:$("#acode").val()},function (data,textStatus){
            	$("#diverror").html(data);
				 if(data=="success"){
					 saveIDandPass();
					 window.location.href="/eden/main.jsp";
				 }
				 $("#diverror").css("display","block");
	             $("#diverror").fadeOut(2000);
                 });
        }
        //改变验证码
        function ajaxChangeCode(){
            $.post("/eden/ManagerLoginServlet.do",{txtrad:$("#txtrad").val(),txtid:$("#txtid").val(),txtpass:$("#txtpass").val(),acode:$("#acode").val()},function (data,textStatus){
				// data 可以是 xmlDoc, jsonObj, html, text, 等等.
				//this; // 这个Ajax请求的选项配置信息，请参考jQuery.get()说到的this
					
	  //登录成功才会记录是否保存用户名和密码
				 if(data!=null){
					 saveIDandPass();
				 }
				 $("#diverror").css("display","block");
	             $("#diverror").fadeOut(2000);
                 });
        }
      //判断是否记录是否保存用户名和密码
        function saveIDandPass(){
        	var issave=$("#chksave").prop("checked");
        	if(issave){
        		localStorage.setItem("chkstatus",true);
        		localStorage.setItem("id",$("#txtid").val());
        		localStorage.setItem("pass",$("#txtpass").val());
        	}else{
        		localStorage.setItem("chkstatus",false);
        		localStorage.setItem("id",null);
        		localStorage.setItem("pass",null);
        	}
        }