<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>Java 开发数据统计报表</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
    <style type="text/css">
        *{margin:0;padding:0;}
        body{font-family:"微软雅黑";background:url("");background-position:center;}
        .box{width:1000px;height:660px; margin: 50px auto ; color: #fff}
        h1{font-size:34px ; font_family:"方正嘻哈体"; text-align:center; line-height:100px; background: 
           -webkit-linear-gradient(45deg,#ff0000,#ffcc00,#0099cc,#00ff66,#0033ff,#9966ff);color:transparent: -webkit-background-clip:text;}
        .box .search{width: 620px; height: 37px; background:#7e97de;margin: auto; border-radius:4px;overflow:hidden;border:1px solid #ccc;}
        .box .search .txt{outline:none; border: none; height: 37px; font-family: "微软雅黑";}
        .box .search .txt1{width:536px; padding-left: 4px;}
        .box .search .txt2{width:80px; float:right; font-size:14px;}
        .box .select{text-align:center;line-height:60px; font-size: 14px;}
        .box .content{width: 100%; height:360px;}
    </style>
   </head>
  
  <body>
     <!-- box start -->
     <div class="box">
         <h1>Java 开发大数据实时统计分析系统</h1>
         <div class="search">
              <input type="text" class="txt txt1" value="http://cs.ganji.com/zhaopin/s/_java/"/>
              <input type="button" value="开始统计" class="txt txt2" onclick="tz_init_Chart('column3d')"/>
         </div>
         <p class="select"> 查看不同类型的统计报表：
             <select onchange="tz_change(this)">
                  <option value="">--请选择--</option>
                  <option value="column2d">column2d</option>
                  <option value="column3d">column3d</option>
                  <option value="line">line</option>
                  <option value="area2d">area2d</option>
                  <option value="bar2d">bar2d</option>
                  <option value="pie2d">pie2d</option>
                  <option value="pie3d">pie3d</option>
                  <option value="doughnut2d">doughnut2d</option>                  
                  <option value="doughnut3d">doughnut3d</option>
                  <option value="pareto2d">pareto2d</option>
                  <option value="pareto3d">pareto3d</option>                  
             </select>
         </p>
         <div class="content" id="myReport"></div>
     </div>
     <!-- box end -->
  </body>
  <script type="text/javascript" src="js/jquery-3.1.1.min.js"></script>
  <script type="text/javascript" src="js/chart/fusioncharts.js"></script>
  <script type="text/javascript" src="js/chart/fusioncharts.theme.fint.js"></script>
  <script type="text/javascript" src="js/chart/tzchart.js"></script>
  <script>
      $(function(){
    	  tz_init_Chart("doughnut3d");
      });    
      
      function tz_change(obj){
    	  if(obj.value == ""){
    		  return ;
    	  }
    	  tz_init_Chart(obj.value);
      }
  
      //初始化数据
      function tz_init_Chart(_type){
    	  var url=$(".txt1").val();
    	  $.ajax({
    		  type:"post",
    		  url:"controller",
    		  data:{url:url},
    		  success:function(data){
    			  var datas=eval("("+data+")");
    			  var html="";
    			  for(var i=0;i<datas.length;i++){
    				  html+="<set label='"+datas[i].text+"' value='"+datas[i].num+"' />";
    			  }
    			  $.tzChart({
    				 target: "myReport",
    				 type: _type,
    				 height:"360",
    				 width:"100%",
    				 data:"<chart caption='2016年Java行业招聘任职要求实时统计表'  yaxismaxvalue='250' showborder='0' theme='fint'>"+html+"</chart>"
    			  });
    		  }
    	  });
      }
  
  
  </script>
  
  
  
  
  
  
</html>
