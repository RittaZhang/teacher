<%@ page language="java" import="java.util.*" contentType="text/html; charset=utf-8" %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<meta http-equiv="content-type" content="text/html;charset=UTF-8">
<link rel="stylesheet" type="text/css" href="../css/default.css" />


<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<body>
<style type="text/css">
* {
    margin: 0;
    padding: 0;
    vertical-align: baseline;
	font-family:微软雅黑;
	overflow:hidden;
}
#navi{
	width:100%;
	position:relative;
	word-wrap:break-word;
	border-bottom:1px solid #065FB9;
	margin:0;
	padding:0;
	height:40px;
	line-height:40px;
	vertical-align:middle;
    background-image: -moz-linear-gradient(top,#EBEBEB, #BFBFBF);
    background-image: -webkit-gradient(linear, left top, left bottom, color-stop(0, #EBEBEB),color-stop(1, 
#BFBFBF));
}
#naviDiv{
	font-size:14px;
	color:#333;
	padding-left:10px;
}
#tips{
	margin-top:10px;
	width:100%;
	height:40px;
}
.button{
	float:left;
	margin-right:10px;
	padding-left:10px;
	padding-right:10px;
	font-size:14px;
	width:70px;
	height:30px;
	line-height:30px;
	vertical-align:middle;
	text-align:center;
	cursor:pointer;
    border-color: #77D1F6;
    border-width: 1px;
    border-style: solid;
    border-radius: 6px 6px;
    -moz-box-shadow: 2px 2px 4px #282828;
    -webkit-box-shadow: 2px 2px 4px #282828;
    background-image: -moz-linear-gradient(top,#EBEBEB, #BFBFBF);
    background-image: -webkit-gradient(linear, left top, left bottom, color-stop(0, #EBEBEB),color-stop(1, #BFBFBF));
}
#mainContainer{
	padding-left:10px;
	padding-right:10px;
	text-align:center;
	width:98%;
	font-size:12px;
}
table{
	text-align:left;
	width:70%;
	margin-left:10%;
}
table th{
	text-align:right;
}

</style>
<script type="text/javascript" src="../js/Calendar3.js"></script>

<div id="navi">
	<div id='naviDiv'>
		<span><img src="../images/arror.gif" width="7" height="11" border="0" alt=""></span>&nbsp;教师管理<span>&nbsp;
		<span><img src="../images/arror.gif" width="7" height="11" border="0" alt=""></span>&nbsp;<a href="<%=path%>/teacher/Teacher_teacherSearch.action">教师列表</a><span>&nbsp;
		<span><img src="../images/arror.gif" width="7" height="11" border="0" alt=""></span>&nbsp;<a href="<%=path%>/teacher/Teacher_toDetail.action?tid=<s:property value="#session.modify_teacher.id"/>">教师详细</a><span>&nbsp;
	</div>
</div>
<div id="mainContainer">
<!-- 从session中获取学生集合 -->
<h2><strong>教师详细资料</strong></h2>
<br>
<br>


<table  >
  <tr>
    <th width="30%">教师编号：</th>
    <td><s:property value="#session.modify_teacher.code"/></td>
    <th width="30%">婚姻状况：</th>
    <td><s:property value="#session.modify_teacher.marriage"/></td>
  </tr>
  <tr>
    <th width="30%">姓名：</th>
    
    <td><s:property value="#session.modify_teacher.name"/></td>
    <th width="30%">联系电话：</th>
    <td><s:property value="#session.modify_teacher.phone"/></td>
  </tr>
  <tr>
    <th>性别：</th>
    <td>
    	<s:property value="#session.modify_teacher.gender"/>
    </td>
    <th width="30%">籍贯：</th>
    <td><s:property value="#session.modify_teacher.nativeplace"/></td>
  </tr>
  <tr>
    <th>出生日期：</th>
    <td>
      <s:date name="#session.modify_teacher.birthday" format="yyyy-MM-dd"/>
    </td>
    <th width="30%">民族：</th>
    <td><s:property value="#session.modify_teacher.nation"/></td>
  </tr>
  <tr>
    <th>地址：</th>
    <td><s:property value="#session.modify_teacher.address"/></td>
    <th>政治面貌：</th>
    <td><s:property value="#session.modify_teacher.politics"/></td>
  </tr>
  <tr>
    <th>部门1：</th>
    <td>
    <s:if test="#session.modify_teacher.department!=null">
    	<s:if test="#session.modify_teacher.department.departmentLevel=='一级'">
    	<s:property value="#session.modify_teacher.department.departmentName"></s:property>
    	</s:if>
    	<s:elseif test="#session.modify_teacher.department.departmentLevel=='二级'">
    	<s:property value="#session.modify_teacher.department.fatherDept.departmentName"></s:property>
    	</s:elseif>
   	</s:if>
    </td>
    <th>状态：</th>
    <td><s:property value="#session.modify_teacher.status"></s:property></td>
  </tr>
  <tr>
    <th>部门2：</th>
    <td>
		<s:if test="#session.modify_teacher.department!=null">
       		<s:if test="#session.modify_teacher.department.departmentLevel=='二级'">
       		<s:property value="#session.modify_teacher.department.departmentName"></s:property>
       		</s:if>
       	</s:if>
	</td>
	<th>身份证号：</th>
    <td><s:property value="#session.modify_teacher.idcard"></s:property></td>
  </tr>
  <tr>
  	
  	<th>就职日期：</th>
  	<td>
  		<s:date name="#session.modify_teacher.workdate" format="yyyy-MM-dd"/>
  	</td>
  	<th>退休日期：</th>
  	<td>
  		<s:date name="#session.modify_teacher.retiredate" format="yyyy-MM-dd"/>
  	</td>
  </tr>
  <tr>
  	<th>离职日期：</th>
  	<td>
  		<s:date name="#session.modify_teacher.quitdate" format="yyyy-MM-dd"/>
  	</td>
  	<th>离职原因：</th>
  	<td><s:property value="#session.modify_teacher.quitreason"></s:property></td>
  </tr>
  <tr>
  	<th>职称：</th>
    <td><s:property value="#session.modify_teacher.title" /></td>
    <th>晋职时间：</th>
    <td>
    	<s:date name="#session.modify_teacher.titleDate" format="yyyy-MM-dd"/>
    </td>
  </tr>
  <tr>
  	<th>级别名称(行政)：</th>
    <td><s:property value="#session.modify_teacher.rank" /></td>
    <th>任职时间(行政)：</th>
    <td>
    	<s:date name="#session.modify_teacher.rankDate" format="yyyy-MM-dd"/>
    </td>
  </tr>
  
</table>
<div id="btn">
<a class="button" href="<%=path%>/teacher/Teacher_toupdate.action?tid=<s:property value="#session.modify_teacher.id"/>"  style="margin: 0 45%; display: block;">编辑</a>
</div>
</div>
</body>
</html>