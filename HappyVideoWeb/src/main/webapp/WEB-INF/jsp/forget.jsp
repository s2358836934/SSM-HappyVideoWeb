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
    <title>忘记密码</title>
</head>
<body>

<h1>密码找回</h1>
<form action="/findPwd" method="post">
    邮箱：<input type="text" name="email">

    <input type="submit">
</form>

</body>
</html>
