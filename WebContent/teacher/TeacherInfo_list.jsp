<%@ page language="java" import="java.util.*" contentType="text/html; charset=utf-8" %>
<%@page import="com.vp.model.Department" %>
<%@page import="javax.servlet.http.*" %>
<%@page import="java.util.List" %>

<%@ taglib uri="/struts-tags" prefix="s" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html>
<html>
<meta http-equiv="content-type" content="text/html;charset=UTF-8">
<link rel="stylesheet" type="text/css" href="../css/default.css" />
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
html{
    overflow-x:hidden;
    overflow-y:auto;
}
</style>
<script type="text/javascript">
//当前第几页数据
var currentPage = ${pageBean.currentPage};

// 总页数
var totalPage = ${pageBean.totalPage};

function submitForm(actionUrl){
	var formElement = document.getElementById("teacherForm");
	formElement.action = actionUrl;
	formElement.submit();
}

// 第一页
function firstPage(){
	if(currentPage == 1){
		alert("已经是第一页数据");
		return false;
	}else{
		submitForm("<%=path%>/teacher/Teacher_getTeacherInfo.action");
		return true;
	}
}

// 下一页
function nextPage(){
	if(currentPage == totalPage){
		alert("已经是最后一页数据");
		return false;
	}else{
		submitForm("<%=path%>/teacher/Teacher_getTeacherInfo.action?page=" + (currentPage+1));
		return true;
	}
}

// 上一页
function previousPage(){
	if(currentPage == 1){
		alert("已经是第一页数据");
		return false;
	}else{
		submitForm("<%=path%>/teacher/Teacher_getTeacherInfo.action?page="+(currentPage - 1)) ;
		return true;
	}
}

// 尾页
function lastPage(){
	if(currentPage == totalPage){
		alert("已经是最后一页数据");
		return false;
	}else{
		//alert("weiye");
		submitForm("<%=path%>/teacher/Teacher_getTeacherInfo.action?page="+totalPage);
		return true;
	}
}
</script>
<body>
	<div id="navi">
		<div id='naviDiv'>
			<span><img src="../images/arror.gif" width="7" height="11" border="0" alt=""></span>&nbsp;花名册管理<span>&nbsp;
			<span><img src="../images/arror.gif" width="7" height="11" border="0" alt=""></span>&nbsp;<a href="<%=path%>/teacher/Teacher_getTeacherInfo.action">花名册列表</a><span>&nbsp;
		</div>
	</div>
	<form  action="<%=path%>/teacher/Teacher_exportExcel.action" id="teacherForm" method="post">
		<table style="width:80%; align:center">
			<tr>
				<td><input name="checkbox1" type="checkbox" checked="checked" value="部门" />部门</td>
				<td><input name="checkbox1" type="checkbox" checked="checked" value="姓名" />姓名</td>
				<td><input name="checkbox1" type="checkbox" checked="checked" value="性别" />性别</td>
				<td><input name="checkbox1" type="checkbox" checked="checked" value="出生日期" />出生日期</td>
				<td><input name="checkbox1" type="checkbox" checked="checked" value="学历" />学历</td>
				<td><input name="checkbox1" type="checkbox" checked="checked" value="职称" />职称</td>
				<td><input name="checkbox1" type="checkbox" checked="checked" value="晋职时间" />晋职时间</td>
				<td><input name="checkbox1" type="checkbox" checked="checked" value="行政职务" />行政职务</td>
			</tr>
			<tr>
				<td colspan="9"><input type="submit" class="button"  value="下载" /></td>
			</tr>
		</table>
	</form>
	<form  action="<%=path%>/teacher/Teacher_getTeacherInfo.action" id="teacherForm" method="post">
	</form>
	<div id="mainContainer">
		<table class="default" width="100%">
			<tr class="title">
				<td rowspan="2">序号</td>
				<td colspan="2">部门</td>
				<td rowspan="2">姓名</td>
				<td rowspan="2">性别</td>
				<td rowspan="2">出生日期</td>
				<td colspan="3">学历</td>
				<td rowspan="2">职称</td>
				<td rowspan="2">晋职时间</td>
				<td colspan="2">行政职务</td>
			</tr>
			<tr class="title">
				<td>一级部门</td>
				<td>二级部门</td>
				<td>毕业院校</td>
				<td>所学专业</td>
				<td>学历层次</td>
				<td>级别名称</td>
				<td>任职时间</td>
			</tr>
		<s:iterator value="#pageBean.list" status="bcs" var="t">
        	<tr class="list"> 
	            <td><s:property value="#t.id"></s:property></td>
	            <td>
		            <s:if test="#t.department!=null">
		            	<s:if test="#t.department.departmentLevel=='一级'">
		            	<s:property value="#t.department.departmentName"></s:property>
		            	</s:if>
		            	<s:elseif test="#t.department.departmentLevel=='二级'">
		            	<s:property value="#t.department.fatherDept.departmentName"></s:property>
		            	</s:elseif>
	            	</s:if>
	            </td>
	            <td>
	            	<s:if test="#t.department!=null">
	            		<s:if test="#t.department.departmentLevel=='二级'">
	            		<s:property value="#t.department.departmentName"></s:property>
	            		</s:if>
	            	</s:if>
	            </td>
	            <td><s:property value="#t.name"></s:property></td>
	            <td><s:property value="#t.gender"></s:property></td>
	            <td><s:date name="#t.birthday" format="yyyy-MM-dd"></s:date></td>
	             <td colspan="3">
	            	<table width="100%" >
	            		<s:iterator value="#t.educations" status="bcs" var="e">
	            		<tr>
	            			<td width="33.3%"><s:property value="#e.graduateInstitutions"></s:property></td>
	            			<td width="33.3%"><s:property value="#e.major"></s:property></td>
	            			<td ><s:property value="#e.degree"></s:property></td>
	            		</tr>
	            		</s:iterator>
	            	</table>
	            </td>
	            <td><s:property value="#t.title"></s:property></td>
	            <td><s:date name="#t.titleDate" format="yyyy-MM-dd"></s:date></td>
	            <td><s:property value="#t.rank"></s:property></td>
	            <td><s:date name="#t.rankDate" format="yyyy-MM-dd"></s:date></td>
	        </tr>
	    </s:iterator>
	    </table>
	    <s:if test="#pageBean.list.size()==0">
	        1/1页  首页&nbsp;&nbsp;&nbsp;上一页   下一页&nbsp;&nbsp;&nbsp;尾页
	    </s:if>
	    <s:else>
	    <div>  
	     	<s:property value="#request.pageBean.currentPage"/>/<s:property value="#request.pageBean.totalPage"/>页
	        <s:if test="#request.pageBean.currentPage == 1">
	                         首页&nbsp;&nbsp;&nbsp;上一页
	        </s:if>
	        
	        <s:else>
	            <a href="#" onclick="firstPage();">首页</a>&nbsp;&nbsp;&nbsp;
	            <a href="#" onclick="previousPage();">上一页</a>
	        </s:else>
	        
	        <s:if test="#request.pageBean.currentPage != #request.pageBean.totalPage">
	            <a href="#" onclick="nextPage();">下一页</a>&nbsp;&nbsp;&nbsp;
	            <a href="#" onclick="lastPage();">尾页</a>	
	        </s:if>
	        
	        <s:else>
	                        下一页&nbsp;&nbsp;&nbsp;尾页
	        </s:else>
	    
	    </div><br>
	    </s:else>
	</div>
</body>	
</html>