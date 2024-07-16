<%@ page import="org.jdbc.Page.*" %>
<%@ page import="java.util.List" %><%--
  Created by IntelliJ IDEA.
  User: nan
  Date: 2024/7/1
  Time: 下午4:28
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <title>Title</title>
</head>
<body>
<table class="table table-hover ">
    <thead>
    <tr>
        <th>账号</th>
        <th>密码</th>
        <th>姓名</th>
        <th>性别</th>
        <th>班级</th>
        <th>班主任</th>
        <th>操作</th>
    </tr>
    </thead>
    <tbody>
    <% List<Student> arrayList= (List<Student>) request.getAttribute("list");
        for (int i = 0; i < arrayList.size(); i++) {
            Student student = arrayList.get(i);%>
    <tr>
        <td><%=student.getUsername()%></td>
        <td><%=student.getPassword()%></td>
        <td><%=student.getSname()%></td>
        <td><%=student.getSsex()%></td>
        <td><%=student.getClasss()%></td>
        <td><%=student.getHead_teacher()%></td>
    </tr>
    <% }%>
    </tbody>
</table>
<%@include file="xxx.jsp"%>
</body>
</html>
