<%@ page language="java" import="java.util.*" contentType="text/html; charset=utf-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@page import="com.vp.model.Selection" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
List<String> groupName_list = (List<String>)request.getAttribute("groupName_list");
String groupname_value = (String)request.getAttribute("groupname_value");
System.out.println("groupname_value1------"+groupname_value);
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
#buttonGroup{
	padding-left:10px;
	float:left;
	height:35px;
}
.button{
	/*float:left;
	margin-right:10px;
	padding-left:10px;
	padding-right:10px;*/
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
	var formElement = document.getElementById("selectForm");
	formElement.action = actionUrl;
	formElement.submit();
}

// 第一页
function firstPage(){
	if(currentPage == 1){
		alert("已经是第一页数据");
		return false;
	}else{
		submitForm("<%=path%>/selection/Selection_query.action");
		return true;
	}
}

// 下一页
function nextPage(){
	if(currentPage == totalPage){
		alert("已经是最后一页数据");
		return false;
	}else{
		submitForm("<%=path%>/selection/Selection_query.action?page=" + (currentPage+1));
		return true;
	}
}

// 上一页
function previousPage(){
	if(currentPage == 1){
		alert("已经是第一页数据");
		return false;
	}else{
		submitForm("<%=path%>/selection/Selection_query.action?page="+(currentPage - 1)) ;
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
		submitForm("<%=path%>/selection/Selection_query.action?page="+totalPage);
		return true;
	}
}

function validate(){
    var page = document.getElementsByName("page")[0].value;
    if(page > <s:property value="#request.pageBean.totalPage"/>){
        alert("你输入的页数大于最大页数，页面将跳转到首页！");
        
        window.document.location.href = "<%=path%>/selection/Selection_query.action";
        
        return false;
    }
    return true;
}

</script>
<body>
<div id="navi">
	<div id='naviDiv'>
		<span><img src="../images/arror.gif" width="7" height="11" border="0" alt=""></span>&nbsp;选择项管理<span>&nbsp;
		<span><img src="../images/arror.gif" width="7" height="11" border="0" alt=""></span>&nbsp;<a href="<%=path%>/selection/Selection_query.action">选择项列表</a><span>&nbsp;
	</div>
</div>
<div id="tips">
	<form action="<%=path%>/selection/Selection_query.action" id="selectForm" method="post">
		<table>
			<tr>
				<td>
					分组名：<select id="groupname" name="groupname" >
								<%if(groupName_list!=null){ %>
								<%for(String groupname:groupName_list){ %>
								<option <% if(groupname.equals(groupname_value)){%> selected="selected" <%} %> value="<%=groupname%>"><%=groupname %></option>
								<%}}%>
							</select>
				</td>
				<td><input  class="button" type="submit" value="检索" /></td>
			</tr>
		</table>
	</form>
</div>

<div id="mainContainer">
<!-- 从session中获取选择项集合 -->

<table class="default" width="100%">
	<tr class="title">
		<td>顺序</td>
		<td>分组名</td>
		<td>选项值</td>
		<td>备注</td>
		<td>操作</td>
	</tr>
	
	<!-- 遍历开始 -->
	<s:iterator value="#request.pageBean.list" var="sel">
	<tr class="list">
		<td><s:property value="#sel.optionorder"/></td>
		<td><s:property value="#sel.groupname"/></td>
		<td><s:property value="#sel.optionvalue"/></td>
		<td><s:property value="#sel.remarks"/></td>
		<td><a href="<%=path%>/selection/Selection_modify.action?sid=<s:property value="#sel.id"/>" >编辑</a></td>
	</tr>
	</s:iterator>
	<!-- 遍历结束 -->
</table>
<s:if test="#request.pageBean.list.size()==0">
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
&nbsp;&nbsp;&nbsp;
<form action="<%=path%>/selection/Selection_addSelection.action" method="post">
<%if(groupName_list!=null){ %>
<s:if test="#request.pageBean.list!=null && #request.pageBean.list.size()>0">
<input type="hidden" name="groupname" value="${pageBean.list.get(0).getGroupname()}"/>
</s:if>
<%} %>

选项值：<input type="text" name="optionvalue"/>
	    <input  class="button" type="submit" value="添加" />
	     <div>
			 <s:fielderror/> <!-- 显示表单验证的出错信息 -->
		 </div>
</form>
</div>
</body>
</html>