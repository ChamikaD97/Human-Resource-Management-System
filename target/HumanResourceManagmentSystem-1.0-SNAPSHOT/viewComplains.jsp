<%@ page import="complains.ComplainDao" %>
<%@ page import="complains.ComplainBean" %>
<%@ page import="java.util.List" %><%--
  Created by IntelliJ IDEA.
  User: SupunN
  Date: 10/23/2020
  Time: 12:02 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Human Resource Management System</title>
    <link rel="icon" href="img/logo.png" sizes="25x25" type="image/png">
    <link rel="stylesheet" href="style/mainStyle.css">
    <link rel="stylesheet" href="style/viewComplain.css">
</head>
<body>

<div class="content">
    <div class="heading">
        <h3>View Complain/Suggestions</h3>
    </div>
    <%
        HttpSession sss = request.getSession(false);
        //Check Session Expired
        if (sss == null || sss.isNew()) {
            request.setAttribute("session", "Expired");
            request.getRequestDispatcher("/login.jsp").forward(request, response);
        }
        //Check Logout
        if(session.getAttribute("sessionSt").equals("Logout"))
        {
            //session.setAttribute("sessionSt", "Logout");
            request.setAttribute("session","Logout");
            request.getRequestDispatcher("/login.jsp").forward(request, response);
        }

        String result= (String) request.getAttribute("result");

        if(result=="Successful"){%>
        <h4 class="response" style="color: #4bbe19;">
            Complain/Suggestion  is Successfully Removed!
        </h4><%

        }request.setAttribute("result",null);
    %>
        <%
            if(result == "Unsuccessful"){%>
        <h4 class="response" style="color: #DC143C;">
            Unable To Remove This  Complain/Suggestion ! , Try Again.
        </h4>
        <%request.setAttribute("result",null);}
            request.setAttribute("result",null);
        %>
        <br>
    <div class="main">
        <table class="data">
            <tr>
                <td>
                    <label class="label">Complain/Suggestions Id</label>
                </td>
                <th>
                    <input class="input" type="text" name="comId" id="comId" readonly>
                </th>
            </tr>
            <tr>
                <td>
                    <label class="label">Date</label>
                </td>
                <th>
                    <input class="input" type="text" name="date" id="date" readonly>
                </th>
            </tr>
        </table>

        <table class="data">
            <tr>
                <th>
                    <textarea rows="5" cols="50" name="description" id="description" readonly></textarea>
                </th>
            </tr>
        </table>
    </div>
    <br>
    <%
        ComplainDao comDao = new ComplainDao();
        String empId= (String) session.getAttribute("empId");

        List<ComplainBean> complainList = comDao.allComplains(empId);
    %>
    <input class="input" type="number" name="empId" value="<%=session.getAttribute("empId")%>" hidden>
    <div class="result">
        <table id="result">
            <tr>
                <th>Id</th>
                <th>Date</th>
                <th>Description</th>
            </tr>
            <%
                for(ComplainBean complain:complainList){
                    if(session.getAttribute("empId").equals(complain.getEmpId())){}
                    else{
            %>
            <tr>
                <td class="comId"><%=complain.getcomId()%></td>
                <td class="Date"><%=complain.getDate()%></td>
                <td class="description"><%=complain.getDescription()%><%}}%></td>

        </table>

    </div>
</div>
<%@include file="mainDashboard.jsp" %>
<script type="text/javascript" src="js/viewComplains.js"></script>
</body>
</html>
