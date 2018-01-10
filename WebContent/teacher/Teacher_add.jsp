<%@ page language="java" import="java.util.*" contentType="text/html; charset=utf-8" %>
<%@page import="com.vp.model.Department" %>
<%@page import="javax.servlet.http.*" %>
<%@page import="java.util.List" %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@page import="com.vp.action.Util" %>
<%@page import="com.vp.model.Teacher" %>
<%@page import="java.text.SimpleDateFormat" %>

<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<meta http-equiv="content-type" content="text/html;charset=UTF-8">
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
<link rel="stylesheet" type="text/css" href="../css/default.css" />


<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%
	Util util = new Util();
	List<Department> allDepartment1s = util.getAllDepartment1();
	List<Department> allDepartment2s = util.getAllDepartment2();
	List<String> allTitles = util.getAllValueByName("职称");
	List<String> allStatus = util.getAllValueByName("状态");
	List<String> allRanks = util.getAllValueByName("行政级别");
	Teacher t = new Teacher();
	t = (Teacher)request.getAttribute("teacher")==null?t:(Teacher)request.getAttribute("teacher");
	System.out.println("t====="+t);
	SimpleDateFormat formatter = new SimpleDateFormat ("yyyy-MM-dd");
	
%>
<body>
<script type="text/javascript" src="../js/Calendar3.js"></script>
<div id="navi">
	<div id='naviDiv'>
		<span><img src="../images/arror.gif" width="7" height="11" border="0" alt=""></span>&nbsp;教师管理<span>&nbsp;
		<span><img src="../images/arror.gif" width="7" height="11" border="0" alt=""></span>&nbsp;<a href="<%=path%>/teacher/Teacher_teacherSearch.action">教师列表</a><span>&nbsp;
		<span><img src="../images/arror.gif" width="7" height="11" border="0" alt=""></span>&nbsp;<a href="<%=path%>/teacher/Teacher_add.jsp">教师添加</a><span>&nbsp;
	</div>
</div>

<div id="mainContainer">
<!-- 从session中获取学生集合 -->
<h2><strong>添加教师资料</strong></h2>
<br>
<br>
<form action="<%=path%>/teacher/Teacher_add.action" method="post">
<div>
	<s:fielderror/> <!-- 显示表单验证的出错信息 -->
</div>
<table>
  <tr>
    <th width="30%">教师编号：</th>
    <td><input type="text" name="code" <%if(t.getCode()!=null){%>value="<%=t.getCode() %>" <%} %>/></td>
    <th width="30%">婚姻状况：</th>
    <td><input type="text" name="marriage" <%if(t.getMarriage()!=null){%> value="<%=t.getMarriage()%>" <%} %>/></td>
  </tr>
  <tr>
    <th width="30%">姓名：</th>
    <td><input type="text" name="name" <%if(t.getName()!=null){%> value="<%=t.getName()%>" <%} %>/></td>
    <th width="30%">联系电话：</th>
    <td><input type="text" name="phone" <%if(t.getPhone()!=null){%> value="<%=t.getPhone()%>" <%} %>/></td>
  </tr>
  <tr>
    <th>性别：</th>
    <td>
	    <input type="radio" name="gender" value="男" checked="checked"/>男
	    <input type="radio" name="gender" value="女"/>女
    </td>
    <th width="30%">籍贯：</th>
    <td><input type="text" name="nativeplace" <%if(t.getNativeplace()!=null){%> value="<%=t.getNativeplace()%>" <%} %>/></td>
  </tr>
  <tr>
    <th>出生日期：</th>
    <td><input name="birthday" type="text" id="control_date" size="20"
      maxlength="10" onclick="new Calendar().show(this);" readonly="readonly" 
      <%if(t.getBirthday()!=null){%>value="<%=formatter.format(t.getBirthday()) %>" <%} %>/>
    </td>
    <th width="30%">民族：</th>
    <td><input type="text" name="nation" <%if(t.getNation()!=null){%> value="<%=t.getNation()%>" <%} %>/></td>
  </tr>
  <tr>
    <th>地址：</th>
    <td><input type="text" name="address" <%if(t.getAddress()!=null){%> value="<%=t.getAddress()%>" <%} %>/></td>
    <th>政治面貌：</th>
    <td><input type="text" name="politics" <%if(t.getPolitics()!=null){%> value="<%=t.getPolitics()%>" <%} %>/></td>
    
  </tr>
  <tr>
    <th>部门1：</th>
    <td>
    	<select id="depart1" name="depart1" onchange="changeDepart()">
			<option value=""></option>
			<% if(t.getDepartment()!=null && t.getDepartment().getDepartmentLevel().equals("一级")){%>
				<%for(Integer i=0;i<allDepartment1s.size();i++){ %>
				<option value="<%=allDepartment1s.get(i).getId()%>" <%if(t.getDepartment().getId()==allDepartment1s.get(i).getId()){%> selected="selected" <%} %> >
				<%=allDepartment1s.get(i).getDepartmentName() %>
				</option>
				<%}%>
			<%}else if(t.getDepartment()!=null && t.getDepartment().getDepartmentLevel().equals("二级")){ %>
				<%for(Integer i=0;i<allDepartment1s.size();i++){ %>
				<option value="<%=allDepartment1s.get(i).getId()%>" <%if(t.getDepartment().getFatherDept().getId()==allDepartment1s.get(i).getId()){%> selected="selected" <%} %> >
				<%=allDepartment1s.get(i).getDepartmentName() %>
				</option>
				<%}%>
			<%}else{ %>
				<%for(Integer i=0;i<allDepartment1s.size();i++){ %>
				<option value="<%=allDepartment1s.get(i).getId()%>">
				<%=allDepartment1s.get(i).getDepartmentName() %>
				</option>
				<%}%>
			<%} %>
			
		</select>
    </td>
    <th>状态：</th>
  	<td>
  		<select id="status" name="status" >
  				<option value=""></option>
				<%for(Integer i=0;i<allStatus.size();i++){ %>
				<option value="<%=allStatus.get(i)%>" <%if(t.getStatus()!=null && t.getStatus().equals(allStatus.get(i))){%> selected="selected" <%} %>>
				<%=allStatus.get(i)%>
				</option>
				<%}%>
		 </select>
  	</td>
  </tr>
  <tr>
    <th>部门2：</th>
    <td>
    	<% if(t.getDepartment()!=null && t.getDepartment().getDepartmentLevel().equals("二级")){%>
    	<select id="depart2" name="depart2">
			<option value="<%= t.getDepartment().getId()%>"><%= t.getDepartment().getDepartmentName()%></option>
		</select>
		<%}else{ %>
		<select id="depart2" name="depart2">
			<option value=""></option>
		</select>
		<%} %>
    </td>
    <th>身份证号：</th>
    <td><input type="text" name="idcard" <%if(t.getIdcard()!=null){%> value="<%=t.getIdcard()%>" <%} %>/></td>
  </tr>
  <tr>
  	
  	<th>就职日期：</th>
  	<td>
  		<input name="workdate" type="text" id="control_date" size="20"
      maxlength="10" onclick="new Calendar().show(this);" readonly="readonly" 
      <%if(t.getWorkdate()!=null){%>value="<%=formatter.format(t.getWorkdate()) %>" <%} %>/>
  	</td>
  	<th>退休日期：</th>
  	<td>
  		<input name="retiredate" type="text" id="control_date" size="20"
      maxlength="10" onclick="new Calendar().show(this);" readonly="readonly" 
      <%if(t.getRetiredate()!=null){%>value="<%=formatter.format(t.getRetiredate()) %>" <%} %>/>
  	</td>
  </tr>
  <tr>
  	<th>离职日期：</th>
  	<td>
  		<input name="quitdate" type="text" id="control_date" size="20"
      maxlength="10" onclick="new Calendar().show(this);" readonly="readonly" 
      <%if(t.getQuitdate()!=null){%>value="<%=formatter.format(t.getQuitdate()) %>" <%} %>/>
  	</td>
  	<th>离职原因：</th>
  	<td><input type="text" name="quitreason" <%if(t.getQuitreason()!=null){%> value="<%=t.getQuitreason()%>" <%} %>/></td>
  </tr>
  <tr>
  	<th>职称：</th>
    <td>
		<select id="title" name="title" >
			<option value=""></option>
			<%for(Integer i=0;i<allTitles.size();i++){ %>
			<option value="<%=allTitles.get(i)%>" <%if(t.getTitle()!=null && t.getTitle().equals(allTitles.get(i))){%> selected="selected" <%} %>>
			<%=allTitles.get(i)%></option>
			<%}%>
		 </select>
	</td>
    <th>晋职时间：</th>
    <td>
		<input name="titleDate" type="text" id="control_date" size="20"
	      maxlength="10" onclick="new Calendar().show(this);" readonly="readonly" 
	      <%if(t.getTitleDate()!=null){%>value="<%=formatter.format(t.getTitleDate()) %>" <%} %>/>
    </td>
  </tr>
  <tr>
  	<th>级别名称(行政)：</th>
    <td>
		<select id="rank" name="rank" >
   			<option value=""></option>
			<%for(Integer i=0;i<allRanks.size();i++){ %>
			<option value="<%=allRanks.get(i)%>" <%if(t.getRank()!=null && t.getRank().equals(allRanks.get(i))){%> selected="selected" <%} %> >
			<%=allRanks.get(i)%></option>
			<%}%>
		 </select>
	</td>
    <th>任职时间(行政)：</th>
    <td>
		<input name="rankDate" type="text" id="control_date" size="20"
	      maxlength="10" onclick="new Calendar().show(this);" readonly="readonly" 
	      <%if(t.getRankDate()!=null){%>value="<%=formatter.format(t.getRankDate()) %>" <%} %>/>
    </td>
  </tr>
  
</table>
<div id="btn">
<input class="button" type="submit" value="添加" style="margin: 0 45%; display: block;">
</div>
<br><br>
</form>

</div>
<script type="text/javascript">
function changeDepart(){
	var depart1 = document.getElementById("depart1");
	var depart2 = document.getElementById("depart2");
	var index = depart1.selectedIndex; 
	var departid = depart1.options[index].value;
	var str = "<option value=''></option>";
	var index1 = <%= allDepartment2s.size()%>;
	<%for(Integer i = 0;i<allDepartment2s.size();i++){%>
		var depart1Id = <%= allDepartment2s.get(i).getFatherDept().getId()%>
		if(depart1Id == departid){
			var depart2id = <%=allDepartment2s.get(i).getId()%>;
			var depart2name = "";
			var depart2name = "<%=allDepartment2s.get(i).getDepartmentName()%>";
			str += "<option value="+depart2id+">";
			str += depart2name;
			str += "</option>";
		}
	<%}%>
	depart2.innerHTML = str;
	
}
</script>
</body>
</html>