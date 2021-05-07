<%--
  Created by IntelliJ IDEA.
  User: SupunN
  Date: 3/30/2021
  Time: 08:23
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
    session.setAttribute("sessionSt", "Logout");
    request.setAttribute("session","Logout");
    request.getRequestDispatcher("/login.jsp").forward(request, response);
%>
