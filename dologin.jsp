<%@ page import="java.io.BufferedReader" %>
<%@ page import="java.io.BufferedWriter" %>
<%@ page import="java.io.FileReader" %><%--
  Created by IntelliJ IDEA.
  User: AnZH
  Date: 2023/4/18
  Time: 20:29
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>登录</title>
</head>
<body>
<%
    request.setCharacterEncoding("UTF-8");
    String filename = request.getServletContext().getRealPath("/user.txt");
    String username = request.getParameter("username");
    String password = request.getParameter("password");
    BufferedReader br1 = new BufferedReader(new FileReader(filename));
    String temp;
    String[] element;
    boolean flag = false;
    while ( (temp = br1.readLine()) != null){
        element = temp.split(",");
        if (element[0].equals(username)){
           if ( element[1].equals(password)){
               flag = true;
           }
        }
    }
    if (flag){
        response.getWriter().write("登录成功");
    }
    else {
        response.getWriter().write("用户名或密码错误");
    }
%>
</body>
</html>
