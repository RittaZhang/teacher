<%@ page language="java" import="java.util.*" contentType="text/html; charset=utf-8" %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@page import="com.vp.model.Department" %>
<%@page import="com.vp.action.Util" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
Util util = new Util();
List<Department> allDepartment1s = util.getAllDepartment1();
Department d = new Department();
d=(Department)request.getAttribute("department")==null?d:(Department)request.getAttribute("department");
%>
<!DOCTYPE html>
<html>
	<meta http-equiv="content-type" content="text/html;charset=UTF-8">
	<link rel="stylesheet" type="text/css" href="../css/default.css" />
<style type="text/css">
* {
    background: none repeat scroll 0 0 transparent;
    border: 1 none;
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
	margin-top:20px;
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
	text-align:left;
	width:98%;
	font-size:16px;
}
</style>
<body>
<script type="text/javascript" src="../js/Calendar3.js"></script>

<div id="navi">
	<div id='naviDiv'>
		<span><img src="../images/arror.gif" width="7" height="11" border="0" alt=""></span>&nbsp;部门管理<span>&nbsp;
		<span><img src="../images/arror.gif" width="7" height="11" border="0" alt=""></span>&nbsp;<a href="<%=path%>/department/Department_query.action">部门列表</a><span>&nbsp;
	</div>
</div>
<div id="tips">
</div>
<div id="mainContainer">
<!-- 从session中获取部门集合 -->
<strong>添加部门资料</strong>
<br>
<br>
<form name="addForm" action="<%=path%>/department/Department_add.action" method="post">
<table width="500" >

  <tr>
    <td>部门级别：</td>
    <td>
  		<input type="radio" name="departmentLevel" value="一级" onchange="changeDepart()" <%if(d.getDepartmentLevel()!=null&&d.getDepartmentLevel().equals("一级")){ %>checked="checked"<%} %>/>一级
  		<input type="radio" name="departmentLevel" value="二级" onchange="changeDepart()" <%if(d.getDepartmentLevel()==null||!d.getDepartmentLevel().equals("一级")){ %>checked="checked" <%} %>/>二级
   	</td>
  </tr>
  <tr>
    <td>部门编号：</td>
    <td><input type="text" name="departmentCode" <%if(d.getDepartmentCode()!=null){ %>value="<%=d.getDepartmentCode()%>"<%} %> /></td>
  </tr>
  <tr>
    <td>部门名称：</td>
    <td><input type="text" name="departmentName" <%if(d.getDepartmentName()!=null){ %>value="<%=d.getDepartmentName()%>"<%} %>/></td>
  </tr>
  <tr id="fatherDeptId">
    <td>上级部门：</td>
    <td>
    	<select id="depart1" name="depart1" >
			<%for(Integer i=0;i<allDepartment1s.size();i++){ %>
			<option value="<%=allDepartment1s.get(i).getId()%>" <%if(d.getDepartmentLevel()!=null&&d.getDepartmentLevel().equals("二级")&&d.getFatherDept().getId()==allDepartment1s.get(i).getId()){ %>selected="selected"<%} %>>
			<%=allDepartment1s.get(i).getDepartmentName() %>
			</option>
			<%}%>
		</select>
    </td>
  </tr>
  <tr>
    <td>备注：</td>
    <td><input  name="remarks" <%if(d.getRemarks()!=null){ %>value="<%=d.getRemarks()%>"<%} %>/></input>
  </tr>
  <tr>
    <td colspan="2" align="center"><input class="button" type="submit" value="添加"></td>
  </tr>
  <div>
	  <s:fielderror/> <!-- 显示表单验证的出错信息 -->
  </div>

</table>
</form>


</div>
</body>
<script type="text/javascript">
window.onload=function(){
	changeDepart();
}
function changeDepart(){
	var level= document.getElementsByName('departmentLevel');
	for (var i = 0; i < level.length; i++) {
		if (level[i].checked == true) {//如果选中，下面的alert就会弹出选中的值
			if(level[i].value =='一级'){
				document.getElementById('fatherDeptId').style.visibility="hidden";
			}else{
				document.getElementById('fatherDeptId').style.visibility="visible";
			}
		}
	}
}
</script>
</html>