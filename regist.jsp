<%--
  Created by IntelliJ IDEA.
  User: AnZH
  Date: 2023/4/18
  Time: 20:26
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>注册页面</title>
</head>
<body>
<form action = "/doregist.jsp" method = "post">
    <input type = "text" placeholder="请输入用户名"  name = "username"><br>
    <input type="text" placeholder="请输入密码" name = "password"><br>
    <select id="gender" name = "gender">
        <option value="male">男</option>
        <option value="female">女</option>
    </select><br>
    <input type="submit" name = "注册">
</form>
</body>
</html>
