<%--
  Created by IntelliJ IDEA.
  User: HSOD2
  Date: 2021-02-02
  Time: 21:15
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>重置密码页面</title>
</head>
<body>

<h1>设置新密码</h1>
<form action="/resetPwd" method="post">
    <input type="hidden" name="email" value="${email}">
    <input type="hidden" name="p" value="${p}">
    新密码：<input type="text" name="password">

    <input type="submit">
</form>

</body>
</html>
