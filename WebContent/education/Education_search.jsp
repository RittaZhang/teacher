<%@ page language="java" import="java.util.*" contentType="text/html; charset=utf-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page import="com.vp.model.Department" %>
<%@page import="javax.servlet.http.*" %>
<%@page import="java.util.List" %>

<%@ taglib uri="/struts-tags" prefix="s" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html >
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
	var formElement = document.getElementById("educationForm");
	formElement.action = actionUrl;
	formElement.submit();
}

// 第一页
function firstPage(){
	if(currentPage == 1){
		alert("已经是第一页数据");
		return false;
	}else{
		submitForm("<%=path%>/education/Education_educationSearch.action");
		return true;
	}
}

// 下一页
function nextPage(){
	if(currentPage == totalPage){
		alert("已经是最后一页数据");
		return false;
	}else{
		submitForm("<%=path%>/education/Education_educationSearch.action?page=" + (currentPage+1));
		return true;
	}
}

// 上一页
function previousPage(){
	if(currentPage == 1){
		alert("已经是第一页数据");
		return false;
	}else{
		submitForm("<%=path%>/education/Education_educationSearch.action?page="+(currentPage - 1)) ;
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
		submitForm("<%=path%>/education/Education_educationSearch.action?page="+totalPage);
		return true;
	}
}
</script>
<body>
	<div id="navi">
		<div id='naviDiv'>
			<span><img src="../images/arror.gif" width="7" height="11" border="0" alt=""></span>&nbsp;学历管理<span>&nbsp;
			<span><img src="../images/arror.gif" width="7" height="11" border="0" alt=""></span>&nbsp;<a href="<%=path%>/education/Education_educationSearch.action">学历列表</a><span>&nbsp;
		</div>
	</div>
	<div id="tips">
		<form  action="<%=path%>/education/Education_educationSearch.action" id="educationForm" method="post">
			<table>
				<tr>
					<td>姓名：<input type="text" id="tname" name="tname" value="${tname}"></td>
					<td>职工编号：<input type="text" id="tcode" name="tcode"  value="${tcode}"></td>
					<td><input class="button" type="submit" value="检索" /></td>
				</tr>
			</table>
		</form>
	</div>
	<div id="mainContainer">
		<table class="default" width="100%" >
			<tr class="title">
				<td width="10%"><a onclick="document.getElementById('educationForm').action='<%=path%>/education/Education_educationSearch.action?order=name';document.getElementById('educationForm').submit()" >姓名</a></td>
				<td width="20%"><a onclick="document.getElementById('educationForm').action='<%=path%>/education/Education_educationSearch.action?order=code';document.getElementById('educationForm').submit()" >教职工编号</a></td>
				<td width="20%">毕业院校</td>
				<td width="20%">专业</td>
				<td width="20%">学历层次</td>
				<td width="10%">操作</td>
			</tr>
		<s:iterator value="#pageBean.list" status="bcs" var="t">
	        <tr class="list"> 
	            <td><s:property value="#t.name"></s:property></td>
	            <td><s:property value="#t.code"></s:property></td>
	            <td colspan="3">
	            	<table width="100%" >
	            		<s:iterator value="#t.educations" status="bcs" var="e">
	            		<tr>
	            			<td width="33.3%"><s:property value="#e.graduateInstitutions"></s:property></td>
	            			<td width="33.3%"><s:property value="#e.major"></s:property></td>
	            			<td><s:property value="#e.degree"></s:property></td>
	            		</tr>
	            		</s:iterator>
	            	</table>
	            </td>
	            <td><a href="<%=path%>/education/Education_educationEdit.action?tid=<s:property value="#t.id"/>">编辑</a></td>
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
	    <a href="<%=path%>/education/Education_exportExcel.action">下载</a>
	</div>
</body>
</html>