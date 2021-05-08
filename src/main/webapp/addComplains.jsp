<%@ page import="java.text.SimpleDateFormat" %><%--
  Created by IntelliJ IDEA.
  User: SupunN
  Date: 10/23/2020
  Time: 12:05 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import ="java.util.Date" %>
<%@ page import="complains.ComplainDao" %>
<%@ page import="complains.ComplainBean" %>
<%@ page import="java.util.List" %>
<html>
<head>
    <title>Human Resource Management System</title>
    <link rel="icon" href="img/logo.png" sizes="25x25" type="image/png">

    <link rel="stylesheet" href="style/mainStyle.css">
    <link rel="stylesheet" href="style/addComplain.css">
</head>
<body>

<div class="content">
    <div class="heading">
        <h3>Add Complain/Suggestions</h3>
    </div>
    <form name="addComplain" action="addComplain" method="POST" onsubmit="return validateForm()">
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

        if(result != null){
            if(result=="Successful"){%>
            <h4 class="response" style="color: #4bbe19;">
                Your Idea is Successfully Send!
            </h4><%

            }request.setAttribute("result",null);

         if(result == "Unsuccessful"){%>
        <h4 class="response" style="color: #DC143C;">
            Unable To Send Your Idea! , Try Again.
        </h4>
            <%} request.setAttribute("result",null);}
        request.setAttribute("result",null);
    %>
                <br>
    <input class="input" type="number" name="empId" value="<%=session.getAttribute("empId")%>" hidden>
    <div class="main">
        <table class="data">
            <tr>
                <td>
                    <label class="label">Complain/Suggestions Id</label>
                </td>
                <%
                    ResultSet rs2= null;
                    try {

                        Connection con = DBconn.getConnection();
                        Statement statement = con.createStatement();
                        rs2 = statement.executeQuery("SELECT ID FROM complainsuggestion ORDER BY ID DESC LIMIT 1");
                        if (rs2.next()) {%>

                <th>
                    <input class="input" type="text" name="comId" id="comId" value="<%=rs2.getInt("ID")+1%>" readonly>
                </th>

            </tr>
            <%}else {%>
            <th>
                <input class="input" type="text" name="comId" id="comId2" value="200" readonly>
            </th>

            </tr>
            <%}
            } catch (SQLException e) {
                e.printStackTrace();
            }

                Date date = new Date();
                SimpleDateFormat formatter = new SimpleDateFormat("yyy-MM-dd");
                String sendDay= formatter.format(date);
            %>
            <tr>
                <td>
                    <label class="label">Date</label>
                </td>
                <th>
                    <input class="input" type="text" value="<%=sendDay%>" name="date" id="today" readonly>
                </th>
            </tr>
        </table>

        <table class="data">
                <tr>
                    <th>
                        <textarea rows="5" cols="50" name="description" placeholder="Enter your Complain/Suggestion Here"></textarea>
                        <label id="lblDes" class="valLabel" ></label>
                    </th>
                </tr>
            </table>

            <table>
                <tr>
                    <td></td>
                    <td></td>
                    <td>
                        <input class="send" type="submit" value="Send"/>
                    </td>
                </tr>

        </table>
    </div>
    <hr>
                <br>
        <%
            ComplainDao comDao = new ComplainDao();
            String empId= (String) session.getAttribute("empId");
            List<ComplainBean> complainList = comDao.allComplains(empId);
        %>

        <div class="result">
            <table id="table">
                <tr>
                    <th>Id</th>
                    <th>Date</th>
                    <th>Description</th>
                </tr>
                <%
                    for(ComplainBean complain:complainList){
                        if(!complain.getEmpId().equals(empId)){}
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

<script type="text/javascript" src="js/addComplain.js"></script>
</body>

</html>
