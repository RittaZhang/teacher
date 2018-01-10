<%@ page language="java" import="java.util.*" contentType="text/html; charset=utf-8" %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<html>
<head>
<meta http-equiv="content-type" content="text/html;charset=UTF-8">
</head>
<body>
<form name="nobatch" action="<%=path%>/upload/UpLoad_uploadExcel.action" method="post" enctype = "multipart/form-data">
<input type="text" name="usename"> <br/>
<input type="file" name="file1" accept=".xls,.xlsx"/>
<input type="submit" value="提交">
<a href="<%=path%>/export/excelExport_exportExcel.action">下载excel</a><br/>
<a href="<%=path%>/teacher/Teacher_testInsert.action">插入一千条教师数据</a>
</form>
</body>
</html>