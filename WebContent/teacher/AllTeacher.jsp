<%@ page language="java" import="java.util.*" contentType="text/html; charset=utf-8" %>
<%@page import="com.vp.model.Department" %>
<%@page import="javax.servlet.http.*" %>
<%@page import="java.util.List" %>
<%@page import="com.vp.action.Util" %>
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
		submitForm("<%=path%>/teacher/Teacher_teacherSearch.action");
		return true;
	}
}

// 下一页
function nextPage(){
	if(currentPage == totalPage){
		alert("已经是最后一页数据");
		return false;
	}else{
		submitForm("<%=path%>/teacher/Teacher_teacherSearch.action?page=" + (currentPage+1));
		return true;
	}
}

// 上一页
function previousPage(){
	if(currentPage == 1){
		alert("已经是第一页数据");
		return false;
	}else{
		submitForm("<%=path%>/teacher/Teacher_teacherSearch.action?page="+(currentPage - 1)) ;
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
		submitForm("<%=path%>/teacher/Teacher_teacherSearch.action?page="+totalPage);
		return true;
	}
}
</script>
<%
	Util util = new Util();
	List<Department> allDepartment1s = util.getAllDepartment1();
	List<Department> allDepartment2s = util.getAllDepartment2();
	List<String> allTitles = util.getAllValueByName("职称");
	List<String> allRanks = util.getAllValueByName("行政级别");
	String tname = (String)request.getAttribute("tname");
	String depart1 = (String)request.getAttribute("depart1");
	String depart2 = (String)request.getAttribute("depart2");
	String title = (String)request.getAttribute("title");
	String rank = (String)request.getAttribute("rank");
	String d2 = "";
	System.out.println("depart1===="+depart1);
	if(depart2!=null && !depart2.isEmpty()){
		for(Department d:allDepartment2s){
			if(depart2.equals(String.valueOf(d.getId()))){
				d2 = d.getDepartmentName();
				System.out.println("d.getDepartmentName()===="+d.getDepartmentName());
			}
		}
	}
%>
<body>
	<div id="navi">
		<div id='naviDiv'>
			<span><img src="../images/arror.gif" width="7" height="11" border="0" alt=""></span>&nbsp;教师管理<span>&nbsp;
			<span><img src="../images/arror.gif" width="7" height="11" border="0" alt=""></span>&nbsp;<a href="<%=path%>/teacher/Teacher_teacherSearch.action">教师列表</a><span>&nbsp;
		</div>
	</div>
	<div id="tips">
		<form name="nobatch" action="<%=path%>/teacher/Teacher_teacherSearch.action" id="teacherForm" method="post">
			<table style="width:100%;">
				<tr>
					<td style="width:15%;">
					姓名：<input  style="width:90px" type="text" id="tname" name="tname" value="<%=tname%>">
					</td>
					<td style="width:20%;">
					部门1：<select id="depart1" style="max-width:140px" size="1" name="department1" onchange="changeDepart()">
						<option value=""></option>
							<%for(Integer i=0;i<allDepartment1s.size();i++){ %>
							<option value="<%=allDepartment1s.get(i).getId()%>" <%if(String.valueOf(allDepartment1s.get(i).getId()).equals(depart1)){ %> selected = "selected"<%} %> ><%=allDepartment1s.get(i).getDepartmentName() %></option>
							<%}%>
						</select>
					</td>
					<td style="width:18%;">
					部门2：<select id="depart2" style="max-width:120px" size="1" name="department2">
							<option></option>
							<%if(depart2!=null && !depart2.isEmpty()){ %>
							<option value="${depart2}" selected="selected"><%=d2%></option>
							<%}  %>
						</select>
					</td>
					<td style="width:18%;">
					职称：<select id="title" name="ttitle" style="max-width:110px" size="1" >
							<option value=""></option>
							<%for(Integer i=0;i<allTitles.size();i++){ %>
							<option value="<%=allTitles.get(i)%>" <%if(allTitles.get(i).equals(title)){ %> selected = "selected"<%} %>><%=allTitles.get(i)%></option>
							<%}%>
					 </select>
					</td>
					<td style="width:22%;">
					级别名称(行政)：<select id="rank" name="trank" style="max-width:100px" size="1" >
								<option value=""></option>
								<%for(Integer i=0;i<allRanks.size();i++){ %>
								<option value="<%=allRanks.get(i)%>" <%if(allRanks.get(i).equals(rank)){ %> selected = "selected"<%} %>><%=allRanks.get(i)%></option>
								<%}%>
							 </select>
					</td>
					<td>
					<input type="submit" class="button" value="检索" />
					</td>
				</tr>
			</table>
			
		</form>
	</div>
	<form  action="<%=path%>/teacher/Teacher_teacherBatch.action" method="post">
	<div id="mainContainer">
		<table class="default" width="100%">
			<tr class="title">
				<td style="display:none" name="batch"></td>
				<td style="display:none" name="batch"></td>
				<td style="display:none" name="batch"></td>
				<td style="display:none" name="batch"></td>
				<td style="display:none" name="batch">
					<select id="rank-1" name="rank-1" >
						<option value=""></option>
						<%for(Integer i=0;i<allRanks.size();i++){ %>
						<option value="<%=allRanks.get(i)%>"><%=allRanks.get(i)%></option>
						<%}%>
					 </select>
				</td>
				<td style="display:none" name="batch">
					<select id="title-1" name="title-1" >
						<option value=""></option>
						<%for(Integer i=0;i<allTitles.size();i++){ %>
						<option value="<%=allTitles.get(i)%>"><%=allTitles.get(i)%></option>
						<%}%>
					 </select>
					
				</td>
				<td style="display:none" name="batch">
					<select id="depart1-1" name="depart1-1" onchange="changeDepart1()">
						<option value=""></option>
						<%for(Integer i=0;i<allDepartment1s.size();i++){ %>
						<option value="<%=allDepartment1s.get(i).getId()%>"><%=allDepartment1s.get(i).getDepartmentName() %></option>
						<%}%>
					</select> 
				</td>
				<td style="display:none" name="batch">
					<select id="depart2-1" name="depart2-1">
						<option value=""></option>
					</select>
				</td>
				
			</tr>
			<tr class="title">
				<td style="display:none" name="batch">选择</td>
				<td><a onclick="document.getElementById('teacherForm').action='<%=path%>/teacher/Teacher_teacherSearch.action?order=id';document.getElementById('teacherForm').submit()" >No</a></td>
				<td><a onclick="document.getElementById('teacherForm').action='<%=path%>/teacher/Teacher_teacherSearch.action?order=name';document.getElementById('teacherForm').submit()" >姓名</a></td>
				<td><a onclick="document.getElementById('teacherForm').action='<%=path%>/teacher/Teacher_teacherSearch.action?order=code';document.getElementById('teacherForm').submit()" >教师工号</a></td>
				<td><a onclick="document.getElementById('teacherForm').action='<%=path%>/teacher/Teacher_teacherSearch.action?order=rank';document.getElementById('teacherForm').submit()" >级别名称(行政)</a></td>
				<td><a onclick="document.getElementById('teacherForm').action='<%=path%>/teacher/Teacher_teacherSearch.action?order=title';document.getElementById('teacherForm').submit()" >职称</a></td>
				<td><a onclick="document.getElementById('teacherForm').action='<%=path%>/teacher/Teacher_teacherSearch.action?order=department';document.getElementById('teacherForm').submit()" >部门1</a></td>
				<td>部门2</td>
				<td name="nobatch">操作</td>
			</tr>
		<s:iterator value="#pageBean.list" status="bcs" var="t">
	        <tr class="list"> 
	        	<td style="display:none" name="batch"><input name="checkbox" type="checkbox" value="<s:property value="#t.id" />"/></td>
	            <td><s:property value="#t.id"></s:property></td>
	            <td><s:property value="#t.name"></s:property></td>
	            <td><s:property value="#t.code"></s:property></td>
	            <td><s:property value="#t.rank"></s:property></td>
	            <td><s:property value="#t.title"></s:property></td>
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
	            <td name="nobatch"><a href="<%=path%>/teacher/Teacher_toDetail.action?tid=<s:property value="#t.id"/>">详细</a></td>
	        </tr>
	    </s:iterator>
	    </table>
	    <s:if test="#pageBean.list.size()==0">
	        1/1页  首页&nbsp;&nbsp;&nbsp;上一页   下一页&nbsp;&nbsp;&nbsp;尾页
	    </s:if>
	    <s:else>
	    <div name="nobatch">  
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
	
	
	<div name="nobatch" class="button" onmouseout="this.style.backgroundColor='';this.style.fontWeight='normal'" onmouseover="this.style.backgroundColor='#77D1F6';this.style.fontWeight='bold'">
		<a onclick="batchact()">批量处理</a>
	</div>
	<div style="display:none" name="batch" class="button" onmouseout="this.style.backgroundColor='';this.style.fontWeight='normal'" onmouseover="this.style.backgroundColor='#77D1F6';this.style.fontWeight='bold'">
		<a onclick="nobatchact()">取消</a>
	</div>
	<input style="display:none" name="batch" class="button" type="submit" value="批量更新"/>
	<div name="nobatch" class="button" onmouseout="this.style.backgroundColor='';this.style.fontWeight='normal'" onmouseover="this.style.backgroundColor='#77D1F6';this.style.fontWeight='bold'">
		<a href="<%=path%>/teacher/Teacher_add.jsp">添加教师</a>
	</div>
	</form>
</body>	
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
	function changeDepart1(){
		var depart1 = document.getElementById("depart1-1");
		var depart2 = document.getElementById("depart2-1");
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
	function batchact(){
		var batch = document.getElementsByName("batch");
		var nobatch = document.getElementsByName("nobatch");
		for(var i=0;i<batch.length;i++){
			batch[i].style.display="";
		}
		for(var i=0;i<nobatch.length;i++){
			nobatch[i].style.display="none";
		}
	}
	function nobatchact(){
		var batch = document.getElementsByName("batch");
		var nobatch = document.getElementsByName("nobatch");
		var inputs = document.getElementsByTagName("input");
		for(var i=0;i<batch.length;i++){
			batch[i].style.display="none";
		}
		for(var j=0;j<inputs.length;j++){
			if(inputs[j].type=="checkbox"){
				inputs[j].checked=false;
			}
		}
		for(var i=0;i<nobatch.length;i++){
			nobatch[i].style.display="";
		}
	}

</script>
</html>