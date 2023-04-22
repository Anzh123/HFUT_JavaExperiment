<%--
  Created by IntelliJ IDEA.
  User: AnZH
  Date: 2023/4/18
  Time: 20:27
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>登录页面</title>
</head>
<body>
<form action="/dologin.jsp" method = "post">
    <input type="text" placeholder="输入用户名" name = "用户名"><br>
    <input type="text" placeholder="输入密码" name = "密码"><br>
    <input type="submit" name = "登录">
</form>
</body>
</html>
