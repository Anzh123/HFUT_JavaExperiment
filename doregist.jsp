<%@ page import="java.io.BufferedReader" %>
<%@ page import="java.io.FileReader" %>
<%@ page import="java.io.BufferedWriter" %>
<%@ page import="java.io.FileWriter" %><%--
  Created by IntelliJ IDEA.
  User: AnZH
  Date: 2023/4/18
  Time: 20:38
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>注册</title>
</head>
<body>
<%
    request.setCharacterEncoding("UTF-8");
    String filename = request.getServletContext().getRealPath("/user.txt");
    String username = request.getParameter("username");
    String password = request.getParameter("password");
    String gender = request.getParameter("gender");
    if ( !username.equals("") && !password.equals("")){
        BufferedReader br = new BufferedReader(new FileReader(filename));
        String temp ;
        boolean flag = true;
        while((temp = br.readLine()) != null){
            String[] element = temp.split(",");
            if (element[0].equals(username)){
                response.getWriter().write("该用户已存在");
                flag = false;
            }
        }
        if(flag){
            BufferedWriter bw = new BufferedWriter(new FileWriter(filename));
            bw.write(username + "," + password + "," + gender);
            bw.flush();
            bw.close();
            response.getWriter().write("注册成功");
        }

    }
   else {
       response.getWriter().write("你输入的用户名为空");
    }
%>
</body>
</html>
