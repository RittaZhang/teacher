<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%@page import="java.text.SimpleDateFormat" %>
<%@page import="java.util.Date" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<link rel="stylesheet" type="text/css" href="../css/default.css" />
<link rel="stylesheet" href="../css/jquery-ui.min.css">
<script type="text/javascript" src="../js/jquery-1.11.3.min.js"></script>
<script type="text/javascript" src="../js/jquery-ui.min.js"></script>
<script type="text/javascript" src="../js/Calendar3.js"></script>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
String[] attendanceDate = (String[])request.getParameterValues("attendanceDate");
String[] attendanceStatus = (String[])request.getParameterValues("attendanceStatus");
String[] remarks = (String[])request.getParameterValues("remarks");
SimpleDateFormat formatter = new SimpleDateFormat ("yyyy-MM-dd");
%>
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
	/*float:left;
	margin-right:10px;
	padding-left:10px;
	padding-right:10px;*/
	font-size:14px;
	width:80px;
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
	var formElement = document.getElementById("selform");
	formElement.action = actionUrl;
	formElement.submit();
}

// 第一页
function firstPage(){
	if(currentPage == 1){
		alert("已经是第一页数据");
		return false;
	}else{
		submitForm("<%=path%>/attendance/Attendance_selectAtt.action");
		return true;
	}
}

// 下一页
function nextPage(){
	if(currentPage == totalPage){
		alert("已经是最后一页数据");
		return false;
	}else{
		submitForm("<%=path%>/attendance/Attendance_selectAtt.action?page1=" + (currentPage+1));
		return true;
	}
}

// 上一页
function previousPage(){
	if(currentPage == 1){
		alert("已经是第一页数据");
		return false;
	}else{
		submitForm("<%=path%>/attendance/Attendance_selectAtt.action?page1="+(currentPage - 1)) ;
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
		submitForm("<%=path%>/attendance/Attendance_selectAtt.action?page1="+totalPage);
		return true;
	}
}
</script>
<body>
	<div id="navi">
		<div id='naviDiv'>
			<span><img src="../images/arror.gif" width="7" height="11" border="0" alt=""></span>&nbsp;考勤管理<span>&nbsp;
			<span><img src="../images/arror.gif" width="7" height="11" border="0" alt=""></span>&nbsp;<a href="<%=path%>/attendance/Attendance_attendanceSearch.action">考勤列表</a><span>&nbsp;
		</div>
	</div>
	<div id="mainContainer">
		
			<div>
				<s:fielderror/> <!-- 显示表单验证的出错信息 -->
			</div>
			
			<table style="width:300px;margin: 8px 0 0 1%;">
				<tr>
					<th>教师姓名：</th>
					<td><s:property value="#att_teacher.name" /></td>
					<th>教师工号：</th>
					<td><s:property value="#att_teacher.code" /></td>
				</tr>
			</table>
		<form id="selform" action="<%=path%>/attendance/Attendance_selectAtt.action" method="post">
		<input type="hidden" name="tid" value="<s:property value="#att_teacher.id" />"/>
			<table>
				<tr>
					<td>起始时间<input value="<s:property value="stratDate" />" name="stratDate" type="text" id="control_date" size="20" maxlength="10" onclick="new Calendar().show(this);" readonly="readonly" /></td>
					<td>结束时间<input value="<s:property value="endDate" />" name="endDate" type="text" id="control_date" size="20" maxlength="10" onclick="new Calendar().show(this);" readonly="readonly" /></td>
					<td><input class="button" type="submit" value="检索"></td>
				</tr>
			</table>
		</form>
		<form action="<%=path%>/attendance/Attendance_attendanceUpdate.action" method="post">
			<input type="hidden" name="tid" value="<s:property value="#att_teacher.id" />"/>
			<table class="default" width="100%" id="tbl">
				<tr class="title">
					<td>签到时间</td>
					<td>签到状态</td>
					<td>备注</td>
					<td>操作</td>
				</tr>
				<s:iterator value="#pageBean.list" status="bcs" var="a">
				<tr class="list">
					<td><s:date name="#a.attendanceDate" format="yyyy-MM-dd"/></td>
					<td><s:property value="#a.attendanceStatus" /></td>
					<td><s:property value="#a.remarks" /></td>
					<td>
						<a onclick="if(confirm('确定删除?')==false)return false;" href="<%=path%>/attendance/Attendance_attendanceDelete.action?aid=<s:property value="#a.id"/>&tid=<s:property value="#att_teacher.id"/>">删除</a>
					</td>
				</tr>
				</s:iterator>
				<%if(attendanceDate!=null){ %>
				<%for(Integer i=0;i<attendanceDate.length;i++){ %>
					<tr>
					<td><input value="<%=attendanceDate[i]%>" name="attendanceDate" type="text" id="control_date" size="20" maxlength="10" onclick="new Calendar().show(this);" readonly="readonly" /></td>
					<td><input class="status" name="attendanceStatus" type="text"  value="<%=attendanceStatus[i]%>"/></td>
					<td><input class="remarks" name="remarks" type="text" value="<%=remarks[i]%>"/></td>
					<td>
						<a onclick="deleteRow(this)">删除行</a>
					</td>
				</tr>
				<%} }%>
			</table>
			<s:if test="#pageBean.list.size()==0">
	        1/1页  首页&nbsp;&nbsp;&nbsp;上一页   下一页&nbsp;&nbsp;&nbsp;尾页
		    </s:if>
		    <s:else>
		    <div>  
		     	<s:property value="#pageBean.currentPage"/>/<s:property value="#pageBean.totalPage"/>页
		        <s:if test="#pageBean.currentPage == 1">
		                         首页&nbsp;&nbsp;&nbsp;上一页
		        </s:if>
		        
		        <s:else>
		            <a href="#" onclick="firstPage();">首页</a>&nbsp;&nbsp;&nbsp;
		            <a href="#" onclick="previousPage();">上一页</a>
		        </s:else>
		        
		        <s:if test="#pageBean.currentPage != #pageBean.totalPage">
		            <a href="#" onclick="nextPage();">下一页</a>&nbsp;&nbsp;&nbsp;
		            <a href="#" onclick="lastPage();">尾页</a>	
		        </s:if>
		        
		        <s:else>
		                        下一页&nbsp;&nbsp;&nbsp;尾页
		        </s:else>
		    
		    </div><br>
		    </s:else>
		<input type="button" class="button"  value="添加一行" onclick="addRow()" />&nbsp;&nbsp;
		<input type="submit" class="button" />
		</form>
	</div>
</body>
<script type="text/javascript">
     function addRow(){
    	 var table  =  document.getElementById("tbl");
    	 var trlist = table.getElementsByTagName('tr');
    	 
    	 var newRow = table.insertRow();
    	 var newCell0 = newRow.insertCell();
         var newCell1 = newRow.insertCell();
         var newCell2 = newRow.insertCell();
         var newCell3 = newRow.insertCell();
         newCell0.innerHTML = "<input name='attendanceDate' type='text' id='control_date' size='20' maxlength='10' onclick='new Calendar().show(this);' readonly='readonly' /> ";
         newCell1.innerHTML = "<input class='status' name='attendanceStatus' type='text' value=''/>";
         newCell2.innerHTML = "<input class='remarks' name='remarks' type='text' value=''/>";
         
		 newCell3.innerHTML = "<a onclick='deleteRow(this)'>删除行</a>";
		 
     }
     function deleteRow(obj){
    	 var table  =  document.getElementById("tbl");
    	 var trlist = table.getElementsByTagName('tr');
    	 var tr = obj.parentNode.parentNode; 
    	 for(var i=0;i<trlist.length;i++){
    		 if(tr == trlist[i]){
    			 table.deleteRow(i);
    		 }
    	 }
     }
</script>
</html>