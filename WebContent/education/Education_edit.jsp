<%@ page language="java" import="java.util.*" contentType="text/html; charset=utf-8" %>
<%@page import="javax.servlet.http.*" %>
<%@page import="java.util.List" %>
<%@page import="com.vp.action.Util" %>
<%@page import="java.text.SimpleDateFormat" %>
<%@page import="java.util.Date" %>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%
Util util = new Util();
List<String> allSchool = util.getAllValueByName("毕业院校");
List<String> allMajor = util.getAllValueByName("所学专业");
List<String> allDegree = util.getAllValueByName("学历层次");
String[] graduate = (String[])request.getParameterValues("graduate");
String[] major = (String[])request.getParameterValues("major");
String[] degree = (String[])request.getParameterValues("degree");
String[] graduateDate = (String[])request.getParameterValues("graduateDate");

SimpleDateFormat formatter = new SimpleDateFormat ("yyyy-MM-dd");
%>
<!DOCTYPE html >
<html>
<head>
<meta http-equiv="content-type" content="text/html;charset=UTF-8">
<link rel="stylesheet" type="text/css" href="../css/default.css" />
<link rel="stylesheet" href="../css/jquery-ui.min.css">
<script type="text/javascript" src="../js/jquery-1.11.3.min.js"></script>
<script type="text/javascript" src="../js/jquery-ui.min.js"></script>
<script type="text/javascript" src="../js/Calendar3.js"></script>

<script>
  $(function() {
	var schools=new Array(); 
	var index = 0;
	var length = <%=allSchool.size()%>
	<%for(Integer i = 0;i<allSchool.size();i++){%>
		schools[index] = "<%=allSchool.get(i)%>";
		index++;
	<%}%>
    $( ".school" ).autocomplete({
      source: schools
    });
  });
  </script>
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
	text-align:center;
	width:70%;
	margin-left:10%;
}
table th{
	text-align:right;
}

</style>
</head>
<body>
	<div id="navi">
		<div id='naviDiv'>
			<span><img src="../images/arror.gif" width="7" height="11" border="0" alt=""></span>&nbsp;学历管理<span>&nbsp;
			<span><img src="../images/arror.gif" width="7" height="11" border="0" alt=""></span>&nbsp;<a href="<%=path%>/education/Education_educationSearch.action">学历列表</a><span>&nbsp;
			<span><img src="../images/arror.gif" width="7" height="11" border="0" alt=""></span>&nbsp;<a href="<%=path%>/education/Education_educationEdit.action?tid=<s:property value="#edu_teacher.id"/>">学历编辑</a><span>&nbsp;
		</div>
	</div>
	<div id="mainContainer">
	<form  action="<%=path%>/education/Education_educationUpdate.action" method="post">
		<input type="hidden" name="tid" value="<s:property value="#edu_teacher.id" />"/>
		<div style="text-align:left;">
		<h2><strong>教师学历编辑</strong></h2>
		<br>
		<h3>教师信息</h3>
		<table style="width:200px;margin-left:1%;">
			<tr>
				<th>姓名：</th>
				<td><s:property value="#edu_teacher.name" /></td>
				<th>编号：</th>
				<td><s:property value="#edu_teacher.code" /></td>
			</tr>
		</table>
		<br>
		<h3>学历信息</h3>
		</div>
		<div>
			<s:fielderror/> <!-- 显示表单验证的出错信息 -->
		</div>
		<table class="default" id="tbl">
			<tr class="title">
				<td style="width:30%">毕业院校</td>
				<td style="width:20%">专业</td>
				<td style="width:15%">学历层次</td>
				<td style="width:25%">毕业时间</td>
				<td style="width:10%">操作</td>
			</tr>
			<s:iterator value="#edu_teacher.educations" status="bcs" var="e">
			<tr class="list">
				<td>
					<s:property value="#e.graduateInstitutions" />
				</td>
				<td>
					<s:property value="#e.major" />
				</td>
				<td>
					<s:property value="#e.degree" />
				</td>
				<td>
					<s:date name="#e.graduateDate" format="yyyy-MM-dd"/>
				</td>
				<td>
					<a onclick="if(confirm('确定删除?')==false)return false;" href="<%=path%>/education/Education_educationDelete.action?eid=<s:property value="#e.id"/>&tid=<s:property value="#edu_teacher.id"/>">删除</a>
				</td>
			</tr>
			</s:iterator>
			<%if(graduate!=null){ %>
			<%for(Integer i=0;i<graduate.length;i++){ %>
				<tr class="list">
					<td>
					<input class="school" name="graduate" type="text" value="<%=graduate[i]%>"/>
					</td>
					<td>
					<select name="major">
					<%if(major[i]==""){ %>
					<option></option>
					<% }%>
					<%for(Integer j=0;j<allMajor.size();j++){ %>
			        <option value="<%=allMajor.get(j)%>" <%if(allMajor.get(j).equals(major[i])){ %>selected="selected"<%} %> ><%=allMajor.get(j)%></option>
					<%} %>
					</select>
					</td>
				<td>
					<select name="degree">
					<%if(degree[i]==""){ %>
					<option></option>
					<% }%>
					<%for(Integer j=0;j<allDegree.size();j++){ %>
		         	<option value="<%=allDegree.get(j)%>" <%if(allDegree.get(j).equals(degree[i])){ %>selected="selected"<%} %>><%=allDegree.get(j)%></option>
					<%} %>
					</select>
				</td>
				<td>
				<input value="<%=graduateDate[i]%>" name="graduateDate" type="text" id="control_date" size="20" maxlength="10" onclick="new Calendar().show(this);" readonly="readonly" />
				</td>
				<td>
					<a onclick="deleteRow(this)">删除行</a>
				</td>
			</tr>
			<%} }%>
		</table>
		<br><br>
		<input class="button" type="button" value="添加一行" onclick="addRow()">&nbsp;&nbsp;
		<input class="button" type="button" value="添加新院校" onclick="addSchool()">&nbsp;&nbsp;
		<input class="button" type="submit" value="更新"/>
	</form>
	</div>
	<div id="addSchool" style="display:none">
	<form  action="<%=path%>/selection/Selection_addSchool.action" method="post">
		<div>
			<h3>院校新规登录画面</h3>
			<div>
				<input type="hidden" name="tid" value="<s:property value="#edu_teacher.id" />"/>
				<input type="text" name="school" id="input_school"/>
			</div>
			<div>
				<input type="submit" value="保存" />
				<input type="button" value="取消" onclick="hiddenDiv()"/>
			</div>
		</div>
	</form>
	</div>
<script type="text/javascript">
     function addRow(){
    	 var table  =  document.getElementById("tbl");
    	 var trlist = table.getElementsByTagName('tr');
    	 
    	 var newRow = table.insertRow();
    	 var newCell0 = newRow.insertCell();
         var newCell1 = newRow.insertCell();
         var newCell2 = newRow.insertCell();
         var newCell3 = newRow.insertCell();
         var newCell4 = newRow.insertCell();
         newCell0.innerHTML = "<input class='school' name='graduate' type='text' value=''/>";
         var cell1 = "<select name='major'><option></option>";
         <%for(Integer i=0;i<allMajor.size();i++){ %>
         var value1 = "<%=allMajor.get(i)%>";
         cell1+="<option value='"+value1+"'>"+value1+"</option>";
		 <%} %>
         
         newCell1.innerHTML = cell1;
         var cell2 = "<select name='degree'><option></option>";
         <%for(Integer i=0;i<allDegree.size();i++){ %>
         var value2 = "<%=allDegree.get(i)%>";
         cell2+="<option value='"+value2+"'>"+value2+"</option>";
		 <%} %>
		 newCell2.innerHTML = cell2;
		 newCell3.innerHTML = "<input name='graduateDate' type='text' id='control_date' size='20' maxlength='10' onclick='new Calendar().show(this);' readonly='readonly' /> ";
		 newCell4.innerHTML = "<a onclick='deleteRow(this)'>删除行</a>";
		 getschool();
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
     function getschool(){
    	 var schools=new Array(); 
   		 var index = 0;
   		 var length = <%=allSchool.size()%>
   		 <%for(Integer i = 0;i<allSchool.size();i++){%>
   		 	schools[index] = "<%=allSchool.get(i)%>";
   		 	index++;
   		 <%}%>
   	     $( ".school" ).autocomplete({
   	       source: schools
   	     });
     }
     function addSchool(){
    	 var div = document.getElementById("addSchool");
    	 div.style.display="";
     }
     function hiddenDiv(){
    	 var div = document.getElementById("addSchool");
    	 var input_school = document.getElementById("input_school");
    	 input_school.value="";
    	 div.style.display="none";
     }
 </script>
</body>
</html>