<%@ page import="user.UserBean" %><%--
  Created by IntelliJ IDEA.
  User: SupunN
  Date: 23/18/2020
  Time: 21:53 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="user.UserBean" %>
<%@ page import="user.EmployeeDao" %>
<%@ page import="java.util.List" %>
<html>
<head>
    <title>Human Resource Management System</title>
    <link rel="icon" href="img/logo.png" sizes="25x25" type="image/png">
    <link rel="stylesheet" href="style/mainStyle.css">
    <link rel="stylesheet" href="style/searchEmployee.css">
</head>
<body>

<div class="content">
    <div class="heading">
        <h3>Update Employee</h3>
    </div>
    <form name="searchEmp" action="searchemployee" method="POST" onsubmit="return validateForm1()">
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
                if(result=="ErrPass"){%>
        <h4 class="response" style="color: #DC143C;">
            Unable To Update Employee!,Please Check The Details And Try Again.
        </h4>
        <%}
            request.setAttribute("result",null);

            if(result=="ErrLength"){%>
        <h4 class="response" style="color: #DC143C;">
            Unable To Update Employee!,Please Check The Details And Try Again.
        </h4><%

        }
        request.setAttribute("result",null);
        if(result=="Successful"){%>

        <h4 class="response" style="color: #4bbe19;">
            Employee Updated Successfully!
        </h4>
        <%}
            request.setAttribute("result",null);
            if(result == "Unsuccessful"){%>
        <h4 class="response" style="color: #DC143C;">
            Unable To Update Employee!,Please Check The Details And Try Again.
        </h4>
        <%} request.setAttribute("result",null);}
            request.setAttribute("result",null);
        %>
        <%
            EmployeeDao empDao = new EmployeeDao();
            List<UserBean> empList = empDao.searchAllEmployees();
        %>
        <div class="search">
            <table class="tblSearch">
                <br>
                <tr>
                    <td></td><td></td>
                    <td>
                        <input class="input" type="text" name="empId" placeholder="Select Employee To Update" id="empId" readonly>
                        <label id="lblEmpId" class="valLabel"></label>
                    </td>
                    <td></td><td></td>
                </tr>
                <tr><td></td><td></td>
                    <td>
                        <input type="submit" class="submit" value="Search">
                    </td>
                    <td></td>
                </tr>
            </table>
        </div>
            <div class="result">
                <br>
                <table id="tableResult">
                <tr>
                    <th>
                        Employee Id
                    </th>
                    <th align="left">
                           Name
                    </th>
                    <th align="left">
                           NIC
                    </th>
                </tr>
                <%
                for(UserBean employee:empList){
                    if(session.getAttribute("empId").equals(employee.getEmpId())){}
                    else{
                %>
                <tr>
                    <td class="empIdd"><%=employee.getEmpId()%></td>
                    <td class="empName"><%=employee.getFName()%> <%=employee.getLName()%></td>
                    <td class="empNIC"><%=employee.getNIC()%><%}}%></td>
                </tr>
            </table>
            </div>
    </form>
</div>
</div>
<%@include file="mainDashboard.jsp" %>
<!--<script type="text/javascript" src="js/loadDataToTable.js"></script>-->
<script type="text/javascript" src="js/getEmployee.js"></script>
</body>
</html>
